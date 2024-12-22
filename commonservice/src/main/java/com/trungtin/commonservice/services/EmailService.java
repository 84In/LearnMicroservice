package com.trungtin.commonservice.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration config;
    /**
     * Sends an email with the specified content, subject, recipient and optional attachment.
     *
     * @param to the recipient's email address
     * @param subject the subject of the email
     * @param content the body content of the email
     * @param isHtml a flag indicating if the email content is in HTML format
     * @param attachment an optional file attachment to include in the email
     */
    public void sendEmail(String to, String subject, String content, boolean isHtml, File attachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);

            // Add attachment if provided
            if (attachment != null) {
                FileSystemResource file = new FileSystemResource(attachment);
                helper.addAttachment(file.getFilename(), file);
            }

            javaMailSender.send(message);

            log.info("Email sent successfully to {}",to);

        }catch (MessagingException ex){
            log.error("Failed to {} send email to {}",to,ex.getMessage());

            // Handle the exception (retry logic, save dlq)
        }
    }

    /**
     * Sends an email using a specified template, along with optional placeholder data and an attachment.
     *
     * @param to the recipient's email address
     * @param subject the subject of the email
     * @param templateName the name of the template to be used for the email body
     * @param placeholder a map containing placeholders and their corresponding values for use in the template
     * @param attachment an optional file attachment to include in the email
     */

    public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> placeholder, File attachment) {

        try {
            Template template = config.getTemplate(templateName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, placeholder);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            // Add attachment if provided
            if (attachment != null) {
                FileSystemResource file = new FileSystemResource(attachment);
                helper.addAttachment(file.getFilename(), file);
            }
            javaMailSender.send(message);
            log.info("Email sent successfully to {}",to);
        }catch (MessagingException | IOException | TemplateException ex){
            log.error("Failed to {} send email to {}",to,ex.getMessage());
        }

    }
}
