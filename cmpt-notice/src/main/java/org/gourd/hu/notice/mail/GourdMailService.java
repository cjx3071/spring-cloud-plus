package org.gourd.hu.notice.mail;

/**
 * @author gourd.hu
 */
public interface GourdMailService {


    /**
     * 发送包含简单文本的邮件
     *
     * @param toMails 接收者
     * @param subject 主题
     * @param text    内容
     */
    void sendTxtMail(String[] toMails, String subject, String text) ;

    /**
     * 发送包含HTML文本的邮件
     *
     * @param toMails  接收者
     * @param subject  主题
     * @param htmlText html格式内容
     */
    void sendHtmlMail(String[] toMails, String subject, String htmlText) ;

    /**
     * 发送包含内嵌图片的邮件
     */
    void sendImageMail(String[] toMails, String subject, String htmlImageText);

    /**
     * 发送包含附件的邮件
     */
    void sendAttachmentMail(String[] toMails, String subject, String htmlAttachText) ;

    /**
     * 发送模板邮件
     */
    void sendTemplateMail(String[] toMails, String subject) ;

}