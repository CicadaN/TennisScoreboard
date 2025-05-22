package io.util;

import io.entity.Match;
import io.entity.Player;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() { }

    private static SessionFactory buildSessionFactory() {
        String propFile = System.getProperty("hibernate.props", "hibernate.properties");

        Properties props = new Properties();
        try (InputStream in = HibernateUtil.class.getClassLoader().getResourceAsStream(propFile)) {
            if (in == null) {
                throw new IllegalStateException("File " + propFile + " not found");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + propFile, e);
        }

        return new Configuration()
                .addProperties(props)
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Match.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
