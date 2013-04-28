/**
 * 
 */
package fr.exanpe.roomeeting.domain.business;

import java.util.List;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.filters.UserFilter;
import fr.exanpe.roomeeting.domain.core.business.DefaultManager;
import fr.exanpe.roomeeting.domain.model.Role;
import fr.exanpe.roomeeting.domain.model.User;

/**
 * Le service <code>UserManager</code> permet de gérer les utilisateurs {@link User} qui se
 * connectent à
 * l'application.
 * 
 * @author lguerin
 */
public interface UserManager extends DefaultManager<User, Long>
{
    /**
     * Recherche un objet {@link User} à partir de son <code>username</code>.
     * 
     * @param username l'identifiant (login) de l'utilisateur
     * @return l'objet <code>User</code>, ou <code>null</code>
     */
    User findByUsername(String username);

    /**
     * Créé un nouvel utilisateur
     * 
     * @param user l'utilisateur à créer
     * @param roles les rôles de l'utilisateur
     * @throws BusinessException en cas de probleme lors de la creation de l'utilisateur
     */
    void createUser(User user, List<Role> roles) throws BusinessException;

    /**
     * Retourne true si l'utilisateur <code>username</code> n'existe pas déjà.
     * 
     * @param username Le nom de l'utilisateur à créer
     * @return true if available
     */
    boolean isAvailableUsername(String username);

    /**
     * Supprime les roles de l'utilisateur
     * 
     * @param user l'utilisateur à nettoyer
     */
    void cleanRoles(User user);

    List<User> searchUsers(UserFilter username);

    void createRole(Role r);

    List<Role> listRoles();

    void connected(User user);
}
