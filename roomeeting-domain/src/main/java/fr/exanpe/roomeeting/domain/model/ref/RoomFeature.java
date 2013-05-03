/**
 * 
 */
package fr.exanpe.roomeeting.domain.model.ref;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable(true)
@Cache(region = "roomeetingCacheRef", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoomFeature implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243387794560345357L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Lob
    private byte[] icon;

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
     * @return the icon
     */
    public byte[] getIcon()
    {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(byte[] icon)
    {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof RoomFeature)) { return false; }

        return id.equals(((RoomFeature) obj).getId());
    }

}
