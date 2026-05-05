package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapistDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public boolean save(Therapist entity) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();

        try {
            session.persist(entity);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Therapist entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Therapist therapist = session.get(Therapist.class, entity.getId());
            therapist.setName(entity.getName());
            therapist.setEmail(entity.getEmail());
            therapist.setAvailability(entity.getAvailability());

            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Therapist therapist = session.get(Therapist.class, id);
            therapist.setStatus("Inactive");

            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public List<Therapist> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return session.createQuery("from Therapist", Therapist.class).list();

        } catch(Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }
}
