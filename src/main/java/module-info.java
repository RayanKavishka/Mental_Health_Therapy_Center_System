module lk.ijse.mental_health_therapy_center_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires static lombok;


    opens lk.ijse.mental_health_therapy_center_system to javafx.fxml;
    exports lk.ijse.mental_health_therapy_center_system;
}