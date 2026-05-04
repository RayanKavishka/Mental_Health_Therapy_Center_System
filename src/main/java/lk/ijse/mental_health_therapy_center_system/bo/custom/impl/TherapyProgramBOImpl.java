package lk.ijse.mental_health_therapy_center_system.bo.custom.impl;

import lk.ijse.mental_health_therapy_center_system.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center_system.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;
import lk.ijse.mental_health_therapy_center_system.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {
    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_PROGRAM);

    @Override
    public boolean saveTherapyProgram(TherapyProgramDTO therapyProgramDTO) {
        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setName(therapyProgramDTO.getName());
        therapyProgram.setDuration(therapyProgramDTO.getDuration());
        therapyProgram.setFee(therapyProgramDTO.getFee());

        return therapyProgramDAO.save(therapyProgram);
    }

    @Override
    public boolean updateTherapyProgram(TherapyProgramDTO therapyProgramDTO) {
        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setId(therapyProgramDTO.getId());
        therapyProgram.setName(therapyProgramDTO.getName());
        therapyProgram.setDuration(therapyProgramDTO.getDuration());
        therapyProgram.setFee(therapyProgramDTO.getFee());

        return therapyProgramDAO.update(therapyProgram);
    }

    @Override
    public boolean deleteTherapyProgram(int therapyId) {
        return therapyProgramDAO.delete(therapyId);
    }

    @Override
    public List<TherapyProgramDTO> getAllTherapyPrograms() {
        List<TherapyProgramDTO> therapyProgramDTOs = new ArrayList<>();
        for (TherapyProgram therapyProgram : therapyProgramDAO.getAll()) {
            therapyProgramDTOs.add(new TherapyProgramDTO(
                    therapyProgram.getId(),
                    therapyProgram.getName(),
                    therapyProgram.getDuration(),
                    therapyProgram.getFee(),
                    therapyProgram.getStatus()
            ));
        }

        return therapyProgramDTOs;
    }
}
