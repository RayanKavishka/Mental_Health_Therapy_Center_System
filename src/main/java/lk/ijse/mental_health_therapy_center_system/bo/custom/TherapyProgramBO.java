package lk.ijse.mental_health_therapy_center_system.bo.custom;

import lk.ijse.mental_health_therapy_center_system.bo.SuperBO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;

import java.util.List;

public interface TherapyProgramBO extends SuperBO {
    boolean saveTherapyProgram(TherapyProgramDTO therapyProgramDTO);
    boolean updateTherapyProgram(TherapyProgramDTO therapyProgramDTO);
    boolean deleteTherapyProgram(int therapyId);
    List<TherapyProgramDTO> getAllTherapyPrograms();
}
