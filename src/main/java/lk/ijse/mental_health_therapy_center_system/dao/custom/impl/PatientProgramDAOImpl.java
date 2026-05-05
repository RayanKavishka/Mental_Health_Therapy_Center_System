package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PatientProgramDAO;
import lk.ijse.mental_health_therapy_center_system.entity.PatientProgram;
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
    public List<PatientProgram> getAll() {
        return List.of();
    }
}
