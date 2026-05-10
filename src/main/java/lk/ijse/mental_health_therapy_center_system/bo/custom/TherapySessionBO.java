package lk.ijse.mental_health_therapy_center_system.bo.custom;

import lk.ijse.mental_health_therapy_center_system.bo.SuperBO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapySessionDTO;

import java.util.Date;
import java.util.List;

public interface TherapySessionBO extends SuperBO {
    List<TherapySessionDTO> getAllTherapySession();
    List<TherapistDTO> getAllTherapistBySchedule(String timePeriod, Date date);
    boolean bookTherapySession(TherapySessionDTO therapySessionDTO);
    boolean updateTherapySession(TherapySessionDTO therapySessionDTO);
    boolean cancelTherapySession(int therapySessionId);
    boolean checkSessionTimePeriod();
    int getAllTodaySessionCount();
}
