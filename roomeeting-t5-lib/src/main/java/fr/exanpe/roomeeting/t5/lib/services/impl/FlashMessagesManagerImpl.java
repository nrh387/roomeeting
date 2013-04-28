/**
 * 
 */
package fr.exanpe.roomeeting.t5.lib.services.impl;

import org.apache.tapestry5.services.ApplicationStateManager;

import fr.exanpe.roomeeting.t5.lib.services.FlashMessages;
import fr.exanpe.roomeeting.t5.lib.services.FlashMessagesManager;

/**
 * Implémentation du service {@link FlashMessagesManager}
 * 
 * @author lguerin
 */
public class FlashMessagesManagerImpl implements FlashMessagesManager
{
    /**
     * Service responsable de la gestion des objets SSO en session
     */
    private ApplicationStateManager stateManager;

    public FlashMessagesManagerImpl(ApplicationStateManager stateManager)
    {
        this.stateManager = stateManager;
    }

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.t5.lib.services.FlashMessagesManager#getFlashMessages()
     */
    @Override
    public FlashMessages getFlashMessages()
    {
        if (!stateManager.exists(FlashMessages.class))
        {
            stateManager.set(FlashMessages.class, new FlashMessagesImpl());
        }
        return stateManager.get(FlashMessages.class);
    }

}
