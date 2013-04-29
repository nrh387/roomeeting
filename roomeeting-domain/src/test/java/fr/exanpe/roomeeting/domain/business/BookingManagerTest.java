package fr.exanpe.roomeeting.domain.business;

import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByType;

import fr.exanpe.roomeeting.domain.base.RooMeetingDomainBaseTest;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;

@DataSet("/dataset/BookingTest.xml")
public class BookingManagerTest extends RooMeetingDomainBaseTest
{

    @SpringBeanByType
    private BookingManager bookingManager;

    @SpringBeanByType
    private SiteManager siteManager;

    @Test
    public void searchRoomAvailableCapacity()
    {
        Site site = siteManager.find(1L);

        // 10
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(10);
        rf.setDate(new Date());
        rf.setMinutesLength(30);
        rf.setSite(site);

        List<Room> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);

        // 5
        rf.setCapacity(5);

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        // 50
        rf.setCapacity(50);

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    public void searchRoomAvailableSite()
    {
        Site site = siteManager.find(1L);

        // 10
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(1);
        rf.setDate(new Date());
        rf.setMinutesLength(30);
        rf.setSite(site);

        List<Room> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 2);

        // 5
        rf.setSite(siteManager.find(2L));

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);

        // test without filter
        rf.setSite(null);
        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 3);
    }

}
