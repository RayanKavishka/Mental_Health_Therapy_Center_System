package lk.ijse.mental_health_therapy_center_system.bo.custom;

import lk.ijse.mental_health_therapy_center_system.bo.SuperBO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;
import lk.ijse.mental_health_therapy_center_system.entity.Patient;

import java.math.BigDecimal;
import java.util.List;

public interface RegisterBO extends SuperBO {
    boolean savePatient(PatientDTO patientDTO, TherapyProgramDTO therapyProgram, BigDecimal payAmount);
    boolean updatePatient(PatientDTO patientDTO);
    boolean deletePatient(int patientId);
    List<PatientDTO> getAllPatients();
    List<TherapyProgramDTO> getAllTherapyProgramsByPatient(int id);
    PatientDTO getPatientById(int patientId);
}
