/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTO;
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
    public List<RoomAvailabilityDTO> searchRoomAvailable(RoomFilter filter)
    {
        int days = 0;
        int daysSearch = filter.getExtendDays();

        List<Room> rooms = null;

        Date dateSearch = filter.getDate();

        // TODO consolidate multiple days
        List<Room> fullRooms = new ArrayList<Room>();

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

            // TODO ajouter restrict from et to
            // TODO do not check previous dates...
            rooms = bookingDAO.searchRoomAvailable(filter, dateSearch);

            days++;
        }

        if (CollectionUtils.isEmpty(rooms)) { return new ArrayList<RoomAvailabilityDTO>(); }

        List<Gap> gaps = crudDAO.findWithNamedQuery(Room.FIND_GAPS_FOR_DATE, QueryParameters.with("date", dateSearch).and("rooms", rooms).parameters());

        return consolidateRoomAndGaps(rooms, gaps, dateSearch);
    }

    private List<RoomAvailabilityDTO> consolidateRoomAndGaps(List<Room> rooms, List<Gap> gaps, Date dateSearch)
    {
        List<RoomAvailabilityDTO> searchDTO = new ArrayList<RoomAvailabilityDTO>();

        Map<Long, RoomAvailabilityDTO> roomMapDto = new HashMap<Long, RoomAvailabilityDTO>();

        for (Room room : rooms)
        {
            if (!roomMapDto.containsKey(room.getId()))
            {
                RoomAvailabilityDTO search = new RoomAvailabilityDTO(room);
                search.setDate(dateSearch);

                roomMapDto.put(room.getId(), search);
                searchDTO.add(search);
            }
        }

        for (Gap g : gaps)
        {
            if (!roomMapDto.containsKey(g.getRoom().getId())) { throw new IllegalStateException("Gap returned, but not room..."); }
            roomMapDto.get(g.getRoom().getId()).addGap(g);
        }

        return searchDTO;
    }
}
