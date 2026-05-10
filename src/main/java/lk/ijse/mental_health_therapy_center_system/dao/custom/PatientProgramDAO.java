package lk.ijse.mental_health_therapy_center_system.dao.custom;

import lk.ijse.mental_health_therapy_center_system.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center_system.entity.PatientProgram;
import lk.ijse.mental_health_therapy_center_system.entity.TherapyProgram;

import java.util.List;

public interface PatientProgramDAO extends CrudDAO<PatientProgram> {
    List<TherapyProgram> getAllTherapyProgram(int id);
    Long getProgramEnrolledCount(int patientId);
}
