package fr.exanpe.roomeeting.domain.business.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;

public class RoomAvailabilityDTO
{
    private Room room;

    private List<Gap> gaps = new ArrayList<Gap>();

    private Date date;

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
     * @param room the room to set
     */
    public void setRoom(Room room)
    {
        this.room = room;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * @return the gap
     */
    public List<Gap> getGaps()
    {
        return gaps;
    }

    /**
     * @param gap the gap to set
     */
    public void setGaps(List<Gap> gap)
    {
        this.gaps = gap;
    }

    public void addGap(Gap gap)
    {
        this.gaps.add(gap);
    }
}
