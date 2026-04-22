package lk.ijse.mental_health_therapy_center_system.dao;

import lk.ijse.mental_health_therapy_center_system.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return daoFactory == null ?
                daoFactory = new DAOFactory() :
                daoFactory;
    }

    public enum DAOType {
        PATIENT,
        PATIENT_PROGRAM,
        THERAPY_PROGRAM,
        THERAPIST,
        THERAPIST_PROGRAM,
        THERAPY_SESSION,
        PAYMENT,
        USER
    }

    public SuperDAO getDAO(DAOType daoType) {
        switch(daoType) {
            case PATIENT:
                return new PatientDAOImpl();

            case PATIENT_PROGRAM:
                return new PatientProgramDAOImpl();

            case THERAPY_PROGRAM:
                return new TherapyProgramDAOImpl();

            case THERAPIST:
                return new TherapistDAOImpl();

            case THERAPIST_PROGRAM:
                return new TherapistProgramDAOImpl();

            case THERAPY_SESSION:
                return new TherapySessionDAOImpl();

            case PAYMENT:
                return new PaymentDAOImpl();

            case USER:
                return new UserDAOImpl();

            default:
                return null;
        }
    }
}
