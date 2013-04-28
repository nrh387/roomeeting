package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Box
{
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String title;
}
