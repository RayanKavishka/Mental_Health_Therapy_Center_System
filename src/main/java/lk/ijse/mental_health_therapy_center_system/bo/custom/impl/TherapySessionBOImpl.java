package lk.ijse.mental_health_therapy_center_system.bo.custom.impl;

import lk.ijse.mental_health_therapy_center_system.bo.custom.TherapySessionBO;
import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center_system.dao.custom.*;
import lk.ijse.mental_health_therapy_center_system.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapySessionDTO;
import lk.ijse.mental_health_therapy_center_system.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {
    private final TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.THERAPY_SESSION);

    private final PatientDAO patientDAO = (PatientDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.PATIENT);

    private final TherapistDAO therapistDAO = (TherapistDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.THERAPIST);

    private final TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.THERAPY_PROGRAM);

    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public List<TherapySessionDTO> getAllTherapySession() {
        List<TherapySessionDTO> therapySessionDTOs = new ArrayList<>();
        try {
            for (TherapySession therapySession : therapySessionDAO.getAll()) {
                LocalDate localDate = therapySession.getSessionDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                Date date = java.sql.Date.valueOf(localDate);

                therapySessionDTOs.add(new TherapySessionDTO(
                        therapySession.getId(),
                        date,
                        therapySession.getTimePeriod(),
                        therapySession.getStatus(),
                        therapySession.getPatient().getId(),
                        therapySession.getTherapist().getId(),
                        therapySession.getTherapyProgram().getId()
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return therapySessionDTOs;
    }

    @Override
    public List<TherapistDTO> getAllTherapistBySchedule(String timePeriod, Date date) {
        List<TherapistDTO> therapistDTOs = new ArrayList<>();

        List<Therapist> therapists =  therapySessionDAO.getAllBySchedule(timePeriod, date);
        try {
            for (Therapist therapist : therapists) {
                therapistDTOs.add(new TherapistDTO(
                        therapist.getId(),
                        therapist.getName(),
                        therapist.getEmail(),
                        therapist.getAvailability(),
                        therapist.getStatus()
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return therapistDTOs;
    }

    @Override
    public boolean bookTherapySession(TherapySessionDTO therapySessionDTO) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Patient patient = patientDAO.get(therapySessionDTO.getPatientId());
            Therapist therapist = therapistDAO.get(therapySessionDTO.getTherapistId());
            TherapyProgram program = therapyProgramDAO.get(therapySessionDTO.getTherapyProgramId());

            if (patient == null || therapist == null || program == null) {
                transaction.rollback();
                throw new RuntimeException("Invalid foreign key reference (patient/therapist/program not found)");
            }

            TherapySession therapySession = new TherapySession();
            therapySession.setSessionDate(therapySessionDTO.getSessionDate());
            therapySession.setTimePeriod(therapySessionDTO.getTimePeriod());
            therapySession.setStatus(therapySessionDTO.getStatus());
            therapySession.setPatient(patient);
            therapySession.setTherapist(therapist);
            therapySession.setTherapyProgram(program);

            Payment payment = paymentDAO.getPaymentByPatientAndProgramIds(patient.getId(), program.getId());
            payment.setName("Payment is Completed");
            payment.setPaidAmount((
                    payment.getPaidAmount().add(payment.getPendingAmount())
            ));
            payment.setDate(LocalDate.now());
            payment.setPendingAmount(new BigDecimal("0.0"));
            payment.setStatus("Completed");
            payment.setTherapySession(therapySession);

            therapySessionDAO.save(therapySession);
            transaction.commit();
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateTherapySession(TherapySessionDTO therapySessionDTO) {
        try {
            TherapySession therapySession = new TherapySession();
            therapySession.setId(therapySessionDTO.getId());
            therapySession.setSessionDate(therapySessionDTO.getSessionDate());
            therapySession.setTimePeriod(therapySessionDTO.getTimePeriod());
            therapySession.setStatus(therapySessionDTO.getStatus());
            therapySession.setPatient(
                    patientDAO.getPatient(therapySessionDTO.getPatientId())
            );
            therapySession.setTherapist(
                    therapistDAO.getTherapist(therapySessionDTO.getTherapistId())
            );
            therapySession.setTherapyProgram(
                    therapyProgramDAO.getProgram(therapySessionDTO.getTherapyProgramId())
            );

            return therapySessionDAO.update(therapySession);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cancelTherapySession(int therapySessionId) {
        try {
            return therapySessionDAO.delete(therapySessionId);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkSessionTimePeriod() {
        try {
            return therapySessionDAO.checkTimePeriod();

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAllTodaySessionCount() {
        int sessionCount = 0;
        LocalDate today = LocalDate.now();

        for (TherapySessionDTO therapySessionDTO : getAllTherapySession()) {
            java.sql.Date sessionDate = new java.sql.Date(therapySessionDTO.getSessionDate().getTime());
            LocalDate localSessionDate = sessionDate.toLocalDate();

            if (localSessionDate.equals(today)) {
                sessionCount++;
            }
        }

        return sessionCount;
    }
}
