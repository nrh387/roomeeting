/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import java.util.List;

import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.Booking;

public interface BookingManager extends DefaultManager<Booking, Long>
{

    List<RoomAvailabilityDTO> searchRoomAvailable(RoomFilter filter);

}
