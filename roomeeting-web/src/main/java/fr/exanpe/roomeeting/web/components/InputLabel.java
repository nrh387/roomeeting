package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class InputLabel
{
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String forId;

    @Parameter(defaultPrefix = BindingConstants.MESSAGE)
    @Property
    private String label;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property
    private boolean required;
}
