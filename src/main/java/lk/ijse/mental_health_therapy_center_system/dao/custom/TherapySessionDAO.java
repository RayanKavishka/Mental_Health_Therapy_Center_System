package lk.ijse.mental_health_therapy_center_system.dao.custom;

import lk.ijse.mental_health_therapy_center_system.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Therapist;
import lk.ijse.mental_health_therapy_center_system.entity.TherapySession;

import java.util.Date;
import java.util.List;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {
    List<Therapist> getAllBySchedule(String timePeriod, Date date);
}
