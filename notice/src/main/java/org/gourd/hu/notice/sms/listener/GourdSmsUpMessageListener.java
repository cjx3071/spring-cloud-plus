package org.gourd.hu.notice.sms.listener;


import com.alibaba.alicloud.sms.SmsUpMessageListener;
import com.aliyun.mns.model.Message;

/**
 * 如果发送的短信需要接收对方回复的状态消息，只需实现该接口并初始化一个 Spring Bean 即可。
 * @author gourd
 */
//@Component
public class GourdSmsUpMessageListener implements SmsUpMessageListener
		{

	@Override
	public boolean dealMessage(Message message) {
	    // 在这里添加你的处理逻辑

    	//do something

		System.err.println(this.getClass().getName() + "; " + message.toString());
		return true;
	}
}