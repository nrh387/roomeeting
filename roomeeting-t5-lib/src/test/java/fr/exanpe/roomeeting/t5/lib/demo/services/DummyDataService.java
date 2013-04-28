/**
 * 
 */
package fr.exanpe.roomeeting.t5.lib.demo.services;

import java.util.ArrayList;
import java.util.List;

import fr.exanpe.roomeeting.t5.lib.demo.bean.User;

/**
 * Service utilisé pour générer des données de type {@link User}
 * 
 * @author lguerin
 */
public class DummyDataService
{
    private User createUser(int id, String name, String firstName, int age)
    {
        User u = new User();
        u.setId(id);
        u.setLastName(name);
        u.setFirstName(firstName);
        u.setAge(age);
        return u;
    }

    public List<User> getListUsers()
    {
        List<User> users = new ArrayList<User>();

        for (int i = 0; i < 10; i++)
        {
            users.add(createUser(i, "Name " + i, "First Name " + i, 20 + i));
        }
        return users;
    }
}
