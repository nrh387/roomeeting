package fr.exanpe.roomeeting.domain.business.filters;

import fr.exanpe.roomeeting.domain.model.Role;

public class UserFilter
{
    private String name;

    private String firstname;

    private String username;

    private int maxResults;

    private Role role;

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
     * @return the username
     */
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

    /**
     * @return the maxResults
     */
    public int getMaxResults()
    {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults)
    {
        this.maxResults = maxResults;
    }

    /**
     * @return the role
     */
    public Role getRole()
    {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role)
    {
        this.role = role;
    }

}
