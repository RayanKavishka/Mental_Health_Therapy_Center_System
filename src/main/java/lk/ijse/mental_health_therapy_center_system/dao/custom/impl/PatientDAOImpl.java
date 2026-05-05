package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Patient;
import lk.ijse.mental_health_therapy_center_system.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    @Override
    public boolean save(Patient entity) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();
        try {
            session.persist(entity);
            return true;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Patient entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction =  session.beginTransaction();

        try {
            Patient patient = session.get(Patient.class, entity.getId());
            patient.setName(entity.getName());
            patient.setEmail(entity.getEmail());
            patient.setPhone(entity.getPhone());
            patient.setMedicalHistory(entity.getMedicalHistory());

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
            Patient patient = session.get(Patient.class, id);
            patient.setStatus("Inactive");
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
    public List<Patient> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return session.createQuery("from Patient", Patient.class).list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }
}
