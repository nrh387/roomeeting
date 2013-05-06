package fr.exanpe.roomeeting.web.pages.room;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.business.dto.DateAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.SearchAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.SiteAvailabilityDTO;
import fr.exanpe.roomeeting.domain.model.Gap;

public class Choose
{
    @SessionAttribute
    private SearchAvailabilityDTO search;

    @Property
    private List<DateAvailabilityDTO> dateAvailabilitiesDTO;

    @Property
    private DateAvailabilityDTO currentDateAvailabilityDTO;

    @Property
    private SiteAvailabilityDTO currentSiteAvailabilityDTO;

    @Property
    private RoomAvailabilityDTO currentRoomAvailabilityDTO;

    @Property
    private Gap currentGap;

    @Inject
    private ParameterManager parameterManager;

    @Inject
    private SiteManager siteManager;

    @Inject
    private BookingManager bookingManager;

    @SessionAttribute
    private Gap bookGap;

    @Property
    @Persist(PersistenceConstants.SESSION)
    private Integer hourDayStart;

    @Property
    @Persist(PersistenceConstants.SESSION)
    private Integer hourDayEnd;

    @Property
    private Integer currentHour;

    Object onActivate()
    {
        if (search == null) { return Search.class; }

        // backward compat
        dateAvailabilitiesDTO = search.getDates();

        if (hourDayStart == null)
        {
            hourDayStart = parameterManager.find(ParameterEnum.HOUR_DAY_START.getCode()).getIntegerValue();
            hourDayEnd = parameterManager.find(ParameterEnum.HOUR_DAY_END.getCode()).getIntegerValue();
        }

        bookGap = null;

        return null;
    }

    @OnEvent(value = EventConstants.ACTION, component = "book")
    Object book(Long gapId)
    {
        bookGap = bookingManager.findGap(gapId);

        return Book.class;
    }

    @OnEvent(value = EventConstants.ACTION, component = "bookDay")
    Object bookDay(Long roomId, Date date)
    {
        Gap g = new Gap();
        g.setRoom(siteManager.findRoom(roomId));
        g.setDate(date);
        g.setStartHour(hourDayStart);
        g.setEndHour(hourDayEnd);

        bookGap = g;

        return Book.class;
    }

    public boolean isFullyFree()
    {
        return CollectionUtils.isEmpty(currentRoomAvailabilityDTO.getGaps());
    }

    public boolean isFree(int hour, int minutes)
    {
        if (CollectionUtils.isEmpty(currentRoomAvailabilityDTO.getGaps())) { return true; }

        for (Gap g : currentRoomAvailabilityDTO.getGaps())
        {
            if (g.getStartHour() > hour || g.getEndHour() < hour)
            {
                // gap out
                continue;
            }
            if (g.getStartHour() < hour || (g.getStartMinutes() <= minutes))
            {
                if (g.getEndHour() > hour || (g.getEndMinutes() >= minutes)) { return true; }
            }
        }
        return false;
    }

    public String toStringNumberDate(Date d)
    {
        return FastDateFormat.getInstance("yyyyMMdd").format(d);
    }
}
