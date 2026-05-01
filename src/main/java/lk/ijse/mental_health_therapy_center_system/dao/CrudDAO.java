package lk.ijse.mental_health_therapy_center_system.dao;

public interface CrudDAO<T> extends SuperDAO {
    boolean save(T entity);
}
