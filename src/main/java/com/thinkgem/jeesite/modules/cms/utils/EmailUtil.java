package com.thinkgem.jeesite.modules.cms.utils;


import com.thinkgem.jeesite.modules.cms.entity.Email;
import com.thinkgem.jeesite.modules.cms.entity.EmailSender;

/**
 * 发送邮件辅助类
 *
 * @author chenshufeng
 * @version $Id: MailUtil.java, v 0.1 2014年12月4日 下午8:22:43 chenshufeng Exp $
 */
public final class EmailUtil {
    private EmailUtil() {
    }

    /**
     * 发送邮件
     */
    public static final boolean sendEmail(Email email) {
        // 初始化邮件引擎
        EmailSender sender = new EmailSender(email.getHost());
        sender.setNamePass(email.getName(), email.getPassword(), email.getKey());
        if (sender.setFrom(email.getFrom()) == false)
            return false;
        if (sender.setTo(email.getSendTo()) == false)
            return false;
        if (email.getCopyTo() != null && sender.setCopyTo(email.getCopyTo()) == false)
            return false;
        if (sender.setSubject(email.getTopic()) == false)
            return false;
        if (sender.setBody(email.getBody()) == false)
            return false;
        if (email.getFileAffix() != null) {
            for (int i = 0; i < email.getFileAffix().length; i++) {
                if (sender.addFileAffix(email.getFileAffix()[i]) == false)
                    return false;
            }
        }
        // 发送
        return sender.sendout();
    }

    public static void main(String[] args) {
        String body = "您的密码已经重置成功,账号:";
        Email email = new Email("13505182554@163.com", "1097123033@qq.com", null, "蜂巢后台密码重置", body, null);
        email.setHost("smtp.163.com");
        email.setName("13505182554@163.com");
        email.setPassword("qq114616127");
        email.setKey("resetPassword");
        Boolean aa = EmailUtil.sendEmail(email);
        System.out.printf(aa.toString());
    }
}
