package lk.ijse.mental_health_therapy_center_system.bo.custom.impl;

import lk.ijse.mental_health_therapy_center_system.bo.custom.RegisterBO;
import lk.ijse.mental_health_therapy_center_system.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center_system.entity.Patient;

public class RegisterBOImpl implements RegisterBO {
    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);

    @Override
    public boolean savePatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhone(patientDTO.getPhone());
        patient.setMedicalHistory(patientDTO.getMedicalHistory());

        return patientDAO.save(patient);
    }
}
