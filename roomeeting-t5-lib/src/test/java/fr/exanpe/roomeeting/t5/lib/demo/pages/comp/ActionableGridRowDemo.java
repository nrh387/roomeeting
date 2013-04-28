/**
 * 
 */
package fr.exanpe.roomeeting.t5.lib.demo.pages.comp;

import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import fr.exanpe.roomeeting.t5.lib.demo.bean.User;
import fr.exanpe.roomeeting.t5.lib.demo.services.DummyDataService;
import fr.exanpe.roomeeting.t5.lib.mixins.ActionableGridRow;
import fr.exanpe.roomeeting.t5.lib.util.RooMeetingLibEvents;

/**
 * Classe de test du mixin {@link ActionableGridRow}
 * 
 * @author lguerin
 */
public class ActionableGridRowDemo
{
    @Inject
    private DummyDataService dataService;

    @Property
    private User current;

    public List<User> getUsers()
    {
        return dataService.getListUsers();
    }

    @Inject
    private Request request;

    @OnEvent(value = RooMeetingLibEvents.ACTIONABLE_GRID_ROW_ACTION)
    void onGridClick()
    {
        String userId = request.getParameter(ActionableGridRow.ACTION_PARAM);
        System.out.println(String.format("Click sur la grid - userId: %s", userId));
    }
}
