package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class SumUpEntry
{
    @Property
    @Parameter(defaultPrefix = BindingConstants.MESSAGE)
    private String name;
}
