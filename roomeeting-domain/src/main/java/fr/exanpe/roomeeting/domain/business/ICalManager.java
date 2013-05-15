/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import fr.exanpe.roomeeting.domain.model.Booking;

/**
 * Gestion des calendrier iCal
 * 
 * @author jmaupoux
 */
public interface ICalManager
{
    String generateICal(Booking booking);

    String generateICalName(Booking booking);
}
