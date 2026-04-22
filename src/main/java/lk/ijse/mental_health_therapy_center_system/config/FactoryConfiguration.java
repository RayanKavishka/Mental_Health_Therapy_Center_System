package lk.ijse.mental_health_therapy_center_system.config;

import lk.ijse.mental_health_therapy_center_system.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();

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

    public FactoryConfiguration getInstance() {
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration() :
                factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
