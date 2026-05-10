package lk.ijse.mental_health_therapy_center_system.dao.custom.impl;

import lk.ijse.mental_health_therapy_center_system.config.FactoryConfiguration;
import lk.ijse.mental_health_therapy_center_system.dao.custom.TherapySessionDAO;
import lk.ijse.mental_health_therapy_center_system.entity.Therapist;
import lk.ijse.mental_health_therapy_center_system.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {
    @Override
    public boolean save(TherapySession entity) {
        Session session = FactoryConfiguration.getInstance().getCurrentSession();

        try {
            session.persist(entity);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public boolean update(TherapySession entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            TherapySession therapySession = session.get(TherapySession.class, entity.getId());
            therapySession.setSessionDate(entity.getSessionDate());
            therapySession.setTimePeriod(entity.getTimePeriod());
            therapySession.setStatus(entity.getStatus());
            therapySession.setPatient(entity.getPatient());
            therapySession.setTherapist(entity.getTherapist());
            therapySession.setTherapyProgram(entity.getTherapyProgram());

            transaction.commit();
            return true;

        } catch (RuntimeException e) {
            transaction.rollback();
            throw new RuntimeException(e);

        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            TherapySession therapySession = session.get(TherapySession.class, id);
            therapySession.setStatus("Cancelled");
            transaction.commit();
            return true;

        } catch (RuntimeException e) {
            transaction.rollback();
            throw new RuntimeException(e);

        } finally {
            session.close();
        }
    }

    @Override
    public TherapySession get(int id) {
        return null;
    }

    @Override
    public List<TherapySession> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return session.createQuery("from TherapySession", TherapySession.class)
                    .setCacheable(true)
                    .setCacheRegion("therapySessionCache")
                    .list();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            session.close();
        }
    }

    @Override
    public List<Therapist> getAllBySchedule(String timePeriod, Date date) {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            String hql = "FROM Therapist t WHERE t.id NOT IN ( " +
                    "SELECT ts.therapist.id FROM TherapySession ts " +
                    "WHERE ts.timePeriod = :timePeriod AND DATE(ts.sessionDate) = :sessionDate) ";


            Query<Therapist> query = session.createQuery(hql, Therapist.class);
            query.setParameter("timePeriod", timePeriod);
            query.setParameter("sessionDate", date);

            return query.list();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            session.close();
        }
    }

    @Override
    public boolean checkTimePeriod() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            List<TherapySession> therapySessions = session
                    .createQuery("from TherapySession", TherapySession.class)
                    .setCacheable(true)
                    .setCacheRegion("therapySessionCache")
                    .list();

            LocalDate today = LocalDate.now();
            for (TherapySession therapySession : therapySessions) {
                LocalDate sessionDate = therapySession.getSessionDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                if (sessionDate.equals(today)) {
                    String[] timePeriod = therapySession.getTimePeriod().split("-");
                    String endTimeString = timePeriod[1].trim();
                    LocalTime endTime = LocalTime.parse(endTimeString);

                    if (endTime.equals(LocalTime.now().withSecond(0).withNano(0))) {
                        therapySession.setStatus("Completed");
                    }
                }
            }

            transaction.commit();
            return true;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);

        } finally {
            session.close();
        }
    }
}
