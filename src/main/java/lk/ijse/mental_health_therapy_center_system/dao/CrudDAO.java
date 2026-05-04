package lk.ijse.mental_health_therapy_center_system.dao;

import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(int id);
    List<T> getAll();
}