package lk.ijse.mental_health_therapy_center_system.bo.custom;

import lk.ijse.mental_health_therapy_center_system.bo.SuperBO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;

import java.util.List;

public interface AssignmentBO extends SuperBO {
    List<TherapistDTO> getAllTherapist();
    boolean saveTherapist(TherapistDTO therapistDTO, TherapyProgramDTO therapyProgramDTO);
    boolean updateTherapist(TherapistDTO therapistDTO);
    boolean deleteTherapist(int therapistId);
}
