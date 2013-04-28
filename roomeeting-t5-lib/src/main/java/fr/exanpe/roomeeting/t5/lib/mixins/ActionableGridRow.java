package fr.exanpe.roomeeting.t5.lib.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import fr.exanpe.roomeeting.t5.lib.util.RooMeetingLibEvents;

/**
 * Le mixin <code>ActionableGridRow</code> permet de rendre mettre à jour une zone par un clic sur
 * un ligne d'une
 * grille.
 */
@Import(library =
{ "ActionableGridRow.js" })
public class ActionableGridRow
{
    /** l'identifiant client de la zone à mettre à jour */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String zoneId;

    /** le nom de la cellule dont la donnée est récupérée et mise en paramètre de la requête ajax */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
    private String cell;

    /** indique la possibilité de sélectionner plusieurs lignes */
    @Parameter
    private boolean multiSel;

    /**
     * permet de nommer la fonction qui récupère l'event dans le cas où on a plusieurs
     * ActionableGridRow
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String gridName;

    /** le servive d'injection JavaScript */
    @Environmental
    private JavaScriptSupport jsSupport;

    /** le service de ressources Tapestry */
    @Inject
    private ComponentResources resources;

    /**
     * Parametre transmis dans l'url
     */
    public static final String ACTION_PARAM = "gridActionParam";

    /**
     * Met en place le script JavaScript.
     */
    void afterRender()
    {
        String cellName = cell;
        if (cellName != null && !cellName.contains("."))
        {
            cellName = "td." + cellName;
        }

        Link link = null;
        if (gridName == null)
        {
            link = resources.createEventLink(RooMeetingLibEvents.ACTIONABLE_GRID_ROW_ACTION);
        }
        else
        {
            link = resources.createEventLink(RooMeetingLibEvents.ACTIONABLE_GRID_ROW_ACTION + gridName.toLowerCase());
        }
        String url = link.toAbsoluteURI().toString();
        jsSupport.addScript(
                "new ActionableGridRow('%s', '%s', '%s', '%s', %s, '%s');",
                resources.getId(),
                zoneId,
                cellName,
                url,
                Boolean.valueOf(multiSel),
                ACTION_PARAM);

    }
}
