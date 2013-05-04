package fr.exanpe.roomeeting.web.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.web.util.GMapUtils;
import fr.exanpe.t5.lib.model.gmap.GMapMarkerModel;

public class SiteDescription
{
    @Property
    @Parameter(defaultPrefix = BindingConstants.PROP)
    private Site site;

    public boolean hasMap()
    {
        return StringUtils.isNotEmpty(site.getLatitude()) && StringUtils.isNotEmpty(site.getLongitude());
    }

    public List<GMapMarkerModel> getMarkers()
    {
        List<GMapMarkerModel> l = new ArrayList<GMapMarkerModel>();
        l.add(GMapUtils.toMarker(site));
        return l;
    }
}
