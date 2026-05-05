package lk.ijse.mental_health_therapy_center_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.AlertMode;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.AssignmentBO;
import lk.ijse.mental_health_therapy_center_system.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapistDTO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistController implements Initializable {
    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private ComboBox<String> therapistAvailability;

    @FXML
    private TextField therapistId;

    @FXML
    private TextField therapistName;

    @FXML
    private TextField therapistEmail;

    @FXML
    private TableView<TherapistDTO> therapistTable;

    @FXML
    private ComboBox<TherapyProgramDTO> therapyProgramsComboBox;

    private static final String NAME_REGEX = "^[A-Z][a-zA-Z '.-]*[A-Za-z][^-]$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private final AssignmentBO assignmentBO = (AssignmentBO) BOFactory.getInstance().getBO(BOFactory.BOType.ASSIGNMENT);
    private final TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        therapistTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        for (Object col : therapistTable.getColumns()) {
            TableColumn column = (TableColumn) col;
            column.setReorderable(false);
            column.setSortable(false);
        }

        loadTherapistTable();

        try {
            List<TherapyProgramDTO> therapyPrograms = therapyProgramBO.getAllTherapyPrograms();
            ObservableList<TherapyProgramDTO> obList = FXCollections.observableArrayList();
            obList.addAll(therapyPrograms);

            therapyProgramsComboBox.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }

        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Available");
        obList.add("Unavailable");
        therapistAvailability.setItems(obList);
    }

    // Load therapist table
    private void loadTherapistTable() {
        try {
            List<TherapistDTO> therapistDTOs = assignmentBO.getAllTherapist();
            ObservableList<TherapistDTO> obList = FXCollections.observableArrayList();
            obList.addAll(therapistDTOs);

            therapistTable.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Handle save therapist
    @FXML
    private void saveTherapist() {
        String name = therapistName.getText();
        String email = therapistEmail.getText();
        String availability = therapistAvailability.getSelectionModel().getSelectedItem();

        if (!name.matches(NAME_REGEX)) {
            AlertMode.error("Invalid Name!");

        } else if (!email.matches(EMAIL_REGEX)) {
            AlertMode.error("Invalid Email!");

        } else if (therapyProgramsComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Therapy Program!");

        } else if (therapistAvailability.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Availability!");

        } else {
            boolean isSaved = assignmentBO.saveTherapist(new TherapistDTO(
                    name,
                    email,
                    availability
            ), therapyProgramsComboBox.getSelectionModel().getSelectedItem());

            if (isSaved) {
                loadTherapistTable();
                resetAllFields();
                AlertMode.info("Therapist Registration Successfully!");
            }
        }
    }

    // Handle update therapist
    @FXML
    private void updateTherapist() {
        String id = therapistId.getText();
        String name = therapistName.getText();
        String email = therapistEmail.getText();
        String availability = therapistAvailability.getSelectionModel().getSelectedItem();

        if (!name.matches(NAME_REGEX)) {
            AlertMode.error("Invalid Name!");

        } else if (!email.matches(EMAIL_REGEX)) {
            AlertMode.error("Invalid Email!");

        } else if (therapistAvailability.getSelectionModel().isEmpty()) {
            AlertMode.error("Please Select Availability!");

        } else {
            boolean isUpdated = assignmentBO.updateTherapist(new TherapistDTO(
                    Integer.parseInt(id),
                    name,
                    email,
                    availability
            ));

            if (isUpdated) {
                resetAllFields();
                loadTherapistTable();
                AlertMode.info("Therapist Updated Successfully!");
            }
        }
    }

    // Handle delete therapist
    @FXML
    private void deleteTherapist() {
        String id = therapistId.getText();

        boolean isDeleted = assignmentBO.deleteTherapist(Integer.parseInt(id));
        if (isDeleted) {
            resetAllFields();
            loadTherapistTable();
            AlertMode.info("Therapist Deleted Successfully!");
        }
    }

    // Set data on fields when click table row
    @FXML
    private void handleTableClick(MouseEvent event) {
        TherapistDTO selectedObj = (TherapistDTO) therapistTable.getSelectionModel().getSelectedItem();
        if (selectedObj != null) {
            therapistId.setText(String.valueOf(selectedObj.getId()));
            therapistName.setText(selectedObj.getName());
            therapistEmail.setText(selectedObj.getEmail());
            therapistAvailability.setValue(selectedObj.getAvailability());
        }
    }

    // Handle reset therapist fields
    @FXML
    private void resetAllFields() {
        therapistId.setText("");
        therapistName.setText("");
        therapistEmail.setText("");
        therapistAvailability.getSelectionModel().clearSelection();
        therapyProgramsComboBox.getSelectionModel().clearSelection();
    }
}
