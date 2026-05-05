module lk.ijse.mental_health_therapy_center_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires static lombok;
    requires cache.api;
    requires org.controlsfx.controls;

    // Allow To Hibernate to reflect on entity classes
    opens lk.ijse.mental_health_therapy_center_system.entity to org.hibernate.orm.core;
    opens lk.ijse.mental_health_therapy_center_system to javafx.fxml;
    opens lk.ijse.mental_health_therapy_center_system.controller to javafx.fxml;
    opens lk.ijse.mental_health_therapy_center_system.dto to javafx.base;

    exports lk.ijse.mental_health_therapy_center_system;
    exports lk.ijse.mental_health_therapy_center_system.controller;
}