package fr.exanpe.roomeeting.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

/**
 * Gestion des autorisations (Spring Security).
 * 
 * @see GrantedAuthority;
 * @author lguerin
 */
@Entity
public class Authority implements GrantedAuthority
{
    /**
     * serialUID
     */
    private static final long serialVersionUID = -3859072639562257700L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String authority;

    /** Reverse owner */
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<Role> roles;

    public Authority()
    {
        super();
    }

    public Authority(String authority)
    {
        super();
        this.authority = authority;
    }

    public long getId()
    {
        return id;
    }

    public String getAuthority()
    {
        return authority;
    }

    public void setAuthority(String authority)
    {
        this.authority = authority;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

}
