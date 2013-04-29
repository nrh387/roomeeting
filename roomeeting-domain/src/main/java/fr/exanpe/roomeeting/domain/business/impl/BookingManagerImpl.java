/**
 * 
 */
package fr.exanpe.roomeeting.domain.business.impl;

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
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.business.impl.DefaultManagerImpl;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Room;

@Service
public class BookingManagerImpl extends DefaultManagerImpl<Booking, Long> implements BookingManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingManagerImpl.class);

    @Autowired
    private BookingDAO bookingDAO;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Room> searchRoomAvailable(RoomFilter filter)
    {
        int tries = 5;
        int count = 0;

        List<Room> rooms = null;

        while (CollectionUtils.isEmpty(rooms) && count < tries)
        {
            if (count > 0)
            {
                filter.setDate(RoomDateUtils.nextWorkingDate(filter.getDate()));
            }

            rooms = bookingDAO.searchRoomAvailable(filter);
            count++;
        }

        return rooms;
    }
}
