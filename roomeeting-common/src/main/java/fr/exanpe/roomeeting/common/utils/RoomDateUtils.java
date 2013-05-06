package fr.exanpe.roomeeting.common.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public final class RoomDateUtils
{
    private RoomDateUtils()
    {
    }

    /**
     * Return the next working date, excluding {@link Calendar#SATURDAY} and {@link Calendar#SUNDAY}
     */
    public static Date nextWorkingDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        do
        {
            c.add(Calendar.DATE, 1);
        }
        while (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

        return c.getTime();
    }

    /**
     * Return the next working date, excluding {@link Calendar#SATURDAY} and {@link Calendar#SUNDAY}
     */
    public static Date nextDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DATE, 1);

        return c.getTime();
    }

    public static Date setHourMinutes(Date date, int hour, int minutes)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minutes);

        c = DateUtils.truncate(c, Calendar.MINUTE);

        return c.getTime();
    }
}
