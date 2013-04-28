package fr.exanpe.roomeeting.t5.lib.demo.pages.comp;

import org.apache.tapestry5.ioc.annotations.Inject;

import fr.exanpe.roomeeting.t5.lib.services.FlashMessages;

/**
 * Page de demo des flash messages.
 * 
 * @author lguerin
 */
public class FlashMessagesDemo
{
    @Inject
    private FlashMessages flashMessages;

    void onSubmit()
    {
        flashMessages.inform("Hello World !");
    }
}
