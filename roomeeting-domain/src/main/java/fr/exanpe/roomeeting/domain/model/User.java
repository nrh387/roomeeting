/**
 * 
 */
package fr.exanpe.roomeeting.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * Objet représentant l'utilisateur connecté.
 * Implémente l'interface {@link UserDetails} de Spring Security.
 * 
 * @author lguerin
 */
// TODO delete Cache ! just for tests purpose
@Entity
@Cache(region = "archetypeTestCacheMetier", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Cacheable(value = true)
// TODO delete Cache ! just for tests purpose
@NamedQueries(
{ @NamedQuery(name = User.FIND_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true")) })
public class User implements UserDetails
{
    /**
     * serialUID
     */
    private static final long serialVersionUID = -6157226898787740763L;

    /**
     * Identifiants des requetes NamedQuery
     */
    public static final String FIND_BY_USERNAME = "User.findByUsername";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String name;

    @Column
    private String firstname;

    @Temporal(TemporalType.DATE)
    private Date lastConnection;

    /**
     * For SUBSELECT explanation,
     * 
     * @see http://opensource.atlassian.com/projects/hibernate/browse/EJB-346
     * @see http://opensource.atlassian.com/projects/hibernate/browse/HHH-1718
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Column(nullable = false)
    // TODO delete Cache ! just for tests purpose
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "J_USER_ROLE", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;

    @ManyToOne
    private Site defaultSite;

    /**
     * Ajoute un role a un utilisateur.
     * 
     * @param role le role a ajouter
     */
    public void addRole(Role role)
    {
        Assert.notNull(role, "role");
        if (roles == null)
        {
            roles = new ArrayList<Role>();
        }
        if (!roles.contains(role))
        {
            roles.add(role);
        }
    }

    /**
     * Ajoute une liste de roles a un utilisateur.
     * 
     * @param roles
     */
    public void setRoles(List<Role> roles)
    {
        Assert.notNull(roles);
        this.roles = roles;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    /**
     * Permet à Spring Security d'accéder aux autorisations de
     * l'utilisateur courant. {@link UserDetails}
     */
    public Collection<GrantedAuthority> getAuthorities()
    {
        Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();

        for (Role r : roles)
        {
            for (Authority authority : r.getAuthorities())
            {
                GrantedAuthority ga = authority;
                auths.add(ga);
            }
        }
        return auths;
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
     * @return the username
     */
    @Override
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    /**
     * @return the defaultSite
     */
    public Site getDefaultSite()
    {
        return defaultSite;
    }

    /**
     * @param defaultSite the defaultSite to set
     */
    public void setDefaultSite(Site defaultSite)
    {
        this.defaultSite = defaultSite;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
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
     * @return the firstname
     */
    public String getFirstname()
    {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    /**
     * @return the lastConnection
     */
    public Date getLastConnection()
    {
        return lastConnection;
    }

    /**
     * @param lastConnection the lastConnection to set
     */
    public void setLastConnection(Date lastConnection)
    {
        this.lastConnection = lastConnection;
    }

}
