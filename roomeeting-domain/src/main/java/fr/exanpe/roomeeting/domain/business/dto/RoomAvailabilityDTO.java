package fr.exanpe.roomeeting.domain.business.dto;

import java.util.ArrayList;
import java.util.List;

import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;

public class RoomAvailabilityDTO
{
    private Room room;

    private List<Gap> gaps = new ArrayList<Gap>();

    public RoomAvailabilityDTO(Room room)
    {
        super();
        this.room = room;
    }

    /**
     * @return the room
     */
    public Room getRoom()
    {
        return room;
    }

    /**
     * @return the gaps
     */
    public List<Gap> getGaps()
    {
        return gaps;
    }

}
