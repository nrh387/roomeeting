package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import fr.exanpe.roomeeting.domain.security.RooMeetingSecurityContext;

/**
 * Layout component for pages of application roomeeting-web.
 */
@Import(stylesheet =
{ "context:css/bootstrap-roomeeting.css", "context:css/roomeeting.css", "${exanpe.asset-base}/css/exanpe-t5-lib-skin.css" })
public class Layout
{
    /** The page title, for the <title> element and the <h1>element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Inject
    private RequestGlobals requestGlobals;

    @Inject
    private RooMeetingSecurityContext securityContext;

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
}
