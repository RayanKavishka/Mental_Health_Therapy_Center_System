package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Patient;
import lk.ijse.mental_health_therapy_center_system.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {
    @Override
    public boolean save(TherapyProgram entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(entity);
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
    public boolean update(TherapyProgram entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, entity.getId());
            therapyProgram.setName(entity.getName());
            therapyProgram.setDuration(entity.getDuration());
            therapyProgram.setFee(entity.getFee());
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
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
            therapyProgram.setStatus("Inactive");
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

    @Override
    public TherapyProgram get(int id) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();

        try {
            return session.get(TherapyProgram.class, id);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TherapyProgram> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return session.createQuery("from TherapyProgram", TherapyProgram.class)
                    .setCacheable(true)
                    .setCacheRegion("therapyProgramCache")
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public TherapyProgram getProgram(int therapyProgramId) {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return session.get(TherapyProgram.class, therapyProgramId);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);

        } finally {
            session.close();
        }
    }
}
