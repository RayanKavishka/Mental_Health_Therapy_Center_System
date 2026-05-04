package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapistDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Therapist;

import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public boolean save(Therapist entity) {
        return false;
    }

    @Override
    public boolean update(Therapist entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Therapist> getAll() {
        return List.of();
    }
}
