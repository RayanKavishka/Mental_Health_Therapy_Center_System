package lk.ijse.mental_health_therapy_center_system.bo.custom;

import lk.ijse.mental_health_therapy_center_system.bo.SuperBO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;

public interface RegisterBO extends SuperBO {
    boolean savePatient(PatientDTO patientDTO);
}
