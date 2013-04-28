package fr.exanpe.roomeeting.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

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

    void onActivate(@RequestParameter(value = "loginFailed", allowBlank = true)
    boolean loginFailed)
    {
        this.loginFailed = loginFailed;
        this.contextRoot = globals.getRequest().getContextPath();
    }

}
