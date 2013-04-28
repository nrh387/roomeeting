package fr.exanpe.roomeeting.web.pages.room;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;

public class Search
{
    @Persist
    @Property
    private RoomFilter filter;

    void onActivate()
    {
        if (filter == null)
        {
            filter = new RoomFilter();
        }
    }

    @OnEvent(value = "search")
    public void search()
    {
        System.out.println("search ok");
    }

    public String toStringDate(Date d)
    {
        return FastDateFormat.getInstance("dd/MM/yyyy").format(d);
    }

    public Date getToday()
    {
        return new Date();
    }
}
