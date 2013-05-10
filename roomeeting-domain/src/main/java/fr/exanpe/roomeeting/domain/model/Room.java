/**
 * 
 */
package fr.exanpe.roomeeting.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;

import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

@Entity
@NamedQueries(
{ @NamedQuery(name = Room.FIND_GAPS_FOR_DATE, query = "SELECT g FROM Gap g join fetch g.room WHERE g.room in (:rooms) AND g.date = :date and ((g.startTime between :startTime and :endTime) or (g.endTime between :startTime and :endTime)) order by g.room.id, g.date") })
public class Room implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243387796260345357L;

    public static final String FIND_GAPS_FOR_DATE = "Room.findGapsForDate";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int capacity;

    private Integer floor;

    private String phoneNumber;

    @ManyToOne
    private Site site;

    @OrderBy("id")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoomFeature> features = new ArrayList<RoomFeature>();

    @Lob
    private byte[] map;

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the capacity
     */
    public int getCapacity()
    {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    /**
     * @return the site
     */
    public Site getSite()
    {
        return site;
    }

    /**
     * @param site the site to set
     */
    public void setSite(Site site)
    {
        this.site = site;
    }

    /**
     * @return the floor
     */
    public Integer getFloor()
    {
        return floor;
    }

    /**
     * @param floor the floor to set
     */
    public void setFloor(Integer floor)
    {
        this.floor = floor;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the roomFeatures
     */
    public List<RoomFeature> getFeatures()
    {
        return features;
    }

    /**
     * @param roomFeatures the roomFeatures to set
     */
    public void setFeatures(List<RoomFeature> roomFeatures)
    {
        this.features = roomFeatures;
    }

    /**
     * @return the icon
     */
    public byte[] getMap()
    {
        return map;
    }

    /**
     * @param icon the icon to set
     */
    public void setMap(byte[] icon)
    {
        this.map = icon;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Room)) { return false; }

        return id != null && id.equals(((Room) obj).getId());
    }

    @Override
    public int hashCode()
    {
        return id.intValue();
    }
}
