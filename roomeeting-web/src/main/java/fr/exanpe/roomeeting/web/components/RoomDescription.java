package fr.exanpe.roomeeting.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import fr.exanpe.roomeeting.domain.model.Room;

public class RoomDescription
{
    @Property
    @Parameter(defaultPrefix = BindingConstants.PROP)
    private Room room;
}
