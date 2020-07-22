package org.gourd.hu.notice.utils;

import org.gourd.hu.notice.mail.GourdMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description 邮件工具类
 * @author gourd.hu
 * @Date 2019/12/2 10:14
 * @Version 1.0
 */
@Component
public class MailUtil {

    private static GourdMailService mailService;

    @Autowired
    private GourdMailService gourdMailService;

    @PostConstruct
    private void init(){
        mailService = gourdMailService;
    }

    /**
     * 发送包含简单文本的邮件
     *
     * @param toMails 接收者
     * @param subject 主题
     * @param text    内容
     */
    public static void sendTxtMail(String[] toMails, String subject, String text) {
        mailService.sendTxtMail(toMails,subject,text);
    }

    /**
     * 发送包含HTML文本的邮件
     *
     * @param toMails  接收者
     * @param subject  主题
     * @param htmlText html格式内容
     */
    public static void sendHtmlMail(String[] toMails, String subject, String htmlText) {
        mailService.sendHtmlMail(toMails,subject,htmlText);
    }

    /**
     * 发送包含内嵌图片的邮件
     */
    public static void sendImageMail(String[] toMails, String subject, String htmlImageText){
        mailService.sendImageMail(toMails,subject,htmlImageText);
    }

    /**
     * 发送包含附件的邮件
     */
    public static void sendAttachmentMail(String[] toMails, String subject, String htmlAttachText) {
        mailService.sendAttachmentMail(toMails,subject,htmlAttachText);
    }

    /**
     * 发送模板邮件
     */
    public static void sendTemplateMail(String[] toMails, String subject) {
        mailService.sendTemplateMail(toMails,subject);
    }

}
