package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

public class BodyTitle
{
    @Parameter(defaultPrefix = BindingConstants.MESSAGE)
    @Property
    private String title;

    @Inject
    private ComponentResources componentResources;

    @SetupRender
    void init()
    {
        if (title == null)
        {
            title = componentResources.getContainerMessages().get("title");
        }
    }
}
