
package ch.hearc.ig.tools;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {

            //chargement depuis le fichiefr de config qui est dans les ressources
            Configuration config = new Configuration().configure();
            // read from .env
            String uri = Env.get("DB_DRIVER") + ":@"
                    + Env.get("DB_HOST") + ":" + Env.get("DB_PORT") + ":" + Env.get("DB_NAME");
            config.setProperty("hibernate.connection.url", uri);
            config.setProperty("hibernate.connection.username", Env.get("DB_USERNAME"));
            config.setProperty("hibernate.connection.password", Env.get("DB_PASSWORD"));
            return config.buildSessionFactory();
        } catch (Throwable ex) {
            //pourquoi ce catch? Je sais pas du tout ....
            System.err.println("Initial HibernateUtil creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        // Ferme caches et pools de connexions une fois qu'on a tt fini
        getSessionFactory().close();
    }
}
