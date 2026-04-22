package lk.ijse.mental_health_therapy_center_system.config;

import lk.ijse.mental_health_therapy_center_system.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        try (InputStream inputStream =
                     FactoryConfiguration.class.getClassLoader()
                             .getResourceAsStream("hibernate.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("hibernate.properties not found in resources");
            }
            properties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load hibernate.properties", e);
        }

        java.net.URL ehcacheUrl = FactoryConfiguration.class.getClassLoader()
                .getResource("ehcache.xml");
        if (ehcacheUrl == null) {
            throw new RuntimeException("ehcache.xml not found in classpath");
        }
        properties.setProperty("hibernate.javax.cache.uri", ehcacheUrl.toString());

        configuration.setProperties(properties);

        configuration
                .addAnnotatedClass(Therapist.class)
                .addAnnotatedClass(TherapyProgram.class)
                .addAnnotatedClass(TherapistProgram.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(PatientProgram.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(TherapySession.class)
                .addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static synchronized FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
