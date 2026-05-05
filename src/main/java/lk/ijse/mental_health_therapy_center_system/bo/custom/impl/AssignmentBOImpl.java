package lk.ijse.mental_health_therapy_center_system.bo.custom.impl;

import lk.ijse.mental_health_therapy_center_system.bo.custom.AssignmentBO;
import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapistDAO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;
import lk.ijse.mental_health_therapy_center_system.entity.Therapist;
import lk.ijse.mental_health_therapy_center_system.entity.TherapistProgram;
import lk.ijse.mental_health_therapy_center_system.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AssignmentBOImpl implements AssignmentBO {
    private final TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO, TherapyProgramDTO therapyProgramDTO) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Set therapist
            Therapist therapist = new Therapist();
            therapist.setName(therapistDTO.getName());
            therapist.setEmail(therapistDTO.getEmail());
            therapist.setAvailability(therapistDTO.getAvailability());
            therapist.setStatus("Active");

            // Get therapy program
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, therapyProgramDTO.getId());
            if (therapyProgram == null) {
                transaction.rollback();
                return false;
            }

            // Set therapist program
            TherapistProgram therapistProgram = new TherapistProgram();
            therapistProgram.setTherapist(therapist);
            therapistProgram.setTherapyProgram(therapyProgram);

            // Add TherapistProgram into Therapist
            therapist.getTherapistPrograms().add(therapistProgram);

            therapistDAO.save(therapist);
            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean updateTherapist(TherapistDTO therapistDTO) {
        Therapist therapist = new Therapist();
        therapist.setId(therapistDTO.getId());
        therapist.setName(therapistDTO.getName());
        therapist.setEmail(therapistDTO.getEmail());
        therapist.setAvailability(therapistDTO.getAvailability());

        return therapistDAO.update(therapist);
    }

    @Override
    public boolean deleteTherapist(int therapistId) {
        return therapistDAO.delete(therapistId);
    }

    @Override
    public List<TherapistDTO> getAllTherapist() {
        List<TherapistDTO> therapistDTOS = new ArrayList<>();
        for (Therapist therapist : therapistDAO.getAll()) {
            therapistDTOS.add(new TherapistDTO(
                    therapist.getId(),
                    therapist.getName(),
                    therapist.getEmail(),
                    therapist.getAvailability(),
                    therapist.getStatus()
            ));
        }

        return therapistDTOS;
    }
}
