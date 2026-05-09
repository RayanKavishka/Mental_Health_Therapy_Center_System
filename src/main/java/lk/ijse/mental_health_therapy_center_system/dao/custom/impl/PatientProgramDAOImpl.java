package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PatientProgramDAO;
import lk.ijse.mental_health_therapy_center_system.entity.PatientProgram;
import lk.ijse.mental_health_therapy_center_system.entity.TherapyProgram;
import org.hibernate.Session;

import java.util.List;

public class PatientProgramDAOImpl implements PatientProgramDAO {
    @Override
    public boolean save(PatientProgram entity) {
        return false;
    }

    @Override
    public boolean update(PatientProgram entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public PatientProgram get(int id) {
        return null;
    }

    @Override
    public List<PatientProgram> getAll() {
        return List.of();
    }

    @Override
    public List<TherapyProgram> getAllTherapyProgram(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            String hql = "SELECT pp.therapyProgram FROM PatientProgram pp WHERE pp.patient.id = :patientId";
            return session.createQuery(hql, TherapyProgram.class)
                    .setParameter("patientId", id)
                    .list();

        } catch (RuntimeException e) {
            throw new RuntimeException(e);

        } finally {
            session.close();
        }
    }
}
