package lk.ijse.mental_health_therapy_center_system.bo.custom.impl;

import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapyProgramDAO;
import lk.ijse.mental_health_therapy_center_system.entity.*;
import lk.ijse.mental_health_therapy_center_system.tm.PatientEnrolledProgramsTM;
import lk.ijse.mental_health_therapy_center_system.util.AlertMode;
import lk.ijse.mental_health_therapy_center_system.bo.custom.RegisterBO;
import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PatientDAO;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PatientProgramDAO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegisterBOImpl implements RegisterBO {
    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance()
            .getDAO(DAOFactory.DAOType.PATIENT);

    private final PatientProgramDAO patientProgramDAO = (PatientProgramDAO) DAOFactory.getInstance().
            getDAO(DAOFactory.DAOType.PATIENT_PROGRAM);

    private final TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().
            getDAO(DAOFactory.DAOType.THERAPY_PROGRAM);

    @Override
    public boolean savePatient(PatientDTO patientDTO, TherapyProgramDTO therapyProgramDTO, BigDecimal payAmount) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Patient existingPatient = session.get(Patient.class, patientDTO.getId());
        try {
            if (existingPatient == null) {
                // Set patient
                Patient patient = new Patient();
                patient.setName(patientDTO.getName());
                patient.setEmail(patientDTO.getEmail());
                patient.setPhone(patientDTO.getPhone());
                patient.setMedicalHistory(patientDTO.getMedicalHistory());
                patient.setStatus("Active");

                // Get therapy program
                TherapyProgram therapyProgram = session.get(TherapyProgram.class, therapyProgramDTO.getId());
                if (therapyProgram == null) {
                    transaction.rollback();
                    return false;
                }

                // Set patient program
                PatientProgram patientProgram = new PatientProgram();
                patientProgram.setPatient(patient);
                patientProgram.setTherapyProgram(therapyProgram);

                // Add PatientProgram into Patient
                patient.getPatientPrograms().add(patientProgram);

                // Add payment
                Payment payment = new Payment();
                String payStatus = "Pending";
                if (payAmount.compareTo(therapyProgram.getFee()) < 0) {
                    payStatus = "Pending";
                    payment.setName("Upfront Payment");

                } else if (payAmount.compareTo(therapyProgram.getFee()) == 0) {
                    payStatus = "Completed";
                    payment.setName("Payment is Completed");

                } else {
                    AlertMode.error("Something went wrong!");
                    throw new RuntimeException();
                }
                payment.setStatus(payStatus);
                payment.setPaidAmount(payAmount);
                payment.setPendingAmount(therapyProgram.getFee().subtract(payAmount));
                payment.setDate(LocalDate.now());

                payment.setPatientProgram(patientProgram);

                // Set payment into PatientProgram
                patientProgram.setPayment(payment);

                patientDAO.save(patient);
                transaction.commit();
                return true;

            } else {
                // Get therapy program
                TherapyProgram therapyProgram = session.get(TherapyProgram.class, therapyProgramDTO.getId());
                if (therapyProgram == null) {
                    transaction.rollback();
                    return false;
                }

                // Set patient program
                PatientProgram patientProgram = new PatientProgram();
                patientProgram.setPatient(existingPatient);
                patientProgram.setTherapyProgram(therapyProgram);

                // Add PatientProgram into Patient
                existingPatient.getPatientPrograms().add(patientProgram);

                // Add payment
                Payment payment = new Payment();
                String payStatus = "Pending";
                if (payAmount.compareTo(therapyProgram.getFee()) < 0) {
                    payStatus = "Pending";
                    payment.setName("Upfront Payment");

                } else if (payAmount.compareTo(therapyProgram.getFee()) == 0) {
                    payStatus = "Completed";
                    payment.setName("Payment is Completed");

                } else {
                    AlertMode.error("Something went wrong!");
                    throw new RuntimeException();
                }
                payment.setStatus(payStatus);
                payment.setPaidAmount(payAmount);
                payment.setPendingAmount(therapyProgram.getFee().subtract(payAmount));
                payment.setDate(LocalDate.now());

                payment.setPatientProgram(patientProgram);

                // Set payment into PatientProgram
                patientProgram.setPayment(payment);

                session.merge(existingPatient);
                transaction.commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhone(patientDTO.getPhone());
        patient.setMedicalHistory(patientDTO.getMedicalHistory());

        return patientDAO.update(patient);
    }

    @Override
    public boolean deletePatient(int patientId) {
        return patientDAO.delete(patientId);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> patientDTOs = new ArrayList<>();
        for (Patient patient : patientDAO.getAll()) {
            patientDTOs.add(new PatientDTO(
                    patient.getId(),
                    patient.getName(),
                    patient.getEmail(),
                    patient.getPhone(),
                    patient.getMedicalHistory(),
                    patient.getStatus()
            ));
        }

        return patientDTOs;
    }

    @Override
    public List<PatientEnrolledProgramsTM> getAllPatientPrograms() {
        List<PatientEnrolledProgramsTM> patientEnrolledProgramsTMs = new ArrayList<>();

        int therapyProgramCount = 0;
        for (TherapyProgram therapyProgram : therapyProgramDAO.getAll()) {
            if (therapyProgram.getStatus().equals("Active")) {
                therapyProgramCount++;
            }
        }

        for (PatientProgram patientProgram : patientProgramDAO.getAll()) {
            int patientId = patientProgram.getPatient().getId();

            boolean alreadyAdded = patientEnrolledProgramsTMs.stream()
                    .anyMatch(tm -> tm.getPatientId() == patientId);

            if (alreadyAdded) {
                continue;
            }

            Long enrolledProgramsCount = patientProgramDAO.getProgramEnrolledCount(patientId);

            String status = "Not All Programs";

            if (enrolledProgramsCount == therapyProgramCount) {
                status = "All Programs";
            }

            patientEnrolledProgramsTMs.add(new PatientEnrolledProgramsTM(
                    patientProgram.getId(),
                    patientId,
                    patientProgram.getPatient().getName(),
                    enrolledProgramsCount,
                    status
            ));
        }

        return patientEnrolledProgramsTMs;
    }

    @Override
    public List<PatientEnrolledProgramsTM> getPatientsEnrolledAllPrograms() {
        List<PatientEnrolledProgramsTM> allProgramsEnrolledPatients = new ArrayList<>();

        for (PatientEnrolledProgramsTM patientEnrolledProgramsTM : getAllPatientPrograms()) {
            if (patientEnrolledProgramsTM.getEnrolledStatus().equals("All Programs")) {
                allProgramsEnrolledPatients.add(patientEnrolledProgramsTM);
            }
        }

        return allProgramsEnrolledPatients;
    }

    @Override
    public List<TherapyProgramDTO> getAllTherapyProgramsByPatient(int id) {
        try {
            List<TherapyProgramDTO> therapyProgramDTOs = new ArrayList<>();
            for (TherapyProgram program : patientProgramDAO.getAllTherapyProgram(id)) {
                therapyProgramDTOs.add(new TherapyProgramDTO(
                        program.getId(),
                        program.getName(),
                        program.getDuration(),
                        program.getFee(),
                        program.getStatus()
                ));
            }

            return therapyProgramDTOs;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PatientDTO getPatientById(int patientId) {
        Patient patient = patientDAO.getPatient(patientId);
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getMedicalHistory(),
                patient.getStatus()
        );
    }
}