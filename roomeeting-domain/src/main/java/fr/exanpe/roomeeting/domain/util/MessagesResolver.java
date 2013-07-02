package fr.exanpe.roomeeting.domain.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MessagesResolver
{
    @Autowired
    private ApplicationContext applicationContext;

    public String getMessage(String key, Locale locale)
    {
        return getMessage(key, null, locale);
    }

    public String getMessage(String key, Object[] params, Locale locale)
    {
        return applicationContext.getMessage(key, params, locale);
    }
}
