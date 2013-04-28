/**
 * 
 */
package fr.exanpe.roomeeting.t5.lib.services;

/**
 * Gestionnaire de notificaition des {@link FlashMessages}
 * 
 * @author lguerin
 */
public interface FlashMessagesManager
{
    /**
     * Messages à notifier à l'utilisateur
     * 
     * @return Liste de messages à notifier
     */
    FlashMessages getFlashMessages();
}
