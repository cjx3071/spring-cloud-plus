package org.gourd.hu.openapi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Url工具类
 *
 * @author gourd.hu
 */
@Slf4j
public class UrlUtil {

    /**
     * 获取get请求参数
     * @param request
     * @return
     */
    public static Map<String, String> getGetParameterMap(HttpServletRequest request) {
        // 返回值Map
        Map<String, String> returnMap = new TreeMap<>();
        // 参数Map
        Map properties = request.getParameterMap();
        if(properties == null){
            return returnMap;
        }
        Iterator entries = properties.entrySet().iterator();
        return getStringMap(returnMap, entries);
    }
    /**
     * 获取post请求参数
     * @param jsonObject
     * @return
     */
    public static Map<String, String> getPostParameterMap(JSONObject jsonObject) {
        // 返回值Map
        Map<String, String> returnMap = new TreeMap<>();
        if(jsonObject == null ){
            return returnMap;
        }
        Iterator entries = jsonObject.entrySet().iterator();
        return getStringMap(returnMap, entries);
    }

    private static Map<String, String> getStringMap(Map<String, String> returnMap, Iterator entries) {
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (String value1 : values) {
                    value = value1 + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    /**
     * 获取post参数
     *
     * @param request
     * @return
     */
    public static JSONObject getPostParameter(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            log.error("获取post参数异常", e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("获取post参数异常", e);
                }
            }
        }
        return JSON.parseObject(sb.toString());
    }


    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new TreeMap<>();
        if (StringUtils.isEmpty(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.lastIndexOf("&"));
        }
        return s;
    }

}
