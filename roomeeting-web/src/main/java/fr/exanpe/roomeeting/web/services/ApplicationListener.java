/**
 * 
 */
package fr.exanpe.roomeeting.web.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import fr.exanpe.roomeeting.common.exception.BusinessException;
import fr.exanpe.roomeeting.domain.business.BookingManager;
import fr.exanpe.roomeeting.domain.business.RoomFeatureManager;
import fr.exanpe.roomeeting.domain.business.SiteManager;
import fr.exanpe.roomeeting.domain.business.UserManager;
import fr.exanpe.roomeeting.domain.business.dto.TimeSlot;
import fr.exanpe.roomeeting.domain.database.DatabaseVersionManager;
import fr.exanpe.roomeeting.domain.model.Gap;
import fr.exanpe.roomeeting.domain.model.Role;
import fr.exanpe.roomeeting.domain.model.Room;
import fr.exanpe.roomeeting.domain.model.Site;
import fr.exanpe.roomeeting.domain.model.User;
import fr.exanpe.roomeeting.domain.model.ref.RoomFeature;

/**
 * Classe chargée automatiquement au démarrage de l'application.
 * 
 * @author lguerin
 */
@EagerLoad
public class ApplicationListener
{
    /**
     * Logger de la classe
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    /**
     * Constructeur.
     * 
     * @param context Contexte Spring
     * @param productionMode Mode Tapestry (PRODUCTION ou DEVELOPPEMENT)
     * @throws SQLException
     */
    public ApplicationListener(ApplicationContext context, @Inject
    @Symbol(SymbolConstants.PRODUCTION_MODE)
    boolean productionMode) throws SQLException
    {
        if (productionMode)
        {
            LOGGER.info("Demarrage de l'application en mode PRODUCTION.");
            return;
        }

        context.getBean(UserManager.class).listRoles();

        LOGGER.info("Mise à niveau de la base de données...");

        // TODO in production mode too
        DatabaseVersionManager db = context.getBean(DatabaseVersionManager.class);
        db.boot();

        LOGGER.info("Mise à niveau terminée");

        /**
         * En mode DEVELOPPEMENT, permet d'initialiser un jeu de données par défaut.
         */
        LOGGER.info("Demarrage de l'application en mode DEVELOPPEMENT.");

        // Chargement du jeu de données de test
        try
        {
            loadUser(context);
            loadSites(context);
            loadBookings(context);
        }
        catch (IOException e)
        {
            LOGGER.error(">> Erreur fatale : impossible de charger le jeu de données de test: " + e);
            System.exit(1);
        }
        catch (BusinessException e)
        {
            LOGGER.error(">> Erreur fatale : impossible de charger le jeu de données de test: " + e);
            System.exit(1);
        }
    }

    private void loadBookings(ApplicationContext context) throws BusinessException
    {
        BookingManager bookingManager = context.getBean(BookingManager.class);
        UserManager userManager = context.getBean(UserManager.class);
        SiteManager siteManager = context.getBean(SiteManager.class);

        User user = userManager.findByUsername("admin");
        Room room = siteManager.findRoom(1L);

        Gap g = new Gap();
        g.setDate(DateUtils.add(new Date(), Calendar.DAY_OF_YEAR, -7));
        g.setRoom(room);

        bookingManager.processBooking(user, g, new TimeSlot(10, 0), new TimeSlot(14, 0));

        Gap g2 = new Gap();
        g2.setDate(DateUtils.add(new Date(), Calendar.DAY_OF_YEAR, 7));
        g2.setRoom(room);

        bookingManager.processBooking(user, g2, new TimeSlot(17, 0), new TimeSlot(18, 0));
    }

    private void loadSites(ApplicationContext context) throws SQLException, IOException
    {
        LOGGER.info(">>> Sites : Chargement du jeu de donnees par defaut...");

        SiteManager sm = context.getBean(SiteManager.class);

        Site site = new Site();
        site.setName("Mattei");
        site.setAddress("rue de Bercy, Paris");
        site.setLatitude("48");
        site.setLongitude("2");

        sm.create(site);

        Site site2 = new Site();
        site2.setName("Manhattan");
        site2.setAddress("Esplanade de la Defense");

        sm.create(site2);

        RoomFeature rf = context.getBean(RoomFeatureManager.class).find(1L);
        RoomFeature rf2 = context.getBean(RoomFeatureManager.class).find(2L);

        Room room = new Room();
        room.setName("Oeillet");
        room.setFloor(8);
        room.setMap(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/maps/floor/manhattan-8.jpg")));

        room.getFeatures().add(rf);
        room.getFeatures().add(rf2);

        sm.addRoom(site, room);

        Room room2 = new Room();
        room2.setName("Rose");
        room2.setFloor(8);
        room2.setMap(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream("/maps/floor/manhattan-8.jpg")));

        sm.addRoom(site, room2);

        Room room3 = new Room();
        room3.setName("Geranium");
        room3.setFloor(4);
        room3.setPhoneNumber("0101010101");

        sm.addRoom(site, room3);

        LOGGER.info("<<< Sites : Chargement termine.");
    }

    private void loadUser(ApplicationContext context) throws SQLException, BusinessException
    {
        LOGGER.info(">>> Users : Chargement du jeu de donnees par defaut...");

        UserManager userManager = context.getBean(UserManager.class);

        List<Role> roles = context.getBean(UserManager.class).listRoles();

        User superadmin = new User();
        superadmin.setUsername("admin");
        superadmin.setPassword("admin");
        superadmin.setEmail("admin@admin.com");
        superadmin.setName("Name");
        superadmin.setFirstname("Firstname");
        userManager.createUser(superadmin, Collections.singletonList(roles.get(0)));

        User useradmin = new User();
        useradmin.setUsername("sitemanager");
        useradmin.setPassword("sitemanager");
        useradmin.setEmail("sitemanager@sitemanager.com");
        useradmin.setName("Name");
        useradmin.setFirstname("Firstname");
        userManager.createUser(useradmin, Collections.singletonList(roles.get(1)));

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setEmail("jul@gmail.com");
        user.setName("Name");
        user.setFirstname("Firstname");
        userManager.createUser(user, Collections.singletonList(roles.get(2)));

        LOGGER.info("<<< Users : Chargement termine.");

        // TODO delete, cache test purpose
        for (int i = 0; i < 10; i++)
        {
            userManager.findByUsername("admin");
        }
    }
}
