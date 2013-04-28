/**
 * 
 */
package fr.exanpe.roomeeting.t5.lib.services.impl;

import java.util.ArrayList;
import java.util.List;

import fr.exanpe.roomeeting.t5.lib.services.FlashMessages;

/**
 * @author lguerin
 */
public class FlashMessagesImpl implements FlashMessages
{

    /**
     * Flash messages de type information
     */
    List<String> informations = new ArrayList<String>();

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.t5.lib.base.FlashMessage#inform(java.lang.String)
     */
    @Override
    public void inform(String message)
    {
        informations.add(message);
    }

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.t5.lib.base.FlashMessage#getInformations()
     */
    @Override
    public List<String> getInformations()
    {
        return informations;
    }

    /*
     * (non-Javadoc)
     * @see fr.exanpe.roomeeting.t5.lib.base.FlashMessage#clear()
     */
    @Override
    public void clear()
    {
        informations.clear();
    }

    @Override
    public boolean getHasMessages()
    {
        return informations.size() != 0;
    }

}
