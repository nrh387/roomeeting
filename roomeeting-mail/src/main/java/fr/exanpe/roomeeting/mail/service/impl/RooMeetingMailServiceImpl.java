/**
 * 
 */
package fr.exanpe.roomeeting.mail.service.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import fr.exanpe.roomeeting.mail.service.RooMeetingMailService;

/**
 * Implementation du service de mail pour RooMeeting
 * 
 * @author lguerin
 */
@Component("roomeetingMailService")
public class RooMeetingMailServiceImpl implements RooMeetingMailService
{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${mail.from}")
    private String from;

    /*
     * (non-Javadoc)
     * @see
     * fr.exanpe.roomeeting.mail.service.RooMeetingMailService#sendEmail(java.lang.String)
     */
    @Override
    public void sendEmail(String from, String to, String subject, String text)
    {
        this.sendSimpleEmail(from, to, subject, text);
    }

    @Override
    public void sendTemplatedEmail(String from, String to, String subject, String template, Map<String, String> model) throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);

        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, model);
        helper.setText(emailText, true);
        mailSender.send(message);
    }

    private void sendSimpleEmail(String from, String to, String subject, String text)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendTemplatedEmail(String to, String subject, String template, Map<String, String> model) throws MessagingException
    {
        this.sendTemplatedEmail(this.from, to, subject, template, model);
    }

    @Override
    public void sendTemplatedEmailWithAttachment(String from, String to, String subject, String template, Map<String, String> model, Resource attachment)
            throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);

        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, model);
        helper.setText(emailText, true);
        helper.addAttachment(attachment.getFilename(), attachment);
        mailSender.send(message);
    }

}
