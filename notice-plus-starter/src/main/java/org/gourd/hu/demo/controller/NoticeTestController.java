package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.gourd.hu.notice.utils.MailUtil;
import org.gourd.hu.notice.utils.SmsUtil;
import org.gourd.hu.notice.utils.ValidateCodeUtil;
import org.gourd.hu.notice.websocket.NioWebSocket;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通信测试
 * @author gourd.hu
 */
@Api(tags = "通讯测试API", description = "通信测试控制器" )
@RestController
@RequestMapping("/notice")
public class NoticeTestController {

    @PostMapping("/mail/text")
    @ApiOperation(value = "发送文本邮件")
    public void sendTextMail() {
        String[] toMails = new String[]{"869570209@qq.com"} ;
        String subject = "gourd邮件";
        String text = "gourd你好";
        MailUtil.sendTxtMail(toMails,subject,text);
    }

    @PostMapping("/mail/html")
    @ApiOperation(value = "发送html格式邮件")
    public void sendHtmlMail() {
        String[] toMails = new String[]{"869570209@qq.com"} ;
        String subject = "gourd邮件";
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>gourd邮件</head>");
        sb.append("<body><h1>gourd邮件测试</h1><p>hello!this is spring mail test。</p></body>");
        sb.append("</html>");
        MailUtil.sendHtmlMail(toMails,subject,sb.toString());
    }
    @PostMapping("/mail/image")
    @ApiOperation(value = "发送图片邮件")
    public void sendImageMail() {
        String[] toMails = new String[]{"869570209@qq.com"} ;
        String subject = "gourd邮件";
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>gourd邮件</head>");
        sb.append("<body><h1>gourd邮件</h1><p>hello!this is spring mail test。</p>");
        // cid为固定写法，imageId指定一个标识
        sb.append("<img src=\"cid:imageId\"/></body>");
        sb.append("</html>");
        MailUtil.sendImageMail(toMails,subject,sb.toString());
    }

    @PostMapping("/mail/attach")
    @ApiOperation(value = "发送附件邮件")
    public void sendAttachmentMail() {
        String[] toMails = new String[]{"869570209@qq.com"} ;
        String subject = "gourd邮件";
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>gourd邮件</head>");
        sb.append("<body><h1>gourd邮件测试</h1><p>hello!this is spring mail test。</p></body>");
        sb.append("</html>");
        MailUtil.sendAttachmentMail(toMails,subject,sb.toString());
    }

    @PostMapping("/mail/template")
    @ApiOperation(value = "发送模板邮件")
    public void sendTemplateMail() {
        String[] toMails = new String[]{"13584278267@163.com"} ;
        String subject = "gourd邮件";
        MailUtil.sendTemplateMail(toMails,subject);
    }


    @PostMapping("/msg")
    @ApiOperation(value = "发送消息")
    public void sendSmsCode() {
        SmsUtil.sendMessage("13584278267", ValidateCodeUtil.getCode(4));
    }


    @PostMapping("/broadcast")
    @ApiOperation(value = "广播消息")
    public void broadcastMsg(String msg) {
        NioWebSocket.broadcast(msg);
    }



}
