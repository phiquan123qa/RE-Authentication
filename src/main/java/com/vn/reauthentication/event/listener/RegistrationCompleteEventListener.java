package com.vn.reauthentication.event.listener;

import com.vn.reauthentication.entity.User;
import com.vn.reauthentication.event.RegistrationCompleteEvent;
import com.vn.reauthentication.service.VerificationTokenService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final VerificationTokenService tokenService;
    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(@NonNull RegistrationCompleteEvent event) {
        user = event.getUser();
        String vToken = UUID.randomUUID().toString();
        tokenService.saveVerificationTokenForUser(user, vToken);
        String url = event.getConfirmationUrl() + "/verify_email?token=" + vToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "[QMHouse] \uD83C\uDF89 Email Verification New Account";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p>Say hi to " + user.getName() + "</p>" +
                "<p>Thank you for registering with us, " +
                "Please follow the link before to complete your registration.</p>" +
                "<table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:separate;\" align=\"center\"\n" +
                "        border=\"0\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td style=\"border:1px solid #353535;border-radius:5px;color:#fff;cursor:auto;padding:15px 30px;\"\n" +
                "                    align=\"center\" valign=\"middle\" bgcolor=\"#505050\">\n" +
                "                    <a href=\"" + url + "\"\n" +
                "                        style=\"text-decoration:none;line-height:100%;background:#505050;color:white;font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:bold;text-transform:none;margin:0px;\">\n" +
                "                        Verify your email to active your account</p>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>"+
                "<p>Thank you for use QMHouse Service</p>";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }
    public void sendPasswordResetVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException {

        String subject = "[QMHouse] \uD83C\uDF89 Password Reset Request Verification";
        String senderName = "Users Verification Service";
        String mailContent = "<p> Hi "+ user.getName()+ ", </p>"+
                "<p><b>You recently requested to reset your password,</b>"+
                "<p>Thank you for using our service.</p>"+
                "<p>To reset your password,"+
                "Please, follow the link below to complete the action.</p>"+
                "<table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:separate;\" align=\"center\" border=\"0\">\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <td style=\"border:1px solid #353535;border-radius:5px;color:#fff;cursor:auto;padding:15px 30px;\" align=\"center\" valign=\"middle\" bgcolor=\"#505050\">\n" +
                "              <a href=\"" +url+ "\" style=\"text-decoration:none;line-height:100%;background:#505050;color:white;font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:bold;text-transform:none;margin:0px;\">Reset password</a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>"+
                "<p> Users Verification Service</p>";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }
    private static void emailMessage(String subject, String senderName,
                                     String mailContent, JavaMailSender mailSender, User user)
            throws MessagingException,UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("qmhouse.re@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
