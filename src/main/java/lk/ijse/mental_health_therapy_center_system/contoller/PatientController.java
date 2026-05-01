package lk.ijse.mental_health_therapy_center_system.contoller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.mental_health_therapy_center_system.AlertMode;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.RegisterBO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;

public class PatientController {
    @FXML
    private Button addPatientBtn;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMediHistory;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Button deletePatientBtn;

    @FXML
    private Button goToDashboardBtn;

    @FXML
    private TextField patientEmailFiled;

    @FXML
    private TextField patientIdFiled;

    @FXML
    private TextArea patientMediHisFiled;

    @FXML
    private TextField patientNameFiled;

    @FXML
    private TextField patientPhoneFiled;

    @FXML
    private TableView<?> patientTable;

    @FXML
    private Button resetPatientFields;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchPatient;

    @FXML
    private Button updatePatientBtn;

    private static final String NAME_REGEX = "^[A-Z][a-zA-Z '.-]*[A-Za-z][^-]$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^[0]{1}[7]{1}[01245678]{1}[0-9]{7}$";

    RegisterBO registerBO = (RegisterBO) BOFactory.getInstance().getBO(BOFactory.BOType.REGISTER);

    // Handle save patient
    @FXML
    private void savePatient() {
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
            boolean isSaved = registerBO.savePatient(new PatientDTO(
                    name,
                    email,
                    phone,
                    mediHistory
            ));

//            loadPatientTable();

            if (isSaved) {
                AlertMode.info("Patient Registration Successfully!");
            }
        }
    }
}