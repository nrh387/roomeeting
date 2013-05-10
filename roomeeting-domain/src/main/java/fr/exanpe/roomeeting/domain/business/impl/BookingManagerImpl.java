/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.exanpe.roomeeting.common.enums.ParameterEnum;
import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.common.exception.TechnicalException;
import fr.exanpe.roomeeting.common.utils.RoomDateUtils;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
import fr.exanpe.roomeeting.domain.business.consts.ErrorMessages;
import fr.exanpe.roomeeting.domain.business.dao.BookingDAO;
import fr.exanpe.roomeeting.domain.business.dto.DateAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTOBuilder;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.core.dao.QueryParameters;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.User;

@Service
public class BookingManagerImpl extends DefaultManagerImpl<Booking, Long> implements BookingManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingManagerImpl.class);

    @Autowired
    private BookingDAO bookingDAO;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private ParameterManager parameterManager;

    @Autowired
    private CrudDAO crudDAO;

    @Override
    public List<DateAvailabilityDTO> searchRoomAvailable(RoomFilter filter)
    {
        int days = 0;
        int daysSearch = filter.getExtendDays();

        List<Room> rooms = null;

        Date dateSearch = filter.getDate();

        while (CollectionUtils.isEmpty(rooms) && days <= daysSearch)
        {
            if (days > 0)
            {
                if (filter.isExtendWorkingOnly())
                {
                    dateSearch = RoomDateUtils.nextWorkingDay(filter.getDate());
                }
                else
                {
                    dateSearch = RoomDateUtils.nextWorkingDay(filter.getDate());
                }
            }

            // anterior date
            Date toDate = RoomDateUtils.setHourMinutes(dateSearch, filter.getRestrictTo().getHours(), filter.getRestrictTo().getMinutes());
            if (toDate.before(new Date()))
            {
                days++;
                continue;
            }

            // TODO ajouter restrict from et to
            // TODO do not check previous dates...
            rooms = bookingDAO.searchRoomAvailable(filter, dateSearch);

            days++;
        }

        if (CollectionUtils.isEmpty(rooms)) { return new ArrayList<DateAvailabilityDTO>(); }

        List<Gap> gaps = crudDAO.findWithNamedQuery(
                Room.FIND_GAPS_FOR_DATE,
                QueryParameters.with("date", dateSearch).and("rooms", rooms).and("startTime", toTime(dateSearch, filter.getRestrictFrom()))
                        .and("endTime", toTime(dateSearch, filter.getRestrictTo())).parameters());

        return consolidateRoomAndGaps(rooms, gaps, dateSearch);
    }

    private List<DateAvailabilityDTO> consolidateRoomAndGaps(List<Room> rooms, List<Gap> gaps, Date dateSearch)
    {
        RoomAvailabilityDTOBuilder builder = RoomAvailabilityDTOBuilder.create();

        for (Room room : rooms)
        {
            builder.organise(room, dateSearch);
        }

        for (Gap gap : gaps)
        {
            builder.organise(gap, dateSearch);
        }
        return builder.getResult();
    }

    @Override
    public Gap findGap(Long gapId)
    {
        return crudDAO.find(Gap.class, gapId);
    }

    @Override
    public Booking processBooking(User user, Gap bookGap, TimeSlot from, TimeSlot to) throws BusinessException
    {
        List<Gap> gaps = crudDAO.findWithNamedQuery(Gap.FIND_GAP_AROUND_TIMESLOT, QueryParameters.with("date", bookGap.getDate())
                .and("room", bookGap.getRoom()).and("startTime", toTime(bookGap.getDate(), from)).and("endTime", toTime(bookGap.getDate(), from)).parameters());

        // no gap around... check for a booking
        if (CollectionUtils.isEmpty(gaps))
        {
            if (CollectionUtils.isNotEmpty(crudDAO.findWithNamedQuery(
                    Booking.FIND_BOOKING_FOR_DATE,
                    QueryParameters.with("date", bookGap.getDate()).and("room", bookGap.getRoom()).parameters()))) { throw new TechnicalException(
                    ErrorMessages.INCONSISTENT_DATABASE); }
            return bookEmptyDay(user, bookGap, from, to);
        }
        if (gaps.size() > 1) { throw new TechnicalException(ErrorMessages.INCONSISTENT_DATABASE); }

        return bookOnGap(gaps.get(0), user, bookGap, from, to);
    }

    private Booking bookOnGap(Gap gap, User user, Gap bookGap, TimeSlot from, TimeSlot to)
    {
        boolean onGapBound = false;
        boolean perfectMatch = false;

        // booking start on last booking end
        if (from.getHours() == gap.getStartHour() && from.getMinutes() == gap.getStartMinute())
        {
            // alter gap
            gap.setStartHour(to.getHours());
            gap.setStartMinute(to.getMinutes());
            onGapBound = true;
        }
        // booking end on next booking start
        if (to.getHours() == gap.getEndHour() && to.getMinutes() == gap.getEndMinute())
        {
            // alter gap
            gap.setEndHour(from.getHours());
            gap.setEndMinute(from.getMinutes());
            perfectMatch = onGapBound;
            onGapBound = true;
        }

        if (onGapBound)
        {
            if (perfectMatch)
            {
                crudDAO.delete(Gap.class, gap.getId());
            }
            else
            {
                crudDAO.update(gap);
            }
        }
        else
        {
            Gap gapEnd = new Gap();
            gapEnd.setDate(bookGap.getDate());
            gapEnd.setStartHour(to.getHours());
            gapEnd.setStartMinute(to.getMinutes());

            gapEnd.setEndHour(gap.getEndHour());
            gapEnd.setEndMinute(gap.getEndMinute());

            gapEnd.setRoom(bookGap.getRoom());

            crudDAO.create(gapEnd);

            // booking in the middle of a gap
            gap.setEndHour(from.getHours());
            gap.setEndMinute(from.getMinutes());
            crudDAO.update(gap);
        }

        Booking booking = new Booking();

        booking.setDate(bookGap.getDate());
        booking.setStartHour(from.getHours());
        booking.setStartMinute(from.getMinutes());

        booking.setEndHour(to.getHours());
        booking.setEndMinute(to.getMinutes());

        booking.setRoom(bookGap.getRoom());
        booking.setUser(user);

        booking = crudDAO.create(booking);

        return booking;
    }

    private Booking bookEmptyDay(User user, Gap bookGap, TimeSlot from, TimeSlot to)
    {
        // booking
        Booking booking = new Booking();

        booking.setDate(bookGap.getDate());
        booking.setStartHour(from.getHours());
        booking.setStartMinute(from.getMinutes());

        booking.setEndHour(to.getHours());
        booking.setEndMinute(to.getMinutes());

        booking.setRoom(bookGap.getRoom());
        booking.setUser(user);

        booking = crudDAO.create(booking);

        if (!isFirstHour(from))
        {
            // before
            Gap before = new Gap();

            before.setDate(bookGap.getDate());
            before.setStartHour(parameterManager.find(ParameterEnum.HOUR_DAY_START.getCode()).getIntegerValue());
            before.setStartMinute(0);

            before.setEndHour(from.getHours());
            before.setEndMinute(from.getMinutes());

            before.setRoom(bookGap.getRoom());

            crudDAO.create(before);
        }

        if (!isLastHour(to))
        {
            // after
            Gap after = new Gap();

            after.setDate(bookGap.getDate());
            after.setStartHour(to.getHours());
            after.setStartMinute(to.getMinutes());

            after.setEndHour(parameterManager.find(ParameterEnum.HOUR_DAY_END.getCode()).getIntegerValue());
            after.setEndMinute(0);

            after.setRoom(bookGap.getRoom());

            crudDAO.create(after);
        }

        return booking;
    }

    private boolean isFirstHour(TimeSlot ts)
    {
        return ts.getHours() == parameterManager.find(ParameterEnum.HOUR_DAY_START.getCode()).getIntegerValue() && ts.getMinutes() == 0;
    }

    private boolean isLastHour(TimeSlot ts)
    {
        return ts.getHours() == parameterManager.find(ParameterEnum.HOUR_DAY_END.getCode()).getIntegerValue() && ts.getMinutes() == 0;
    }

    private Date toTime(Date d, TimeSlot ts)
    {
        return RoomDateUtils.setHourMinutes(d, ts.getHours(), ts.getMinutes());
    }
}
