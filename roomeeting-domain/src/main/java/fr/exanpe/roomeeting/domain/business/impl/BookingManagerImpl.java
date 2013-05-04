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

import fr.exanpe.roomeeting.common.utils.RoomDateUtils;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.dao.BookingDAO;
import fr.exanpe.roomeeting.domain.business.dto.DateAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTOBuilder;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.core.dao.QueryParameters;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;

@Service
public class BookingManagerImpl extends DefaultManagerImpl<Booking, Long> implements BookingManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingManagerImpl.class);

    @Autowired
    private BookingDAO bookingDAO;

    @PersistenceContext
    protected EntityManager entityManager;

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
            Date toDate = RoomDateUtils.setHour(dateSearch, filter.getRestrictTo());
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
}
