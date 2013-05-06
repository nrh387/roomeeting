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
import fr.exanpe.roomeeting.common.utils.RoomDateUtils;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.ParameterManager;
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

        List<Gap> gaps = crudDAO.findWithNamedQuery(Room.FIND_GAPS_FOR_DATE, QueryParameters.with("date", dateSearch).and("rooms", rooms).parameters());

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
    public void processBooking(User user, Gap bookGap, TimeSlot from, TimeSlot to) throws BusinessException
    {
        List<Gap> gaps = crudDAO.findWithNamedQuery(Gap.FIND_GAP_AROUND_TIMESLOT, QueryParameters.with("date", bookGap.getDate())
                .and("room", bookGap.getRoom()).and("startHour", from.getHours()).and("endHour", to.getHours()).parameters());

        // no gap around... check for a booking
        if (CollectionUtils.isEmpty(gaps))
        {
            if (CollectionUtils.isNotEmpty(crudDAO.findWithNamedQuery(
                    Booking.FIND_BOOKING_FOR_DATE,
                    QueryParameters.with("date", bookGap.getDate()).and("room", bookGap.getRoom()).parameters()))) { throw new BusinessException(
                    "Inconsistent database. Booking found without gap"); }
            bookEmptyDay(user, bookGap, from, to);
            return;
        }
        if (gaps.size() > 1) { throw new BusinessException("Inconsistent database. Multiple gap for selected time slot"); }

    }

    private void bookEmptyDay(User user, Gap bookGap, TimeSlot from, TimeSlot to)
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

        // before
        Gap before = new Gap();

        before.setDate(bookGap.getDate());
        before.setStartHour(parameterManager.find(ParameterEnum.HOUR_DAY_START.getCode()).getIntegerValue());
        before.setStartMinute(0);

        before.setEndHour(from.getHours());
        before.setEndMinute(from.getMinutes());

        before.setRoom(bookGap.getRoom());

        // after
        Gap after = new Gap();

        after.setDate(bookGap.getDate());
        after.setStartHour(to.getHours());
        after.setStartMinute(to.getMinutes());

        after.setEndHour(parameterManager.find(ParameterEnum.HOUR_DAY_END.getCode()).getIntegerValue());
        after.setEndMinute(0);

        after.setRoom(bookGap.getRoom());

        crudDAO.create(booking);
        crudDAO.create(before);
        crudDAO.create(after);
    }
}
