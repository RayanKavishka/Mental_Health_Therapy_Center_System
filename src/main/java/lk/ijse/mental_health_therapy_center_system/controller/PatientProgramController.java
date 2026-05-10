package lk.ijse.mental_health_therapy_center_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.RegisterBO;
import lk.ijse.mental_health_therapy_center_system.dto.PatientDTO;
import lk.ijse.mental_health_therapy_center_system.entity.PatientProgram;
import lk.ijse.mental_health_therapy_center_system.tm.PatientEnrolledProgramsTM;
import lk.ijse.mental_health_therapy_center_system.util.AlertMode;
import lk.ijse.mental_health_therapy_center_system.util.NavigationUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PatientProgramController implements Initializable {
    @FXML
    private TableColumn<?, ?> colEnrolledProgramsCount;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableColumn<?, ?> colEnrolledStatus;

    @FXML
    private TableView<PatientEnrolledProgramsTM> patientProgramTable;

    private final RegisterBO registerBO = (RegisterBO) BOFactory.getInstance().getBO(BOFactory.BOType.REGISTER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colEnrolledProgramsCount.setCellValueFactory(new PropertyValueFactory<>("enrolledPrograms"));
        colEnrolledStatus.setCellValueFactory(new PropertyValueFactory<>("enrolledStatus"));

        patientProgramTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        for (Object col : patientProgramTable.getColumns()) {
            TableColumn column = (TableColumn) col;
            column.setReorderable(false);
            column.setSortable(false);
        }

        loadPatientProgramTable();
    }

    // Load table
    private void loadPatientProgramTable() {
        try {
            List<PatientEnrolledProgramsTM> patients = registerBO.getAllPatientPrograms();
            ObservableList<PatientEnrolledProgramsTM> obList = FXCollections.observableArrayList();
            obList.addAll(patients);

            patientProgramTable.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Load patients who enrolled all programs
    @FXML
    private void filterAllPatients(ActionEvent event) {
        try {
            List<PatientEnrolledProgramsTM> patients = registerBO.getPatientsEnrolledAllPrograms();
            ObservableList<PatientEnrolledProgramsTM> obList = FXCollections.observableArrayList();
            obList.addAll(patients);

            patientProgramTable.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    @FXML
    private void refreshTable() {
        loadPatientProgramTable();
    }

    // Navigate to Dashboard
    @FXML
    private void navigateDashboard(MouseEvent event) {
        NavigationUtil.navigate(event, "Dashboard.fxml");
    }
}
