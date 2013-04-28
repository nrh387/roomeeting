/**
 * 
 */
package fr.exanpe.roomeeting.domain.security;

import fr.exanpe.roomeeting.domain.model.User;

/**
 * Contexte de sécurité de l'application RooMeeting.
 * 
 * @author lguerin
 */
public interface RooMeetingSecurityContext
{
    /**
     * Authentifie un utilisateur.
     * 
     * @param user Utilisateur à authentifier.
     */
    void login(User user);

    /**
     * Teste si un utilisateur est authentifié ou non.
     * 
     * @return true si authentifié
     */
    boolean isLoggedIn();

    /**
     * Retourne l'utilisateur {@link User} authentifié.
     * 
     * @return utilisateur courant authentifié.
     */
    User getUser();

    /**
     * Deconnecte l'utilisateur courant
     */
    void logout();
}
