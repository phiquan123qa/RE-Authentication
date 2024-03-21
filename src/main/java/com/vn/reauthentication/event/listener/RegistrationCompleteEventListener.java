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
        String mailContent = "<table role=\"presentation\\\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:separate;\" align=\"center\"\n" +
                "        border=\"0\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                "                    <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">\n" +
                "                        Confirm Your Email Address</h1>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "                    <p style=\"margin: 0;\">Say hi to \"" + user.getName() + "\".</p>\n" +
                "                    <p style=\"margin: 0;\">Thank you for registering with us, \n" +
                "                        Please follow the link before to complete your registration.</p>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"border:1px solid #353535;border-radius:5px;color:#fff;cursor:auto;padding:15px 30px;\"\n" +
                "                    align=\"center\" valign=\"middle\" bgcolor=\"#505050\">\n" +
                "                    <a href=" + url + "\n" +
                "                        style=\"text-decoration:none;line-height:100%;background:#505050;color:white;font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:bold;text-transform:none;margin:0px;\">Confirm\n" +
                "                        Email Address</a>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "                    <p style=\"margin: 0;\">If that doesn't work, you can contact us with:</p>\n" +
                "                    <p style=\"margin: 0;\"><a href=\"realestatemq.up.railway.app/help\"\n" +
                "                            target=\"_blank\">QMHouse Webside</a></p>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                "                    <p style=\"margin: 0;\">Thank you for use QMHouse Service,<br> qmhouse.re@gmail.com</p>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }
    public void sendPasswordResetVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException {

        String subject = "[QMHouse] \uD83C\uDF89 Password Reset Request Verification";
        String senderName = "Users Verification Service";
        String mailContent = "<table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:separate;\" align=\"center\"\n" +
                "        border=\"0\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                "                    <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">\n" +
                "                        Reset Your Password</h1>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "                    <p style=\"margin: 0;\">Say hi to \"" + user.getName() + "\".</p>\n" +
                "                    <p style=\"margin: 0;\">You recently requested to reset your password, \n" +
                "                        Please follow the link to reset your password.</p>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"border:1px solid #353535;border-radius:5px;color:#fff;cursor:auto;padding:15px 30px;\"\n" +
                "                    align=\"center\" valign=\"middle\" bgcolor=\"#505050\">\n" +
                "                    <a href=" + url + "\n" +
                "                        style=\"text-decoration:none;line-height:100%;background:#505050;color:white;font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:bold;text-transform:none;margin:0px;\">Reset\n" +
                "                        Password</a>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "                    <p style=\"margin: 0;\">If that doesn't work, you can contact us with:</p>\n" +
                "                    <p style=\"margin: 0;\"><a href=\"realestatemq.up.railway.app/help\"\n" +
                "                            target=\"_blank\">QMHouse Webside</a></p>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"left\" bgcolor=\"#ffffff\"\n" +
                "                    style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                "                    <p style=\"margin: 0;\">Thank you for use QMHouse Service,<br> qmhouse.re@gmail.com</p>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>";;
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
