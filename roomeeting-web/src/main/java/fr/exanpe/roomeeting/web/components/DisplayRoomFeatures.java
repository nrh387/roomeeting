package fr.exanpe.roomeeting.web.components;

import java.util.List;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

public class DisplayRoomFeatures
{
    @Parameter
    @Property
    private List<RoomFeature> features;

    @Property
    private RoomFeature currentFeature;

}
