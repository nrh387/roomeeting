package fr.exanpe.roomeeting.web.pages.room;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.model.Booking;

public class ConfirmBooking
{
    @SessionAttribute
    @Property
    private Booking booking;

    Object onActivate()
    {
        if (booking == null) { return Search.class; }

        return null;
    }

    @OnEvent(value = EventConstants.ACTION, component = "exportOutlook")
    void exportOutlook() throws BusinessException
    {

    }
}
