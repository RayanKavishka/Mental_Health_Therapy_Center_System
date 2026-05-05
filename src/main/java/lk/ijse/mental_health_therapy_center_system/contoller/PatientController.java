package lk.ijse.mental_health_therapy_center_system.contoller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.AlertMode;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.RegisterBO;
import lk.ijse.mental_health_therapy_center_system.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {
    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMediHistory;

    @FXML
    private Label totalAmount;

    @FXML
    private TextField yourAmount;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TextField patientEmailFiled;

    @FXML
    private TextField patientIdFiled;

    @FXML
    private ComboBox<TherapyProgramDTO> therapyProgramsComboBox;

    @FXML
    private TextArea patientMediHisFiled;

    @FXML
    private TextField patientNameFiled;

    @FXML
    private TextField patientPhoneFiled;

    @FXML
    private TableView<PatientDTO> patientTable;

    @FXML
    private TextField searchPatient;

    private static final String NAME_REGEX = "^[A-Z][a-zA-Z '.-]*[A-Za-z][^-]$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^[0]{1}[7]{1}[01245678]{1}[0-9]{7}$";

    RegisterBO registerBO = (RegisterBO) BOFactory.getInstance().getBO(BOFactory.BOType.REGISTER);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colMediHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        patientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        for (Object col : patientTable.getColumns()) {
            TableColumn column = (TableColumn) col;
            column.setReorderable(false);
            column.setSortable(false);
        }

        loadPatientTable();

        try {
            List<TherapyProgramDTO> therapyPrograms = therapyProgramBO.getAllTherapyPrograms();
            ObservableList<TherapyProgramDTO> obList = FXCollections.observableArrayList();
            obList.addAll(therapyPrograms);

            therapyProgramsComboBox.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }

        // Handle load full payment of therapy Program
        therapyProgramsComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        totalAmount.setText(newVal.getFee().toString());
                    }
                });
    }

    // Load therapy programs table
    private void loadPatientTable() {
        try {
            List<PatientDTO> patients = registerBO.getAllPatients();
            ObservableList<PatientDTO> obList = FXCollections.observableArrayList();
            obList.addAll(patients);

            patientTable.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Handle save patient
    @FXML
    private void savePatient() {
        String name = patientNameFiled.getText();
        String email = patientEmailFiled.getText();
        String phone = patientPhoneFiled.getText();
        String mediHistory = patientMediHisFiled.getText();

        String payAmount = yourAmount.getText();

        if (!name.matches(NAME_REGEX)) {
            AlertMode.error("Invalid Name!");

        } else if (!email.matches(EMAIL_REGEX)) {
            AlertMode.error("Invalid Email!");

        } else if (!phone.matches(PHONE_REGEX)) {
            AlertMode.error("Invalid Phone Number!");

        } else if (mediHistory.trim().isEmpty()) {
            AlertMode.error("Invalid Medical History!");

        } else if (therapyProgramsComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Therapy Program!");

        } else if (payAmount.isEmpty()) {
            AlertMode.error("Please Type Your Payment Amount!");

        } else {
            boolean isSaved = registerBO.savePatient(new PatientDTO(
                    name,
                    email,
                    phone,
                    mediHistory
            ), therapyProgramsComboBox.getSelectionModel().getSelectedItem(), new BigDecimal(payAmount));

            if (isSaved) {
                loadPatientTable();
                resetAllFields();
                AlertMode.info("Patient Registration Successfully!");
            }
        }
    }

    // Handle update patient
    @FXML
    private void updatePatient() {
        String id = patientIdFiled.getText();
        String name = patientNameFiled.getText();
        String email = patientEmailFiled.getText();
        String phone = patientPhoneFiled.getText();
        String mediHistory = patientMediHisFiled.getText();

        if (!name.matches(NAME_REGEX)) {
            AlertMode.error("Invalid Name!");

        } else if (!email.matches(EMAIL_REGEX)) {
            AlertMode.error("Invalid Email!");

        } else if (!phone.matches(PHONE_REGEX)) {
            AlertMode.error("Invalid Phone Number!");

        } else if (mediHistory.trim().isEmpty()) {
            AlertMode.error("Invalid Medical History!");

        } else {
            boolean isUpdated = registerBO.updatePatient(new PatientDTO(
                    Integer.parseInt(id),
                    name,
                    email,
                    phone,
                    mediHistory
            ));

            if (isUpdated) {
                resetAllFields();
                loadPatientTable();
                AlertMode.info("Patient Updated Successfully!");
            }
        }
    }

    // Handle delete patient
    @FXML
    private void deletePatient() {
        String programId = patientIdFiled.getText();

        boolean isDeleted = registerBO.deletePatient(Integer.parseInt(programId));
        if (isDeleted) {
            resetAllFields();
            loadPatientTable();
            AlertMode.info("Patient Deleted Successfully!");
        }
    }

    // Set data on fields when click table row
    @FXML
    private void handleTableClick(MouseEvent event) {
        PatientDTO selectedObj = (PatientDTO) patientTable.getSelectionModel().getSelectedItem();
        if (selectedObj != null) {
            patientIdFiled.setText(String.valueOf(selectedObj.getId()));
            patientNameFiled.setText(selectedObj.getName());
            patientEmailFiled.setText(selectedObj.getEmail());
            patientPhoneFiled.setText(String.valueOf(selectedObj.getPhone()));
            patientMediHisFiled.setText(String.valueOf(selectedObj.getMedicalHistory()));
        }
    }

    // Handle reset therapy program fields
    @FXML
    private void resetAllFields() {
        patientNameFiled.setText("");
        patientEmailFiled.setText("");
        patientPhoneFiled.setText("");
        patientMediHisFiled.setText("");
        yourAmount.setText("");
        totalAmount.setText("");
        therapyProgramsComboBox.getSelectionModel().clearSelection();
    }
}