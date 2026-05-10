package lk.ijse.mental_health_therapy_center_system.bo.custom.impl;

import lk.ijse.mental_health_therapy_center_system.bo.custom.PaymentBO;
import lk.ijse.mental_health_therapy_center_system.dao.DAOFactory;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PaymentDAO;
import lk.ijse.mental_health_therapy_center_system.dto.PaymentDTO;
import lk.ijse.mental_health_therapy_center_system.entity.Payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public List<PaymentDTO> getAllPayment() {
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : paymentDAO.getAll()) {
            paymentDTOs.add(new PaymentDTO(
                    payment.getId(),
                    payment.getName(),
                    payment.getPatientProgram().getPatient().getId(),
                    payment.getPaidAmount(),
                    payment.getDate(),
                    payment.getPendingAmount(),
                    payment.getStatus()
            ));
        }

        return paymentDTOs;
    }

    @Override
    public double getAllRevenue() {
        double revenue = 0;
        for (PaymentDTO paymentDTO : getAllPayment()) {
            revenue += paymentDTO.getPaidAmount().doubleValue();
        }

        return revenue;
    }

    @Override
    public PaymentDTO getPayment(int paymentId) {
        Payment payment = paymentDAO.get(paymentId);

        if (payment == null) {
            throw new NullPointerException();

        } else {
            return new PaymentDTO(
                    payment.getId(),
                    payment.getName(),
                    payment.getPatientProgram().getPatient().getId(),
                    payment.getPaidAmount(),
                    payment.getDate(),
                    payment.getPendingAmount(),
                    payment.getStatus()
            );
        }
    }
}
