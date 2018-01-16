package com.thinkgem.jeesite.modules.cms.entity;

import com.thinkgem.jeesite.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件引擎
 *
 * @author chenshufeng
 * @version $Id: MailEntrance.java, v 0.1 2014年12月4日 下午8:34:48 chenshufeng Exp $
 */
public final class EmailSender {
    private final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private MimeMessage mimeMsg; // MIME邮件对象
    private Session session; // 邮件会话对象
    private Properties props; // 系统属性

    private String username = ""; // smtp认证用户名和密码
    private String password = "";
    private String userkey = "";

    private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

    public EmailSender(String smtp) {
        try {
            setSmtpHost(smtp);
            createMimeMessage();
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    /**
     * 设置SMTP主机
     *
     * @param hostName String
     */
    public void setSmtpHost(String hostName) {
        if (hostName == null || hostName.trim().equals("")) {
            hostName = Global.getConfig("email.smtp.host");// PropertiesUtil.getString("");
        }
        if (props == null)
            props = System.getProperties(); // 获得系统属性对象
        props.put("mail.smtp.host", hostName); // 设置SMTP主机
        // props.put("mail.smtp.port", "995");
        // props.put("")
    }

    /**
     * 创建MIME邮件对象
     *
     * @return boolean
     */
    public boolean createMimeMessage() {
        try {
            session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
        } catch (Exception e) {
            return false;
        }
        try {
            mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
            mp = new MimeMultipart();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     */
    private void setNeedAuth() {
        if (props == null)
            props = System.getProperties();
        if (userkey == null || userkey.trim().equals("")) {
        }
        if (userkey == null || userkey.trim().equals("")) {
            props.put("mail.smtp.auth", "false");
        } else {
            props.put("mail.smtp.auth", "true");
        }
    }

    /**
     * @param name String
     * @param pass String
     */
    public void setNamePass(String name, String pass, String key) {
        if (name == null || name.trim().equals("")) {
        }
        if (pass == null || pass.trim().equals("")) {
        }
        username = name;
        password = pass;
        userkey = key;
        setNeedAuth();
    }

    /**
     * 设置主题
     *
     * @param mailSubject String
     * @return boolean
     */
    public boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject, "UTF-8");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置内容
     *
     * @param mailBody String
     */
    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=UTF-8");
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置附件
     *
     */
    public boolean addFileAffix(String filename) {
        try {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filename);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(MimeUtility.encodeText(fileds.getName()));
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            logger.error(filename, e);
            return false;
        }
    }

    /**
     * 设置发信人
     *
     */
    public boolean setFrom(String from) {
        if (from == null || from.trim().equals("")) {
        }
        try {
            String[] f = from.split(",");
            if (f.length > 1) {
                from = MimeUtility.encodeText(f[0]) + "<" + f[1] + ">";
            }
            mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
            return true;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * 设置收信人
     *
     */
    public boolean setTo(String to) {
        if (to == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * 设置抄送人
     *
     */
    public boolean setCopyTo(String copyto) {
        if (copyto == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 发送邮件
     *
     */
    public boolean sendout() {
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();

            Session mailSession = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    if (userkey == null || "".equals(userkey.trim())) {
                        return null;
                    }
                    return new PasswordAuthentication(username, userkey);
                }
            });
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), username, password);
            // 设置发送日期
            mimeMsg.setSentDate(new Date());
            // 发送
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            if (mimeMsg.getRecipients(Message.RecipientType.CC) != null) {
                transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
            }
            transport.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
