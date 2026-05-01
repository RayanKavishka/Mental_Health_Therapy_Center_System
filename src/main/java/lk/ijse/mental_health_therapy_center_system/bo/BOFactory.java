package lk.ijse.mental_health_therapy_center_system.bo;

import lk.ijse.mental_health_therapy_center_system.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory daoFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return daoFactory == null ?
                daoFactory = new BOFactory() :
                daoFactory;
    }

    public enum BOType {
        REGISTER,
        THERAPY_PROGRAM,
        ASSIGNMENT,
        THERAPY_SESSION,
        PAYMENT,
        USER
    }

    public SuperBO getBO(BOType boType) {
        switch(boType) {
            case REGISTER:
                return new RegisterBOImpl();

            case THERAPY_PROGRAM:
                return new TherapyProgramBOImpl();

            case ASSIGNMENT:
                return new AssignmentBOImpl();

            case THERAPY_SESSION:
                return new TherapySessionBOImpl();

            case PAYMENT:
                return new PaymentBOImpl();

            case USER:
                return new UserBOImpl();

            default:
                return null;
        }
    }
}
