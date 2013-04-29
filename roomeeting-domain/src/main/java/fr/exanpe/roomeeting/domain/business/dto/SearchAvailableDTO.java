package fr.exanpe.roomeeting.domain.business.dto;

import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;

public class SearchAvailableDTO
{
    private Room room;

    private Gap gap;

    public SearchAvailableDTO(Room room)
    {
        this(room, null);
    }

    public SearchAvailableDTO(Room room, Gap gap)
    {
        super();
        this.room = room;
        this.gap = gap;
    }

    /**
     * @return the room
     */
    public Room getRoom()
    {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room room)
    {
        this.room = room;
    }

    /**
     * @return the gap
     */
    public Gap getGap()
    {
        return gap;
    }

    /**
     * @param gap the gap to set
     */
    public void setGap(Gap gap)
    {
        this.gap = gap;
    }

}
