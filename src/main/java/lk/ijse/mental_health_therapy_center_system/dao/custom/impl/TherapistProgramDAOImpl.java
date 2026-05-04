package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapistProgramDAO;
import lk.ijse.mental_health_therapy_center_system.entity.TherapistProgram;

import java.util.List;

public class TherapistProgramDAOImpl implements TherapistProgramDAO {
    @Override
    public boolean save(TherapistProgram entity) {
        return false;
    }

    @Override
    public boolean update(TherapistProgram entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<TherapistProgram> getAll() {
        return List.of();
    }
}
