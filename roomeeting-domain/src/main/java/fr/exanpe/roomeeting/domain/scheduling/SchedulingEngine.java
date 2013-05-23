/**
 * 
 */
package fr.exanpe.roomeeting.domain.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import fr.exanpe.roomeeting.domain.business.BookingManager;

@Service
public class SchedulingEngine
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingEngine.class);

    @Autowired
    private BookingManager bookingManager;

    /**
     * Fire everyday at 2 am
     */
    @Scheduled(cron = "${database.purge.gaps}")
    public void purgeGaps()
    {
        LOGGER.info("Purging Gaps...");

        int number = bookingManager.purgeGaps();

        LOGGER.info(number + " gaps purge complete");
    }
}
