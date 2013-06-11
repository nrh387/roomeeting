package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import fr.exanpe.roomeeting.domain.business.FeedbackManager;
import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

public class Menu
{
    @Inject
    private FeedbackManager feedbackManager;

    @Inject
    private RooMeetingSecurityContext securityContext;

    @Inject
    private RequestGlobals requestGlobals;

    public String getUsername()
    {
        // TODO : a modifier après avoir mis en place le filtre Spring Security
        // return securityContext.getUser().getUsername();
        return securityContext.getUser().getUsername();
    }

    public boolean isAuthenticated()
    {
        return securityContext.isLoggedIn();
    }

    public String getContextRoot()
    {
        return requestGlobals.getHTTPServletRequest().getContextPath();
    }

    public int getFeedbackCount()
    {
        return feedbackManager.count(securityContext.getUser());
    }
}
