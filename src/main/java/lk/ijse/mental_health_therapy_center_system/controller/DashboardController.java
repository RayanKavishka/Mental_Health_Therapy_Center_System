package lk.ijse.mental_health_therapy_center_system.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.*;
import lk.ijse.mental_health_therapy_center_system.util.AlertMode;
import lk.ijse.mental_health_therapy_center_system.util.NavigationUtil;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardController implements Initializable {
    @FXML
    private Label TotalRevenues;

    @FXML
    private Label todaySessions;

    @FXML
    private Label totalTherapists;

    @FXML
    private Label totalTherapyPrograms;

    @FXML
    private Label userLabel;

    private final TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().
            getBO(BOFactory.BOType.THERAPY_PROGRAM);

    private final TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getInstance().
            getBO(BOFactory.BOType.THERAPY_SESSION);

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().
            getBO(BOFactory.BOType.PAYMENT);

    private final AssignmentBO assignmentBO = (AssignmentBO) BOFactory.getInstance().
            getBO(BOFactory.BOType.ASSIGNMENT);

    public static String userRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startSessionStatusPoller();
    }

    private void loadDashboardCards() {
        double revenue = paymentBO.getAllRevenue();
        TotalRevenues.setText(String.valueOf(revenue));

        int sessionCount = therapySessionBO.getAllTodaySessionCount();
        todaySessions.setText(String.valueOf(sessionCount));

        int therapistsCount = assignmentBO.getAllTherapistCount();
        totalTherapists.setText(String.valueOf(therapistsCount));

        int therapyProgramsCont = therapyProgramBO.getAllTherapyProgramCount();
        totalTherapyPrograms.setText(String.valueOf(therapyProgramsCont));

        if (userRole.equals("Receptionist")) {
            userLabel.setText("RECEPTIONIST");

        } else {
            userLabel.setText("ADMIN");
        }
    }

    // Load these per One Minute
    private void startSessionStatusPoller() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(this::loadDashboardCards);
        }, 0, 1, TimeUnit.MINUTES);
    }

    @FXML
    private void navigatePatientsManagement(MouseEvent event) {
        NavigationUtil.navigate(event, "PatientManagement.fxml");
    }

    @FXML
    private void navigatePatientsWithPrograms(MouseEvent event) {
        NavigationUtil.navigate(event, "PatientProgram.fxml");
    }

    @FXML
    private void navigatePaymentsManagement(MouseEvent event) {
        NavigationUtil.navigate(event, "PaymentManagement.fxml");
    }

    @FXML
    private void navigateTherapistsManagement(MouseEvent event) {
        if (userRole.equals("Admin")) {
            NavigationUtil.navigate(event, "TherapistManagement.fxml");

        } else {
            AlertMode.error("Access denied! Admin access only");
        }
    }

    @FXML
    private void navigateTherapyProgramManagement(MouseEvent event) {
        if (userRole.equals("Admin")) {
            NavigationUtil.navigate(event, "TherapyProgramManagement.fxml");

        } else {
            AlertMode.error("Access denied! Admin access only");
        }
    }

    @FXML
    private void navigateTherapySessionsManagement(MouseEvent event) {
        NavigationUtil.navigate(event, "TherapySessionManagement.fxml");
    }

    @FXML
    private void navigateLogin(MouseEvent event) {
        NavigationUtil.navigate(event, "SignIn.fxml");
    }
}