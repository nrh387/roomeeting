/**
 * 
 */
package fr.exanpe.roomeeting.t5.lib.services;

import java.util.List;

/**
 * Service de notification des flash messages {@link FlashMessages}.
 * 
 * @author lguerin
 */
public interface FlashMessages
{
    /**
     * Retourne un message d'information à l'utilisateur.
     * 
     * @param message Message retourné
     */
    void inform(String message);

    /**
     * Détermine s'il existe des flash messages à afficher à l'utilisateur
     * 
     * @return
     */
    boolean getHasMessages();

    /**
     * Récupère la liste des messages de type information
     * 
     * @return Messages d'information
     */
    List<String> getInformations();

    /**
     * Nettoie les listes de flash messages
     */
    void clear();
}
