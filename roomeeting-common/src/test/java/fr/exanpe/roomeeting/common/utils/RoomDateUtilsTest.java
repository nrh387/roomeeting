package fr.exanpe.roomeeting.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class RoomDateUtilsTest extends TestNG
{
    @Test
    public void nextWorkingDate() throws ParseException
    {
        // +3
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("26/04/2013");

        d = RoomDateUtils.nextWorkingDate(d);

        Assert.assertEquals(FastDateFormat.getInstance("dd/MM/yyyy").format(d), "29/04/2013");

        // +1

        d = new SimpleDateFormat("dd/MM/yyyy").parse("25/04/2013");

        d = RoomDateUtils.nextWorkingDate(d);

        Assert.assertEquals(FastDateFormat.getInstance("dd/MM/yyyy").format(d), "26/04/2013");
    }
}
