package fr.exanpe.roomeeting.domain.business.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.exanpe.roomeeting.common.utils.RoomDateUtils;
import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.model.Booking;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

@Component
public class BookingDAO
{
    @Autowired
    private CrudDAO crudDAO;

    @PersistenceContext
    protected EntityManager entityManager;

    public List<Room> searchRoomAvailable(RoomFilter filter, Date dateSearch)
    {
        /*
         * SELECT r FROM Room r
         * WHERE r.capacity >= :capacity
         * AND r.site = :site
         * AND (NOT EXISTS(SELECT 1 FROM Gap g WHERE g.date = :date AND g.room = r)
         * OR EXISTS (SELECT 1 FROM Gap g2 WHERE g2.date = :date and g2.room = r and
         * g2.minutesLength >= :minutesLength))
         * ORDER BY r.capacity"
         */

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> q = cb.createQuery(Room.class);
        Root<Room> objectRoom = q.from(Room.class);

        objectRoom.fetch("site", JoinType.LEFT);

        q.select(objectRoom);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(cb.ge(objectRoom.<Integer> get("capacity"), filter.getCapacity()));

        if (filter.getSite() != null)
        {
            predicates.add(cb.equal(objectRoom.<Site> get("site"), filter.getSite()));
        }
        if (filter.getName() != null)
        {
            predicates.add(cb.like(cb.upper(objectRoom.<String> get("name")), StringUtils.upperCase(filter.getName()) + "%"));
        }
        if (CollectionUtils.isNotEmpty(filter.getFeatures()))
        {
            for (RoomFeature rf : filter.getFeatures())
            {
                predicates.add(cb.isMember(rf, objectRoom.<List<RoomFeature>> get("features")));
            }
        }

        // no booking
        Subquery<Booking> subqueryNot = q.subquery(Booking.class);
        Root<Booking> objectNotBooking = subqueryNot.from(Booking.class);
        subqueryNot.select(objectNotBooking);
        subqueryNot.where(new Predicate[]
        { cb.equal(objectNotBooking.<Date> get("date"), dateSearch), cb.equal(objectNotBooking.<Room> get("room"), objectRoom) });

        Date fromDate = RoomDateUtils.setHourMinutes(dateSearch, filter.getRestrictFrom().getHours(), filter.getRestrictFrom().getMinutes());
        if (fromDate.before(new Date()))
        {
            fromDate = new Date();
        }

        // already checked
        Date toDate = RoomDateUtils.setHourMinutes(dateSearch, filter.getRestrictTo().getHours(), filter.getRestrictTo().getMinutes());

        // or gap available
        Subquery<Gap> subqueryGap = q.subquery(Gap.class);
        Root<Gap> objectGap = subqueryGap.from(Gap.class);
        subqueryGap.select(objectGap);

        subqueryGap.where(new Predicate[]
        {
                cb.equal(objectGap.<Date> get("date"), dateSearch),
                cb.equal(objectGap.<Room> get("room"), objectRoom),
                cb.or(
                        cb.between(objectGap.<Date> get("startTime"), sqlTime(fromDate), sqlTime(toDate)),
                        cb.between(objectGap.<Date> get("endTime"), sqlTime(fromDate), sqlTime(toDate))) });

        // or gap available inner
        // Subquery<Gap> subqueryGapInner = q.subquery(Gap.class);
        // Root<Gap> objectGapInner = subqueryGapInner.from(Gap.class);
        // subqueryGapInner.select(objectGap);
        // subqueryGapInner.where(new Predicate[]
        // { cb.equal(objectGapInner.<Date> get("date"), dateSearch), cb.equal(objectGapInner.<Room>
        // get("room"), objectRoom),
        // cb.lessThanOrEqualTo(objectGapInner.<Integer> get("startHour"), fromDate.getHours()),
        // cb.greaterThanOrEqualTo(objectGapInner.<Integer> get("endHour"), toDate.getHours()) });

        predicates.add(cb.or(cb.not(cb.exists(subqueryNot)), cb.exists(subqueryGap)));

        q.where(predicates.toArray(new Predicate[predicates.size()]));

        q.orderBy(cb.asc(objectRoom.<Site> get("site")), cb.asc(objectRoom.<Integer> get("capacity")));

        return crudDAO.findCriteriaQuery(q);

        // return crudDAO.findWithNamedQuery(
        // Room.SEARCH_ROOM_AVAILABLE,
        // QueryParameters.with("capacity", filter.getCapacity()).and("date",
        // filter.getDate()).and("minutesLength", filter.getMinutesLength())
        // .and("site", filter.getSite()).parameters());
    }

    private java.sql.Time sqlTime(Date d)
    {
        return new java.sql.Time(d.getTime());
    }
}
