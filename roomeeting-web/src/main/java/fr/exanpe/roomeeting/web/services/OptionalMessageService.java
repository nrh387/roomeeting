package fr.exanpe.roomeeting.web.services;

import org.apache.tapestry5.ioc.Messages;

/**
 * Return a message, or the key itself
 * 
 * @author jmaupoux
 */
public class OptionalMessageService
{
    public String getOptionalMessage(Messages messages, String key)
    {
        if (messages.contains(key)) { return messages.get(key); }
        return key;
    }
}
