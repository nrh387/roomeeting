package fr.exanpe.roomeeting.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Rôles attribués aux utilisateurs de l'application.
 */
@NamedQueries(
{ @NamedQuery(name = Role.FIND_BY_ROLE_NAME, query = "FROM Role WHERE name = :name"), @NamedQuery(name = Role.FIND_ALL, query = "FROM Role ORDER BY priority") })
@Entity
@NamedNativeQuery(name = Role.DELETE_FOR_USER, query = "DELETE FROM Uzer_Role WHERE users_id = :userId", resultClass = Role.class)
public class Role implements Serializable
{
    /**
     * serialUID
     */
    private static final long serialVersionUID = 2186257499592013844L;

    /**
     * Identifiants des requetes NamedQuery
     */
    public static final String FIND_BY_ROLE_NAME = "Role.findByRoleName";

    public static final String DELETE_FOR_USER = "Role.deleteForUser";
    /**
     * Identifiants des requetes NamedQuery
     */
    public static final String FIND_ALL = "Role.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer priority;

    @Column(nullable = false)
    private String name;

    /**
     * Fetch.EAGER because User needs all the authorities
     * all the time
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    public Role()
    {
        super();
    }

    public long getId()
    {
        return id;
    }

    public List<Authority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities)
    {
        this.authorities = authorities;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the order
     */
    public Integer getPriority()
    {
        return priority;
    }

    /**
     * @param order the order to set
     */
    public void setPriority(Integer order)
    {
        this.priority = order;
    }

}
