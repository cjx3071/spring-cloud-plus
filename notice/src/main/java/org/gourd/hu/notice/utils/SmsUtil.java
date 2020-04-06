package org.gourd.hu.notice.utils;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.gourd.hu.notice.sms.GourdSmsService;
import org.gourd.hu.notice.sms.domain.CodeTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @Description 阿里短信工具类
 * @Author gourd
 * @Date 2019/12/2 10:14
 * @Version 1.0
 */
@Component
public class SmsUtil {

    private static GourdSmsService smsService;

    @Autowired
    private GourdSmsService gourdSmsService;

    @PostConstruct
    private void init() {
        smsService = gourdSmsService;
    }

    /**
     * 发送短信
     * @param phoneNumber
     * @param code
     * @return
     */
    public static SendSmsResponse sendMessage(String phoneNumber, String code){
        return smsService.sendMessage(phoneNumber,code);
    }
    /**
     * 批量短信发送
     * @param phoneNumbers
     * @param codes
     * @return
     */
    public static SendBatchSmsResponse batchsendCheckCode(Set phoneNumbers, Set<CodeTemp> codes){
        return smsService.batchSendCheckCode(phoneNumbers,codes);
    }
    /**
     * 短信查询
     * @param searchDate 格式yyyyMMdd
     * @param telephone
     * @return
     */
    public static QuerySendDetailsResponse querySendDetailsResponse(String searchDate, String telephone){
        return smsService.querySendDetailsResponse(searchDate,telephone);
    }


}
