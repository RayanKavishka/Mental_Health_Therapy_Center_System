package lk.ijse.mental_health_therapy_center_system.contoller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.AlertMode;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.TherapyProgramBO;
import lk.ijse.mental_health_therapy_center_system.dto.TherapyProgramDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {
    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Button goToDashboardBtn;

    @FXML
    private Button therapyDeleteBtn;

    @FXML
    private TextField therapyDuration;

    @FXML
    private TextField therapyFee;

    @FXML
    private TextField therapyId;

    @FXML
    private TextField therapyName;

    @FXML
    private TextField therapyProgramSearch;

    @FXML
    private Button therapyProgramSearchBtn;

    @FXML
    private TableView<TherapyProgramDTO> therapyProgramTable;

    @FXML
    private Button therapyResetBtn;

    @FXML
    private Button therapySaveBtn;

    @FXML
    private Button therapyUpdateBtn;

    private static final String NAME_REGEX = "^[A-Z][a-zA-Z '.-]*[A-Za-z][^-]$";
    private static final String DURATION_REGEX = "^\\d+\\s+(week|weeks|month|months|year|years)$";
    private static final String PRICE_REGEX = "^\\d+(\\.\\d{1,2})?$";

    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        therapyProgramTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        for (Object col : therapyProgramTable.getColumns()) {
            TableColumn column = (TableColumn) col;
            column.setReorderable(false);
            column.setSortable(false);
        }

        loadTherapyProgramTable();
    }

    // Load therapy programs table
    private void loadTherapyProgramTable() {
        try {
            List<TherapyProgramDTO> therapyPrograms = therapyProgramBO.getAllTherapyPrograms();
            ObservableList<TherapyProgramDTO> obList = FXCollections.observableArrayList();
            obList.addAll(therapyPrograms);

            therapyProgramTable.setItems(obList);

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    // Handle save therapy program
    @FXML
    private void saveTherapyProgram() {
        String name = therapyName.getText();
        String duration = therapyDuration.getText();
        String fee = therapyFee.getText();

        if (!name.matches(NAME_REGEX)) {
            AlertMode.error("Invalid Name!");

        } else if (!duration.matches(DURATION_REGEX)) {
            AlertMode.error("Invalid Duration!");

        } else if (!fee.matches(PRICE_REGEX)) {
            AlertMode.error("Invalid Fee!");

        } else {
            boolean isSaved = therapyProgramBO.saveTherapyProgram(new TherapyProgramDTO(
                    name,
                    duration,
                    new BigDecimal(fee)
            ));

            if (isSaved) {
                resetAllFields();
                loadTherapyProgramTable();
                AlertMode.info("Therapy Program Saved Successfully!");
            }
        }
    }

    // Handle update therapy program
    @FXML
    private void updateTherapyProgram() {
        String id = therapyId.getText();
        String name = therapyName.getText();
        String duration = therapyDuration.getText();
        String fee = therapyFee.getText();

        if (!name.matches(NAME_REGEX)) {
            AlertMode.error("Invalid Name!");

        } else if (!duration.matches(DURATION_REGEX)) {
            AlertMode.error("Invalid Duration!");

        } else if (!fee.matches(PRICE_REGEX)) {
            AlertMode.error("Invalid Fee!");

        } else {
            boolean isUpdated = therapyProgramBO.updateTherapyProgram(new TherapyProgramDTO(
                    Integer.parseInt(id),
                    name,
                    duration,
                    new BigDecimal(fee)
            ));

            if (isUpdated) {
                resetAllFields();
                loadTherapyProgramTable();
                AlertMode.info("Therapy Program Updated Successfully!");
            }
        }
    }

    // Handle delete therapy program
    @FXML
    private void deleteTherapyProgram() {
        String programId = therapyId.getText();

        boolean isDeleted = therapyProgramBO.deleteTherapyProgram(Integer.parseInt(programId));
        if (isDeleted) {
            resetAllFields();
            loadTherapyProgramTable();
            AlertMode.info("Therapy Program Deleted Successfully!");
        }
    }

    // Set data on fields when click table row
    @FXML
    private void handleTableClick(MouseEvent event) {
        TherapyProgramDTO selectedObj = (TherapyProgramDTO) therapyProgramTable.getSelectionModel().getSelectedItem();
        if (selectedObj != null) {
            therapyId.setText(String.valueOf(selectedObj.getId()));
            therapyName.setText(selectedObj.getName());
            therapyDuration.setText(selectedObj.getDuration());
            therapyFee.setText(String.valueOf(selectedObj.getFee()));
        }
    }

    // Handle reset therapy program fields
    @FXML
    private void resetAllFields() {
        therapyName.setText("");
        therapyDuration.setText("");
        therapyFee.setText("");
    }
}
