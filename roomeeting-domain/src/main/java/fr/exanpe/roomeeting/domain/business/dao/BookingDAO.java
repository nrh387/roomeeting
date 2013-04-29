package fr.exanpe.roomeeting.domain.business.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.exanpe.roomeeting.domain.business.filters.RoomFilter;
import fr.exanpe.roomeeting.domain.core.dao.CrudDAO;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;

@Component
public class BookingDAO
{
    @Autowired
    private CrudDAO crudDAO;

    @PersistenceContext
    protected EntityManager entityManager;

    public List<Room> searchRoomAvailable(RoomFilter filter)
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

        q.select(objectRoom);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(cb.ge(objectRoom.<Integer> get("capacity"), filter.getCapacity()));

        if (filter.getSite() != null)
        {
            predicates.add(cb.equal(objectRoom.<Site> get("site"), filter.getSite()));
        }

        Subquery<Gap> subqueryNot = q.subquery(Gap.class);
        Root<Gap> objectNotGap = subqueryNot.from(Gap.class);
        subqueryNot.select(objectNotGap);
        subqueryNot.where(new Predicate[]
        { cb.equal(objectNotGap.<Date> get("date"), filter.getDate()), cb.equal(objectNotGap.<Room> get("room"), objectRoom) });

        Subquery<Gap> subqueryGap = q.subquery(Gap.class);
        Root<Gap> objectGap = subqueryGap.from(Gap.class);
        subqueryGap.select(objectGap);
        subqueryGap.where(new Predicate[]
        { cb.equal(objectGap.<Date> get("date"), filter.getDate()), cb.equal(objectGap.<Room> get("room"), objectRoom),
                cb.ge(objectGap.<Integer> get("minutesLength"), filter.getMinutesLength()) });

        predicates.add(cb.or(cb.not(cb.exists(subqueryNot)), cb.exists(subqueryGap)));

        q.where(predicates.toArray(new Predicate[predicates.size()]));

        q.orderBy(cb.asc(objectRoom.<Integer> get("capacity")));

        return crudDAO.findCriteriaQuery(q);

        // return crudDAO.findWithNamedQuery(
        // Room.SEARCH_ROOM_AVAILABLE,
        // QueryParameters.with("capacity", filter.getCapacity()).and("date",
        // filter.getDate()).and("minutesLength", filter.getMinutesLength())
        // .and("site", filter.getSite()).parameters());
    }
}
