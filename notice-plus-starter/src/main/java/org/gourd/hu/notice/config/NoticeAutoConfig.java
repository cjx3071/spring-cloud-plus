package org.gourd.hu.notice.config;

import lombok.Data;
import org.gourd.hu.notice.mail.impl.GourdMailServiceImpl;
import org.gourd.hu.notice.sms.impl.GourdSmsServiceImpl;
import org.gourd.hu.notice.utils.MailUtil;
import org.gourd.hu.notice.utils.SmsUtil;
import org.gourd.hu.notice.websocket.NioWebSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 日志配置
 *
 * @author gourd.hu
 */

@Data
@Configuration
@Import({NioWebSocket.class, MailUtil.class, SmsUtil.class, GourdSmsServiceImpl.class, GourdMailServiceImpl.class})
public class NoticeAutoConfig {

}