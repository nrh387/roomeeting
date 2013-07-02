package fr.exanpe.roomeeting.domain.mock;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.core.io.Resource;

import fr.exanpe.roomeeting.mail.service.RooMeetingMailService;

public class RooMeetingMailServiceMock implements RooMeetingMailService
{

    @Override
    public void sendEmail(String from, String to, String subject, String text)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendTemplatedEmail(String from, String to, String subject, String template, Map<String, String> model) throws MessagingException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendTemplatedEmail(String to, String subject, String template, Map<String, String> model) throws MessagingException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendTemplatedEmailWithAttachment(String from, String to, String subject, String template, Map<String, String> model, Resource attachment)
            throws MessagingException
    {
        // TODO Auto-generated method stub

    }

}
