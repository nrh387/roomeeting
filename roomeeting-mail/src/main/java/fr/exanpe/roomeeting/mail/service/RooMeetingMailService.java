package fr.exanpe.roomeeting.mail.service;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.core.io.Resource;

public interface RooMeetingMailService
{

    /**
     * Envoyer un email simple a un destinataire
     * 
     * @param from Expediteur
     * @param to Destinataire
     * @param subject Sujet
     * @param texte Corps du mail
     */
    void sendEmail(String from, String to, String subject, String text);

    /**
     * Envoyer un email au format MIME a partir d'un modele
     * 
     * @param from Expediteur
     * @param to Destinataire
     * @param subject Sujet
     * @param template Le template du mail a envoyer
     * @param model Le modele utilise pour alimenter le template
     * @throws MessagingException en cas de pb lors de la construction du message
     */
    void sendTemplatedEmail(String from, String to, String subject, String template, Map<String, String> model) throws MessagingException;

    /**
     * Envoyer un email au format MIME a partir d'un template et d'un modele
     * 
     * @param to Destinataire
     * @param subject Sujet
     * @param template Le template du mail a envoyer
     * @param model Le modele utilise pour alimenter le template
     * @throws MessagingException en cas de pb lors de la construction du message
     */
    void sendTemplatedEmail(String to, String subject, String template, Map<String, String> model) throws MessagingException;

    /**
     * Envoyer un email au format MIME a partir d'un template et d'un modele, plus une piece jointe
     * 
     * @param from Expediteur
     * @param to Destinataire
     * @param subject Sujet
     * @param template Le template du mail a envoyer
     * @param model Le modele utilise pour alimenter le template
     * @param attachment La piece jointe
     * @throws MessagingException en cas de pb lors de la construction du message
     */
    void sendTemplatedEmailWithAttachment(String from, String to, String subject, String template, Map<String, String> model, Resource attachment)
            throws MessagingException;
}
