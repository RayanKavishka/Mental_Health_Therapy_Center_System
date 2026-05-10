package lk.ijse.mental_health_therapy_center_system.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.RegisterBO;
import lk.ijse.mental_health_therapy_center_system.bo.custom.TherapySessionBO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapySessionDTO;
import lk.ijse.mental_health_therapy_center_system.util.AlertMode;
import lk.ijse.mental_health_therapy_center_system.util.NavigationUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TherapySessionController implements Initializable {
    @FXML
    private ComboBox<PatientDTO> PatientsComboBox;

    @FXML
    private ComboBox<TherapyProgramDTO> ProgramsComboBox;

    @FXML
    private ComboBox<TherapistDTO> TherapistsComboBox;

    @FXML
    private ComboBox<String> TimesComboBox;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPatient;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTherapist;

    @FXML
    private TableColumn<?, ?> colTimePeriod;

    @FXML
    private DatePicker sessionDatePicker;

    @FXML
    private TextField therapySessionId;

    @FXML
    private TableView<TherapySessionDTO> therapySessionTable;

    private final RegisterBO registerBO = (RegisterBO) BOFactory.getInstance().
            getBO(BOFactory.BOType.REGISTER);

    private final TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getInstance().
            getBO(BOFactory.BOType.THERAPY_SESSION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colTimePeriod.setCellValueFactory(new PropertyValueFactory<>("timePeriod"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("therapyProgramId"));

        therapySessionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        for (Object col : therapySessionTable.getColumns()) {
            TableColumn column = (TableColumn) col;
            column.setReorderable(false);
            column.setSortable(false);
        }

        loadTherapySessionTable();

        // Load some comboBoxes
        List<PatientDTO> patients = registerBO.getAllPatients();
        ObservableList<PatientDTO> obPatientsList = FXCollections.observableArrayList();
        obPatientsList.addAll(patients);
        PatientsComboBox.setItems(obPatientsList);

        for (int h = 0; h < 24; h++) {
            TimesComboBox.getItems().add(
                    String.format("%02d:", h) + "00 - " + String.format("%02d:", h+1) + "00"
            );
        }

        startSessionStatusPoller();
    }

    // Load therapy session table
    private void loadTherapySessionTable() {
        try {
            List<TherapySessionDTO> patients = therapySessionBO.getAllTherapySession();
            ObservableList<TherapySessionDTO> obList = FXCollections.observableArrayList();
            obList.addAll(patients);

            therapySessionTable.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Load these per One Minute
    private void startSessionStatusPoller() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(this::loadSessionStatus);
        }, 0, 1, TimeUnit.MINUTES);

        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(this::loadTherapySessionTable);
        }, 0, 1, TimeUnit.MINUTES);
    }

    // Load session status after met the time period
    private void loadSessionStatus() {
        try {
            if(!therapySessionBO.checkSessionTimePeriod()) {
                throw new RuntimeException();
            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Handle load available therapist for selected time period in related date
    @FXML
    private void loadTherapists(MouseEvent event) {
        if (sessionDatePicker.getValue() == null) {
            AlertMode.error("Please Pick Date!");

        } else if (TimesComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Time Period!");

        } else {
            java.util.Date date = java.sql.Date.valueOf(sessionDatePicker.getValue());
            List<TherapistDTO> therapists = therapySessionBO.getAllTherapistBySchedule(
                    TimesComboBox.getSelectionModel().getSelectedItem(), date);

            ObservableList<TherapistDTO> therapistsObList = FXCollections.observableArrayList();
            therapistsObList.addAll(therapists);
            TherapistsComboBox.setItems(therapistsObList);
        }
    }

    // Handle load patient related therapy programs
    @FXML
    private void loadTherapyPrograms(MouseEvent event) {
        if (PatientsComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Patient!");

        } else {
            List<TherapyProgramDTO> therapyPrograms = registerBO.getAllTherapyProgramsByPatient(
                    PatientsComboBox.getSelectionModel().getSelectedItem().getId()
            );
            ObservableList<TherapyProgramDTO> therapyProgramsObList = FXCollections.observableArrayList();
            therapyProgramsObList.addAll(therapyPrograms);
            ProgramsComboBox.setItems(therapyProgramsObList);
        }
    }

    // Handle book session
    @FXML
    private void bookSession() {
        try {
            if (PatientsComboBox.getSelectionModel().isEmpty()) {
                AlertMode.error("Please Select Patient!");

            } else if (ProgramsComboBox.getSelectionModel().isEmpty()) {
                AlertMode.error("Please Select Therapy Program!");

            } else if (sessionDatePicker.getValue() == null) {
                AlertMode.error("Please Pick Date!");

            } else if (TimesComboBox.getSelectionModel().isEmpty()) {
                AlertMode.error("Please Select Time Period!");

            } else if (TherapistsComboBox.getSelectionModel().isEmpty()) {
                AlertMode.error("Please Select Therapist!");

            } else {
                java.util.Date date = java.sql.Date.valueOf(sessionDatePicker.getValue());
                boolean isBooked = therapySessionBO.bookTherapySession(new TherapySessionDTO(
                        date,
                        TimesComboBox.getSelectionModel().getSelectedItem(),
                        "Scheduled",
                        PatientsComboBox.getSelectionModel().getSelectedItem().getId(),
                        TherapistsComboBox.getSelectionModel().getSelectedItem().getId(),
                        ProgramsComboBox.getSelectionModel().getSelectedItem().getId()
                ));

                if (isBooked) {
                    loadTherapySessionTable();
                    resetFields();
                    AlertMode.info("Therapy Session Is Booked Successfully!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Handle update session
    @FXML
    private void updateSession() {
        String sessionId = therapySessionId.getText();

        if (PatientsComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Patient!");

        } else if (ProgramsComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Therapy Program!");

        } else if (sessionDatePicker.getValue() == null) {
            AlertMode.error("Please Pick Date!");

        } else if (TimesComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Time Period!");

        } else if (TherapistsComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Therapist!");

        } else {
            java.util.Date date = java.sql.Date.valueOf(sessionDatePicker.getValue());
            boolean isUpdated = therapySessionBO.updateTherapySession(new TherapySessionDTO(
                    Integer.parseInt(sessionId),
                    date,
                    TimesComboBox.getSelectionModel().getSelectedItem(),
                    "Rescheduled",
                    PatientsComboBox.getSelectionModel().getSelectedItem().getId(),
                    TherapistsComboBox.getSelectionModel().getSelectedItem().getId(),
                    ProgramsComboBox.getSelectionModel().getSelectedItem().getId()
            ));

            if (isUpdated) {
                loadTherapySessionTable();
                resetFields();
                AlertMode.info("Therapy Session Is Updated Successfully!");
            }
        }
    }

    // Handle cancel session
    @FXML
    private void cancelSession() {
        String sessionId = therapySessionId.getText();

        try {
            boolean isCancelled = therapySessionBO.cancelTherapySession(Integer.parseInt(sessionId));

            if (isCancelled) {
                loadTherapySessionTable();
                resetFields();
                AlertMode.info("Therapy Session Is Cancelled Successfully!");
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Reset all fields
    @FXML
    private void resetFields() {
        therapySessionId.setText("");
        TherapistsComboBox.getSelectionModel().clearSelection();
        PatientsComboBox.getSelectionModel().clearSelection();
        ProgramsComboBox.getSelectionModel().clearSelection();
        TimesComboBox.getSelectionModel().clearSelection();
        sessionDatePicker.setValue(null);
    }

    // Handle session table's row click
    @FXML
    private void handleTableRowClick() {
        TherapySessionDTO selectedRow = (TherapySessionDTO) therapySessionTable.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            therapySessionId.setText(String.valueOf(selectedRow.getId()));
            for (TherapistDTO therapist : TherapistsComboBox.getItems()) {
                if (therapist.getId() == selectedRow.getTherapistId()) {
                    TherapistsComboBox.getSelectionModel().select(therapist);
                    break;
                }
            }
            for (PatientDTO patient : PatientsComboBox.getItems()) {
                if (patient.getId() == selectedRow.getPatientId()) {
                    PatientsComboBox.getSelectionModel().select(patient);
                    break;
                }
            }
            for (TherapyProgramDTO program : ProgramsComboBox.getItems()) {
                if (program.getId() == selectedRow.getTherapyProgramId()) {
                    ProgramsComboBox.getSelectionModel().select(program);
                    break;
                }
            }

            TimesComboBox.setValue(selectedRow.getTimePeriod());
            java.util.Date date = selectedRow.getSessionDate();

            LocalDate localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            sessionDatePicker.setValue(localDate);
        }
    }

    @FXML
    private void searchTherapySession() {

    }

    // Navigate to Dashboard
    @FXML
    private void navigateDashboard(MouseEvent event) {
        NavigationUtil.navigate(event, "Dashboard.fxml");
    }
}
