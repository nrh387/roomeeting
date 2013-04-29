package fr.exanpe.roomeeting.common.utils;

import java.util.Calendar;
import java.util.Date;

public final class RoomDateUtils
{
    private RoomDateUtils()
    {
    }

    /**
     * Return the next working date, excluding {@link Calendar#SATURDAY} and {@link Calendar#SUNDAY}
     */
    public static Date nextWorkingDate(Date date)
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
}
