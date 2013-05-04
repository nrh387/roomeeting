package fr.exanpe.roomeeting.web.components;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import fr.exanpe.t5.lib.model.gmap.GMapMarkerModel;

public class SiteMapModal
{
    @Parameter(defaultPrefix = BindingConstants.PROP)
    @Property
    private List<GMapMarkerModel> markers;

    @Property
    private GMapMarkerModel currentMarker;
}
