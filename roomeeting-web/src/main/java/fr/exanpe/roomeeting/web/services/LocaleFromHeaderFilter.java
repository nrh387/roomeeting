/**
 * 
 */
package fr.exanpe.roomeeting.web.services;

import java.io.IOException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.RequestGlobals;

import fr.exanpe.t5.lib.services.LocaleSessionService;

/**
 * @author jmaupoux
 */
public class LocaleFromHeaderFilter implements ComponentRequestFilter
{
    @Inject
    private LocaleSessionService localeSessionService;

    @Inject
    private RequestGlobals requestGlobals;

    @Override
    public void handleComponentEvent(ComponentEventRequestParameters parameters, ComponentRequestHandler handler) throws IOException
    {
        System.out.println("Locale required::" + requestGlobals.getRequest().getLocale());
        localeSessionService.setLocale(requestGlobals.getRequest().getLocale());

        handler.handleComponentEvent(parameters);
    }

    @Override
    public void handlePageRender(PageRenderRequestParameters parameters, ComponentRequestHandler handler) throws IOException
    {
        System.out.println("Locale required::" + requestGlobals.getRequest().getLocale());
        localeSessionService.setLocale(requestGlobals.getRequest().getLocale());

        handler.handlePageRender(parameters);
    }
}
