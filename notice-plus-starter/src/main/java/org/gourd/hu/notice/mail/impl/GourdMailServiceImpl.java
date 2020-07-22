package org.gourd.hu.notice.mail.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.notice.mail.GourdMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送
 * @author gourd.hu
 */
@Service
@Slf4j
public class GourdMailServiceImpl implements GourdMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.senderName}")
    private String fromName;

    /**
     * 发送包含简单文本的邮件
     *
     * @param toMails 接收者
     * @param subject 主题
     * @param text    内容
     */
    @Override
    public void sendTxtMail(String[] toMails, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(toMails);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        // 发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("^o^= 邮件已发送");
    }

    /**
     * 发送包含HTML文本的邮件
     *
     * @param toMails  接收者
     * @param subject  主题
     * @param htmlText html格式内容
     */
    @Override
    public void sendHtmlMail(String[] toMails, String subject, String htmlText) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            setMailInfo(toMails, subject, htmlText, mimeMessageHelper);
            // 发送邮件
            javaMailSender.send(mimeMessage);
            log.info("^o^= 邮件已发送");
        } catch (Exception e) {
            log.info("^o^= 邮件发送失败{}：",e);
        }
    }

    /**
     * 发送包含内嵌图片的邮件
     */
    @Override
    public void sendImageMail(String[] toMails, String subject, String htmlImageText) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // multipart模式
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            setMailInfo(toMails, subject, htmlImageText, mimeMessageHelper);
            // 设置imageId
            FileSystemResource img = new FileSystemResource(new File("D:/MailImage/welcome.jpg"));
            mimeMessageHelper.addInline("imageId", img);
            // 发送邮件
            javaMailSender.send(mimeMessage);
            log.info("^o^= 邮件已发送");
        } catch (Exception e) {
            log.info("^o^= 邮件发送失败{}：",e);
        }
    }

    /**
     * 发送包含附件的邮件
     */
    @Override
    public void sendAttachmentMail(String[] toMails, String subject, String htmlAttachText) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // multipart模式
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            setMailInfo(toMails, subject, htmlAttachText, mimeMessageHelper);
            // 设置附件
            FileSystemResource img = new FileSystemResource(new File("D:/MailImage/welcome.jpg"));
            FileSystemResource xlsx = new FileSystemResource(new File("D:/MailImage/软件项目需求.xlsx"));
            mimeMessageHelper.addAttachment("welcome.jpg", img);
            mimeMessageHelper.addAttachment("软件项目需求.xlsx", xlsx);
            // 发送邮件
            javaMailSender.send(mimeMessage);
            log.info("^o^= 邮件已发送");
        } catch (Exception e) {
            log.info("^o^= 邮件发送失败{}：",e);
        }
    }

    /**
     * 发送模板邮件
     */
    @Override
    public void sendTemplateMail(String[] toMails, String subject) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // multipart模式
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            // 声明一个上下文对象，里面放入要存到模板里面的数据
            Context context = new Context();
            Map<String, Object> params = new HashMap(4){{
                put("name","小葫芦");
                put("account","gourdHu");
                put("password","123456");
            }};
            context.setVariables(params);
            // 指定相应的模板，然后给context数据传过去
            String process = templateEngine.process("mail", context);
            setMailInfo(toMails, subject, process, mimeMessageHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 发送邮件
        javaMailSender.send(mimeMessage);
        log.info("^o^= 邮件已发送");
    }

    /**
     * 设置邮件信息
     * @param toMails
     * @param subject
     * @param text
     * @param mimeMessageHelper
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private void setMailInfo(String[] toMails, String subject, String text, MimeMessageHelper mimeMessageHelper) throws MessagingException, UnsupportedEncodingException {
        mimeMessageHelper.setTo(toMails);
        mimeMessageHelper.setFrom(from, fromName);
        mimeMessageHelper.setSubject(subject);
        if(StringUtils.isNotBlank(text)){
            // 启用html
            mimeMessageHelper.setText(text, true);
        }
    }
}