/**
 * 
 */
package fr.exanpe.roomeeting.domain.model.ref;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable(true)
@Cache(region = "roomeetingCacheRef", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parameter implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -243387794560345357L;

    @Id
    private Long id;

    private String name;

    private String description;

    private String stringValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateValue;

    @Temporal(TemporalType.TIME)
    private Date timeValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampValue;

    private Boolean booleanValue;

    private Integer integerValue;

    public Parameter()
    {

    }

    public Parameter(Long id)
    {
        this(id, null, null);
    }

    public Parameter(Long id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

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
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the valuestring
     */
    public String getStringValue()
    {
        return stringValue;
    }

    /**
     * @param valuestring the valuestring to set
     */
    public void setStringValue(String valuestring)
    {
        this.stringValue = valuestring;
    }

    /**
     * @return the valuedate
     */
    public Date getDateValue()
    {
        return dateValue;
    }

    /**
     * @param valuedate the valuedate to set
     */
    public void setDateValue(Date valuedate)
    {
        this.dateValue = valuedate;
    }

    /**
     * @return the valueinteger
     */
    public Integer getIntegerValue()
    {
        return integerValue;
    }

    /**
     * @param valueinteger the valueinteger to set
     */
    public void setIntegerValue(Integer valueinteger)
    {
        this.integerValue = valueinteger;
    }

    /**
     * @return the valuetime
     */
    public Date getTimeValue()
    {
        return timeValue;
    }

    /**
     * @param valuetime the valuetime to set
     */
    public void setTimeValue(Date valuetime)
    {
        this.timeValue = valuetime;
    }

    /**
     * @return the valuetimestamp
     */
    public Date getTimestampValue()
    {
        return timestampValue;
    }

    /**
     * @param valuetimestamp the valuetimestamp to set
     */
    public void setTimestampValue(Date valuetimestamp)
    {
        this.timestampValue = valuetimestamp;
    }

    /**
     * @return the valueboolean
     */
    public Boolean getBooleanValue()
    {
        return booleanValue;
    }

    /**
     * @param valueboolean the valueboolean to set
     */
    public void setBooleanValue(Boolean valueboolean)
    {
        this.booleanValue = valueboolean;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Parameter)) { return false; }

        return id.equals(((Parameter) obj).getId());
    }

}
