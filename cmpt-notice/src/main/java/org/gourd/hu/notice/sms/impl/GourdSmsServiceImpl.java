package org.gourd.hu.notice.sms.impl;

import com.alibaba.alicloud.sms.ISmsService;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import org.gourd.hu.notice.sms.GourdSmsService;
import org.gourd.hu.notice.sms.domain.CodeTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author gourd
 *
 */
@Service
public class GourdSmsServiceImpl implements GourdSmsService {

    @Autowired
    private ISmsService iSmsService;

    /**
     * 短信签名
     */
    private static final String SIGN_NAME = "admin";
    /**
     * 短信模板
     */
    private static final String  TEMPLATE_CODE= "SMS_173195570";

    /**
     * 发送短信
     * @param phoneNumber
     * @param code
     * @return
     */
    @Override
    public SendSmsResponse sendMessage(String phoneNumber, String code) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SIGN_NAME);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(TEMPLATE_CODE);
        // 可选:模板中的变量替换JSON串,如模板内容为"【企业级分布式应用服务】,您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse ;
        try {
            sendSmsResponse = iSmsService.sendSmsRequest(request);
        }
        catch (ClientException e) {
            e.printStackTrace();
            sendSmsResponse = new SendSmsResponse();
        }
        return sendSmsResponse ;
    }

    /**
     * 批量短信发送
     * @param phoneNumbers
     * @param codes
     * @return
     */
    @Override
    public SendBatchSmsResponse batchSendCheckCode(Set phoneNumbers, Set<CodeTemp> codes) {
        // 组装请求对象
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        // 使用 GET 提交
        request.setMethod(MethodType.GET);
        // 必填:待发送手机号。支持JSON格式的批量调用，批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumberJson(JSON.toJSONString(phoneNumbers));
        // 必填:短信签名-支持不同的号码发送不同的短信签名
        request.setSignNameJson("[\""+SIGN_NAME+"\"]");
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(TEMPLATE_CODE);
        // 必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParamJson(JSON.toJSONString(codes));
        SendBatchSmsResponse sendSmsResponse ;
        try {
            sendSmsResponse = iSmsService
                    .sendSmsBatchRequest(request);
            return sendSmsResponse;
        }
        catch (ClientException e) {
            e.printStackTrace();
            sendSmsResponse =  new SendBatchSmsResponse();
        }
        return sendSmsResponse ;
    }

    /**
     * 短信查询
     * @param searchDate 格式yyyyMMdd
     * @param telephone
     * @return
     */
    @Override
    public QuerySendDetailsResponse querySendDetailsResponse(String searchDate,String telephone) {
        // 组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // 必填-号码
        request.setPhoneNumber(telephone);
        // 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据）
        request.setSendDate(searchDate);
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        try {
            QuerySendDetailsResponse response = iSmsService.querySendDetails(request);
            return response;
        }
        catch (ClientException e) {
            e.printStackTrace();
        }

        return new QuerySendDetailsResponse();
    }
}