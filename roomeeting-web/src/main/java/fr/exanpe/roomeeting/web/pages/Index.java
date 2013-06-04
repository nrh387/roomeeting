package fr.exanpe.roomeeting.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

/**
 * Start page of application roomeeting-web.
 */
public class Index
{
    @Property
    @SuppressWarnings("unused")
    private boolean loginFailed;

    @Property
    @SuppressWarnings("unused")
    private String contextRoot;

    @Inject
    private RequestGlobals globals;

    @Inject
    private RooMeetingSecurityContext securityContext;

    Object onActivate(@RequestParameter(value = "loginFailed", allowBlank = true)
    boolean loginFailed)
    {
        if (securityContext.isLoggedIn()) { return Home.class; }

        this.loginFailed = loginFailed;
        this.contextRoot = globals.getRequest().getContextPath();

        return null;
    }

}
