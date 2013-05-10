package fr.exanpe.roomeeting.domain.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBeanByType;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.base.RooMeetingDomainBaseTest;
import fr.exanpe.roomeeting.domain.business.dto.DateAvailabilityDTO;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

public class BookingManagerTest extends RooMeetingDomainBaseTest
{
    @SpringBeanByType
    private BookingManager bookingManager;

    @SpringBeanByType
    private SiteManager siteManager;

    @SpringBeanByType
    private RoomFeatureManager roomFeatureManager;

    private TimeSlot createTS(int hour)
    {
        return new TimeSlot(hour, 0);
    }

    private RoomFilter createFilter()
    {
        RoomFilter rf = new RoomFilter();
        rf.setRestrictFrom(createTS(8));
        rf.setRestrictTo(createTS(20));
        try
        {
            rf.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2020"));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return rf;
    }

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailable.xml")
    public void searchRoomAvailableFilterCapacity()
    {
        Site site = siteManager.find(1L);

        // 10
        RoomFilter rf = createFilter();
        rf.setCapacity(10);
        rf.setSite(site);

        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().size(), 1);

        // 5
        rf.setCapacity(5);

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().size(), 2);

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
        RoomFilter rf = createFilter();
        rf.setCapacity(1);
        rf.setSite(site);

        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().size(), 2);

        // 5
        rf.setSite(siteManager.find(2L));

        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertNotNull(list);
        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().size(), 1);

        // test without filter
        rf.setSite(null);
        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(1).getRoomAvailabilityDTOs().size(), 1);
        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().size(), 2);
    }

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailable.xml")
    public void searchRoomAvailableFilterName()
    {
        RoomFilter rf = createFilter();
        rf.setCapacity(1);
        rf.setName("ro");

        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);
        Assert.assertEquals("Rose", list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().get(0).getRoom().getName());
    }

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailableGaps.xml")
    public void searchRoomAvailableGaps() throws ParseException
    {
        RoomFilter rf = createFilter();
        rf.setCapacity(1);
        rf.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));

        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);

        // minutes length
        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);

        // extendDays
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
        RoomFilter rf = createFilter();
        rf.setCapacity(10);

        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

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
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailableGaps.xml")
    public void searchRoomAvailableGap() throws ParseException
    {
        RoomFilter rf = createFilter();
        rf.setCapacity(1);
        rf.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));
        // minutes length
        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().size(), 1);
        Assert.assertEquals(list.get(0).getSiteAvailabilityDTOs().get(0).getRoomAvailabilityDTOs().get(0).getGaps().size(), 1);
    }

    @Test
    @DataSet("/dataset/BookingManagerTest-searchRoomAvailableGaps.xml")
    public void searchRoomAvailableGapRestrict() throws ParseException
    {
        RoomFilter rf = createFilter();
        rf.setRestrictFrom(createTS(11));
        rf.setCapacity(1);
        rf.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));
        // minutes length
        List<DateAvailabilityDTO> list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);

        rf.setRestrictFrom(createTS(0));
        rf.setRestrictTo(createTS(9));
        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 0);

        rf.setRestrictFrom(createTS(10));
        rf.setRestrictTo(createTS(11));
        list = bookingManager.searchRoomAvailable(rf);

        Assert.assertEquals(list.size(), 1);
    }

    @Test
    @DataSet(value = "/dataset/BookingManagerTest-processBookingNewDay.xml")
    @ExpectedDataSet("/dataset/expected/BookingManagerTest-processBookingNewDay.xml")
    public void processBookingNewDay() throws ParseException, BusinessException
    {
        User u = new User();
        u.setId(1L);

        Gap bookGap = new Gap();
        bookGap.setRoom(siteManager.findRoom(10L));
        bookGap.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));

        TimeSlot from = new TimeSlot(11, 30);
        TimeSlot to = new TimeSlot(12, 0);

        Booking booking = bookingManager.processBooking(u, bookGap, from, to);

        Assert.assertNotNull(booking);
    }

    @Test
    @DataSet(value = "/dataset/BookingManagerTest-processBookingNewDay.xml")
    @ExpectedDataSet("/dataset/expected/BookingManagerTest-processBookingNewDayBorder.xml")
    public void processBookingNewDayBorder() throws ParseException, BusinessException
    {
        User u = new User();
        u.setId(1L);

        Gap bookGap = new Gap();
        bookGap.setRoom(siteManager.findRoom(10L));
        bookGap.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));

        TimeSlot from = new TimeSlot(9, 0);
        TimeSlot to = new TimeSlot(12, 0);

        Booking booking = bookingManager.processBooking(u, bookGap, from, to);

        Assert.assertNotNull(booking);
    }

    @Test
    @DataSet(value = "/dataset/BookingManagerTest-processBookingDay.xml")
    @ExpectedDataSet("/dataset/expected/BookingManagerTest-processBookingDayInner.xml")
    public void processBookingDayInner() throws ParseException, BusinessException
    {
        User u = new User();
        u.setId(1L);

        Gap bookGap = new Gap();
        bookGap.setRoom(siteManager.findRoom(10L));
        bookGap.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));

        TimeSlot from = new TimeSlot(9, 30);
        TimeSlot to = new TimeSlot(10, 30);

        Booking booking = bookingManager.processBooking(u, bookGap, from, to);

        Assert.assertNotNull(booking);
    }

    @Test
    @DataSet(value = "/dataset/BookingManagerTest-processBookingDay.xml")
    @ExpectedDataSet("/dataset/expected/BookingManagerTest-processBookingDayFullGap.xml")
    public void processBookingDayInnerFullGap() throws ParseException, BusinessException
    {
        User u = new User();
        u.setId(1L);

        Gap bookGap = new Gap();
        bookGap.setRoom(siteManager.findRoom(10L));
        bookGap.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019"));

        TimeSlot from = new TimeSlot(9, 00);
        TimeSlot to = new TimeSlot(11, 30);

        Booking booking = bookingManager.processBooking(u, bookGap, from, to);

        Assert.assertNotNull(booking);
    }
}
