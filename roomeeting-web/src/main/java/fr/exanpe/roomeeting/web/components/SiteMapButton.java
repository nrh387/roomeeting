package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class SiteMapButton
{
    @Parameter(allowNull = false, required = true)
    @Property
    private String latitude;

    @Parameter(allowNull = false, required = true)
    @Property
    private String longitude;
}
