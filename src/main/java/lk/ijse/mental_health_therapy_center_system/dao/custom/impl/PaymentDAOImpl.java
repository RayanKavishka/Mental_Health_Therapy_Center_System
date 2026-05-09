package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.PaymentDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Payment;
import org.hibernate.Session;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment entity) {
        return false;
    }

    @Override
    public boolean update(Payment entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Payment get(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
           return session.get(Payment.class, id);

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return session.createQuery("from Payment", Payment.class)
                    .setCacheable(true)
                    .setCacheRegion("paymentCache")
                    .list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public Payment getPaymentByPatientAndProgramIds(int patientId, int programId) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();

        try {
            String hql = "FROM Payment p " +
                    "WHERE p.patientProgram.patient.id = :patientId " +
                    "AND p.patientProgram.therapyProgram.id = :programId";

            return session.createQuery(hql, Payment.class)
                    .setParameter("patientId", patientId)
                    .setParameter("programId", programId)
                    .uniqueResult();

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
