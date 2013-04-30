package fr.exanpe.roomeeting.web.pages.room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTO;

public class ShowAvailability
{
    @SessionAttribute
    @Property
    private List<RoomAvailabilityDTO> roomAvailabilityDTO;

    @Persist
    @Property
    private List<Date> dates;

    @Property
    private Date currentDate;

    private RoomAvailabilityDTO currentRA;

    @Property
    private boolean dateChanged;

    Object onActivate()
    {
        if (roomAvailabilityDTO == null) { return Search.class; }

        dates = new ArrayList<Date>();
        for (RoomAvailabilityDTO r : roomAvailabilityDTO)
        {
            if (!dates.contains(r.getDate()))
            {
                dates.add(r.getDate());
            }
        }

        return null;
    }

    /**
     * @return the currentRA
     */
    public RoomAvailabilityDTO getCurrentRA()
    {
        return currentRA;
    }

    /**
     * @param currentRA the currentRA to set
     */
    public void setCurrentRA(RoomAvailabilityDTO currentRA)
    {
        this.dateChanged = true;

        if (this.currentRA != null)
        {
            if (this.currentRA.getDate().equals(currentRA.getDate()))
            {
                this.dateChanged = false;
            }
        }
        this.currentRA = currentRA;
    }

}
