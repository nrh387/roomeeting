package fr.exanpe.roomeeting.web.util;

import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.t5.lib.model.gmap.GMapMarkerModel;

public class GMapUtils
{
    public static GMapMarkerModel toMarker(Site s)
    {
        GMapMarkerModel model = new GMapMarkerModel();
        model.setInfo(s.getName());
        model.setTitle(s.getName());
        model.setLatitude(s.getLatitude());
        model.setLongitude(s.getLongitude());
        model.setId("" + s.getId());
        return model;
    }
}
