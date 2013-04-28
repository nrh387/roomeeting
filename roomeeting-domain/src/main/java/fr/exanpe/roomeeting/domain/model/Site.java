/**
 * 
 */
package fr.exanpe.roomeeting.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries(
{ @NamedQuery(name = Site.FIND_ALL, query = "FROM Site s ORDER BY name"),
        @NamedQuery(name = Site.FIND_WITH_ROOMS, query = "FROM Site s left join fetch s.rooms where id=:id ") })
public class Site implements Serializable
{
    public static final String FIND_ALL = "Site.findAll";

    public static final String FIND_WITH_ROOMS = "Site.findWithRooms";

    /**
     * 
     */
    private static final long serialVersionUID = -243387796270345357L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<Room>();

    private int roomCount;

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
     * @return the rooms
     */
    public List<Room> getRooms()
    {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<Room> rooms)
    {
        this.rooms = rooms;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return the RoomNumber
     */
    public int getRoomCount()
    {
        return roomCount;
    }

    /**
     * @param RoomNumber the RoomNumber to set
     */
    public void setRoomCount(int roomNumber)
    {
        this.roomCount = roomNumber;
    }

}
