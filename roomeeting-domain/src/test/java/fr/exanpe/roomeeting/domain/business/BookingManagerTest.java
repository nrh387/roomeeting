package fr.exanpe.roomeeting.domain.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByType;

import fr.exanpe.roomeeting.domain.base.RooMeetingDomainBaseTest;
import fr.exanpe.roomeeting.domain.business.dto.RoomAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

public class BookingManagerTest extends RooMeetingDomainBaseTest
{

    @SpringBeanByType
    private BookingManager bookingManager;

    @SpringBeanByType
    private SiteManager siteManager;

    @SpringBeanByType
    private RoomFeatureManager roomFeatureManager;

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailable.xml")
    public void searchRoomAvailableFilterCapacity()
    {
        Site site = siteManager.find(1L);

        // 10
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(10);
        rf.setDate(new Date());
        rf.setMinutesLength(30);
        rf.setSite(site);

        List<RoomAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);
        Assert.assertNotNull(list.get(0).getRoom().getSite());

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
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailable.xml")
    public void searchRoomAvailableFilterSite()
    {
        Site site = siteManager.find(1L);

        // 10
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(1);
        rf.setDate(new Date());
        rf.setMinutesLength(30);
        rf.setSite(site);

        List<RoomAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

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

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailable.xml")
    public void searchRoomAvailableFilterName()
    {
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(1);
        rf.setDate(new Date());
        rf.setMinutesLength(30);
        rf.setName("ro");

        List<RoomAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals("Rose", list.get(0).getRoom().getName());
    }

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailableExtended.xml")
    public void searchRoomAvailableExtended() throws ParseException
    {
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(1);
        rf.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012"));
        rf.setMinutesLength(30);

        List<RoomAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 0);

        // minutes length
        rf.setMinutesLength(10);
        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);

        // extendDays
        rf.setMinutesLength(30);
        rf.setExtendDays(1);
        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);
    }

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailableFeatures.xml")
    public void searchRoomAvailableFeatures()
    {
        RoomFeature r1 = roomFeatureManager.find(1L);
        RoomFeature r2 = roomFeatureManager.find(2L);
        RoomFeature r3 = roomFeatureManager.find(3L);

        // 10
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(10);
        rf.setDate(new Date());
        rf.setMinutesLength(30);

        List<RoomAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);

        rf.getFeatures().add(r1);

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);

        rf.getFeatures().add(r2);

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 1);

        rf.getFeatures().add(r3);

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 0);
    }

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailableExtended.xml")
    public void searchRoomAvailableGap() throws ParseException
    {
        RoomFilter rf = new RoomFilter();
        rf.setCapacity(1);
        rf.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012"));
        // minutes length
        rf.setMinutesLength(10);
        List<RoomAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals(list.get(0).getGaps().size(), 1);
    }

}
