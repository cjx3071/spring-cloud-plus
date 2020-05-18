package org.gourd.hu.notice.sms;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.gourd.hu.notice.sms.domain.CodeTemp;

import java.util.Set;

/**
 * @author gourd.hu
 *
 */
public interface GourdSmsService {

    SendSmsResponse sendMessage(String phoneNumber, String code);

    SendBatchSmsResponse batchSendCheckCode(Set phoneNumbers, Set<CodeTemp> codes);

    QuerySendDetailsResponse querySendDetailsResponse(String searchDate, String telephone);
}