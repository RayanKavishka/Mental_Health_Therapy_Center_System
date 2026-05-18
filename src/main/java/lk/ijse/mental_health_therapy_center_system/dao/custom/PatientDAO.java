package lk.ijse.mental_health_therapy_center_system.dao.custom;

import lk.ijse.mental_health_therapy_center_system.dao.CrudDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Patient;

public interface PatientDAO extends CrudDAO<Patient> {
    Patient getPatient(int patientId);
    Patient getPatientByPhone(String phoneNo);
}
