package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

/**
 * Layout component for pages of application roomeeting-web.
 */
@Import(stylesheet =
{ "context:css/bootstrap-roomeeting.css", "context:css/roomeeting.css", "${exanpe.asset-base}/css/exanpe-t5-lib-skin.css",
        "context:bootstrap/datepicker/datepicker.css" })
public class Layout
{
    /** The page title, for the <title> element and the <h1>element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    /** The page title, for the <title> element and the <h1>element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, value = "true")
    private String bar;

    @Inject
    private RequestGlobals requestGlobals;

    public String getContextRoot()
    {
        return requestGlobals.getHTTPServletRequest().getContextPath();
    }
}
