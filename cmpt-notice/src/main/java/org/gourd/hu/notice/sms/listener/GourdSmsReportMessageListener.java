package org.gourd.hu.notice.sms.listener;

import com.alibaba.alicloud.sms.SmsReportMessageListener;
import com.aliyun.mns.model.Message;

/**
 * 如果需要监听短信是否被对方成功接收，只需实现这个接口并初始化一个 Spring Bean 即可。
 * @author gourd.hu
 */
//@Component
public class GourdSmsReportMessageListener implements SmsReportMessageListener {

	@Override
	public boolean dealMessage(Message message) {
	    // 在这里添加你的处理逻辑
	    //do something

		System.err.println(this.getClass().getName() + "; " + message.toString());
		return true;
	}
}