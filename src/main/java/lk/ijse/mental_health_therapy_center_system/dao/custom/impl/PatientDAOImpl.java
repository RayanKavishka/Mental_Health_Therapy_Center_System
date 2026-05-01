package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PatientDAOImpl implements PatientDAO {
    @Override
    public boolean save(Patient entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(entity);
            transaction.commit();
            return true;

        } catch(Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;

        } finally {
            session.close();
        }
    }
}
