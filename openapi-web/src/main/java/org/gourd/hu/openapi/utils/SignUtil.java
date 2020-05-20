package org.gourd.hu.openapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.cache.utils.RedisUtil;
import org.gourd.hu.core.utils.IpAddressUtil;
import org.gourd.hu.openapi.constant.AuthConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 签名工具类
 *
 * @author gourd.hu
 */
@Slf4j
@Component
public class SignUtil {


    /**
     * 请求参数Map转换验证Map
     * @param requestParams 请求参数Map
     * @param charset 是否要转utf8编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String,String> toVerifyMap(Map<String, String[]> requestParams, boolean charset) {
        Map<String,String> params = new HashMap<>(requestParams.size());
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            if(charset){
                valueStr = getContentString(valueStr, AuthConstant.INPUT_CHARSET);
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<>(sArray.size());
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (StringUtils.isBlank(value) || key.equalsIgnoreCase(AuthConstant.SIGN_KEY)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        return createLinkString(params, false);
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @param encode 是否需要UrlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode){
                value = urlEncode(value, AuthConstant.INPUT_CHARSET);
            }
            stringBuilder.append(key).append("=").append(value);
            // 拼接时，不包括最后一个&字符
            if (i != keys.size() - 1) {
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 编码转换
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 编码转换
     * @param content
     * @param charset
     * @return
     */
    private static String getContentString(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return new String(content.getBytes());
        }
        try {
            return new String(content.getBytes("ISO-8859-1"), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * URL转码
     * @param content
     * @param charset
     * @return
     */
    private static String urlEncode(String content, String charset) {
        try {
            return URLEncoder.encode(content, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }



    /**
     * 生成要请求的签名参数数组
     * @param sParaTemp 需要签名的参数Map
     * @return 要请求的签名参数数组
     */
    public static Map<String, String> signMap(Map<String, String[]> sParaTemp) {
        // 请求参数Map转换验证Map，并生成要请求的签名参数数组
        return sign(toVerifyMap(sParaTemp, false));
    }

    /**
     * 生成要请求的签名参数数组
     * @param sParaTemp 需要签名的参数
     * @return 要请求的签名参数数组
     */
    public static Map<String, String> sign(Map<String, String> sParaTemp) {
        HttpServletRequest request = RequestHolder.getRequest();
        // 时间戳加入签名参数组中
        if(StringUtils.isBlank(sParaTemp.get(AuthConstant.TIMESTAMP_KEY))){
            sParaTemp.put(AuthConstant.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        }
        // 随机数加入签名参数组中
        if(StringUtils.isBlank(sParaTemp.get(AuthConstant.NONCE_KEY))){
            sParaTemp.put(AuthConstant.NONCE_KEY, IpAddressUtil.getIpAddr(request)+RandomUtils.nextLong());
        }else {
            // 随机数拼接上请求ip
            sParaTemp.put(AuthConstant.NONCE_KEY, IpAddressUtil.getIpAddr(request)+sParaTemp.get(AuthConstant.NONCE_KEY));
        }
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String linkString = createLinkString(sPara);
        // 生成签名结果
        String mySign = DigestUtils.md5DigestAsHex(getContentBytes(linkString, AuthConstant.INPUT_CHARSET));
        // 签名结果加入请求提交参数组中
        sPara.put(AuthConstant.SIGN_KEY, mySign);
        return sPara;
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {
        // 传入的签名
        String sign = params.get(AuthConstant.SIGN_KEY);
        // 获得签名验证结果
        String mySign = sign(params).get(AuthConstant.SIGN_KEY);
        if (!mySign.equals(sign)){
            return false;
        }
        return true;
    }

    /**
     * 生成签名
     * @param params
     * @return
     */
    public static String generateSign(Map<String, String> params,Integer expireTimes) {
        Map<String, String> stringMap = sign(params);
        // 保存随机数
        RedisUtil.setExpire(AuthConstant.NONCE_KEY,stringMap.get(AuthConstant.NONCE_KEY),expireTimes);
        return stringMap.get(AuthConstant.SIGN_KEY);
    }



}