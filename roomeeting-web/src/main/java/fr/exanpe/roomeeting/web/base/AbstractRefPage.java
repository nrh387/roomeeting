package fr.exanpe.roomeeting.web.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import fr.exanpe.roomeeting.domain.core.business.DefaultManager;

public abstract class AbstractRefPage<T>
{
    @Persist
    private List<T> list;

    private T current;

    @Persist
    private T editing;

    @InjectComponent("editZone")
    private Zone editZone;

    @InjectComponent(value = "grid")
    private Grid grid;

    @Inject
    private Logger logger;

    private Class<T> clazz;

    void onActivate()
    {
        list = getManager().list();

        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (clazz == null)
        {
            logger.error("Could not get parametered class by reflection");
            throw new RuntimeException("Could not get parametered class by reflection");
        }
    }

    public abstract DefaultManager<T, Long> getManager();

    public abstract boolean save(T t);

    @OnEvent(value = EventConstants.ACTION, component = "setupEdit")
    Object setupEdit(Long id)
    {
        editing = getManager().find(id);

        return editZone.getBody();
    }

    @OnEvent(value = EventConstants.ACTION, component = "setupAdd")
    Object setupAdd()
    {
        try
        {
            editing = clazz.newInstance();
        }
        catch (Exception e)
        {
            logger.error("Instanciation failed for " + clazz.getName(), e);
        }
        return editZone.getBody();
    }

    @OnEvent(value = EventConstants.ACTION, component = "cancel")
    void cancel()
    {
        editing = null;
    }

    @OnEvent(value = "validateSave")
    void validateSave()
    {
        if (save(editing))
        {
            list = getManager().list();
            editing = null;
        }

    }

    @OnEvent(value = EventConstants.ACTION, component = "delete")
    public void delete(Long id)
    {
        // security check
        getManager().delete(id);

    }

    public boolean hasElements()
    {
        return CollectionUtils.isNotEmpty(list);
    }

    /**
     * @return the list
     */
    public List<T> getList()
    {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<T> list)
    {
        this.list = list;
    }

    /**
     * @return the current
     */
    public T getCurrent()
    {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(T current)
    {
        this.current = current;
    }

    /**
     * @return the editing
     */
    public T getEditing()
    {
        return editing;
    }

    /**
     * @param editing the editing to set
     */
    public void setEditing(T editing)
    {
        this.editing = editing;
    }

    /**
     * @return the editZone
     */
    public Zone getEditZone()
    {
        return editZone;
    }

    /**
     * @param editZone the editZone to set
     */
    public void setEditZone(Zone editZone)
    {
        this.editZone = editZone;
    }

    /**
     * @return the grid
     */
    public Grid getGrid()
    {
        return grid;
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(Grid grid)
    {
        this.grid = grid;
    }

}
