/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import java.util.List;

import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Room;

public interface BookingManager extends DefaultManager<Booking, Long>
{

    List<Room> searchRoomAvailable(RoomFilter filter);

}
