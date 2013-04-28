package fr.exanpe.roomeeting.mail.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

/**
 * @author lguerin
 */
@SpringApplicationContext("applicationContext-mail.xml")
public class RooMeetingMailServiceTest extends UnitilsTestNG
{

    private static final String MAIL_TEST_FROM = "noreply@roomeeting.fr";

    private static final String MAIL_TEST_SUBJECT = "Test";

    private static final String MAIL_TEST_TO = "test@roomeeting.fr";

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RooMeetingMailServiceTest.class);

    /**
     * Service de gestion des utilisateurs a tester
     */
    @SpringBean("roomeetingMailService")
    private RooMeetingMailService mailService;

    /**
     * Port de Test
     */
    private static final int TEST_SMTP_PORT = 1025;

    /**
     * Server SMTP de Test
     */
    private SimpleSmtpServer smtpServer;

    @BeforeMethod
    public void startSmtpServer()
    {
        smtpServer = SimpleSmtpServer.start(TEST_SMTP_PORT);
    }

    @AfterMethod
    public void stopSmtpServer()
    {
        smtpServer.stop();
    }

    /**
     * Test method for
     * {@link fr.exanpe.roomeeting.mail.service.RooMeetingMailService#sendEmail(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testSendEmail()
    {
        Assert.assertEquals(0, smtpServer.getReceivedEmailSize());
        mailService.sendEmail(MAIL_TEST_FROM, MAIL_TEST_TO, MAIL_TEST_SUBJECT, "Message de test");
        Assert.assertEquals(1, smtpServer.getReceivedEmailSize());

        SmtpMessage email = (SmtpMessage) smtpServer.getReceivedEmail().next();
        Assert.assertEquals(MAIL_TEST_SUBJECT, email.getHeaderValue("Subject"));
        Assert.assertEquals(MAIL_TEST_FROM, email.getHeaderValue("From"));
        Assert.assertEquals(MAIL_TEST_TO, email.getHeaderValue("To"));
        Assert.assertEquals("Message de test", email.getBody());
    }

    @Test
    public void testSendTemplatedEmail()
    {
        Map<String, String> model = this.getTemplateModel();
        Assert.assertEquals(0, smtpServer.getReceivedEmailSize());
        try
        {
            mailService.sendTemplatedEmail(MAIL_TEST_FROM, MAIL_TEST_TO, MAIL_TEST_SUBJECT, "emailTemplateTest.vm", model);
        }
        catch (MessagingException e)
        {
            LOGGER.error("Pb lors du préparation ou de l'envoi du mail: " + e);
            throw new RuntimeException(e);
        }
        Assert.assertEquals(1, smtpServer.getReceivedEmailSize());
        SmtpMessage email = (SmtpMessage) smtpServer.getReceivedEmail().next();
        Assert.assertEquals(MAIL_TEST_SUBJECT, email.getHeaderValue("Subject"));
        Assert.assertEquals(MAIL_TEST_FROM, email.getHeaderValue("From"));
        Assert.assertEquals(MAIL_TEST_TO, email.getHeaderValue("To"));
        Assert.assertTrue(email.getHeaderValue("Content-Type").contains("multipart/mixed"));
        Assert.assertTrue(email.getBody().contains(model.get("name")));
        Assert.assertTrue(email.getBody().contains(model.get("text")));
    }

    @Test
    public void testSendTemplatedEmailWithoutFrom()
    {
        Map<String, String> model = this.getTemplateModel();
        Assert.assertEquals(0, smtpServer.getReceivedEmailSize());
        try
        {
            mailService.sendTemplatedEmail(MAIL_TEST_TO, MAIL_TEST_SUBJECT, "emailTemplateTest.vm", model);
        }
        catch (MessagingException e)
        {
            LOGGER.error("Pb lors du préparation ou de l'envoi du mail: " + e);
            throw new RuntimeException(e);
        }
        Assert.assertEquals(1, smtpServer.getReceivedEmailSize());
        SmtpMessage email = (SmtpMessage) smtpServer.getReceivedEmail().next();
        Assert.assertEquals(MAIL_TEST_FROM, email.getHeaderValue("From"));
    }

    @Test
    public void testSendTemplatedEmailWithAttachment()
    {
        Map<String, String> model = this.getTemplateModel();
        Assert.assertEquals(0, smtpServer.getReceivedEmailSize());
        Resource resource = new ClassPathResource("tapestry.png");
        try
        {
            mailService.sendTemplatedEmailWithAttachment(MAIL_TEST_FROM, MAIL_TEST_TO, MAIL_TEST_SUBJECT, "emailTemplateTest.vm", model, resource);
        }
        catch (MessagingException e)
        {
            LOGGER.error("Pb lors du préparation ou de l'envoi du mail: " + e);
            throw new RuntimeException(e);
        }
        Assert.assertEquals(1, smtpServer.getReceivedEmailSize());
    }

    private Map<String, String> getTemplateModel()
    {
        Map<String, String> model = new HashMap<String, String>();
        String name = "roomeeting";
        String text = "Mail de l'application RooMeeting";
        model.put("name", name);
        model.put("text", text);
        return model;
    }

}
