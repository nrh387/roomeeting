package fr.exanpe.roomeeting.web.components;

import java.util.List;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;

public class DisplayRoomAvailability
{
    @Parameter
    @Property
    private Room room;

    @Parameter
    @Property
    private List<Gap> gaps;

}
