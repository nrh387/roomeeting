/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import java.util.List;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.dto.DateAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.User;

public interface BookingManager extends DefaultManager<Booking, Long>
{

    List<DateAvailabilityDTO> searchRoomAvailable(RoomFilter filter);

    Gap findGap(Long gapId);

    Booking processBooking(User user, Gap bookGap, TimeSlot from, TimeSlot to) throws BusinessException;

    void deleteBooking(Long id);

    List<Booking> listUserFuturesBookings(User u);
}
