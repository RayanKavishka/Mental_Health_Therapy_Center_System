package lk.ijse.mental_health_therapy_center_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.util.AlertMode;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.PaymentBO;
import lk.ijse.mental_health_therapy_center_system.dto.PaymentDTO;
import lk.ijse.mental_health_therapy_center_system.util.NavigationUtil;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPaidAmount;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colPendingAmount;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private Label collectedTotal;

    @FXML
    private Label PendingTotal;

    @FXML
    private Label invoicePaymentId;

    @FXML
    private Label invoicePatientId;

    @FXML
    private Label invoiceDescription;

    @FXML
    private Label invoiceDate;

    @FXML
    private Label invoicePaidAmount;

    @FXML
    private Label invoicePendingAmount;

    @FXML
    private TextField paymentIdField;

    @FXML
    private TableView<PaymentDTO> paymentTable;

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPendingAmount.setCellValueFactory(new PropertyValueFactory<>("pendingAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        paymentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        for (Object col : paymentTable.getColumns()) {
            TableColumn column = (TableColumn) col;
            column.setReorderable(false);
            column.setSortable(false);
        }

        List<PaymentDTO> paymentDTOS = paymentBO.getAllPayment();
        loadPaymentTable(paymentDTOS);
        setPendingAmount(paymentDTOS);
        setCollectedAmount(paymentDTOS);
    }

    // Load payment table
    private void loadPaymentTable(List<PaymentDTO> paymentDTOS) {
        ObservableList<PaymentDTO> obList = FXCollections.observableArrayList();
        obList.addAll(paymentDTOS);

        paymentTable.setItems(obList);
    }

    // Set pending amount
    private void setPendingAmount(List<PaymentDTO> paymentDTOS) {
        BigDecimal pendingAmountTot = new BigDecimal("0.0");

        for (PaymentDTO paymentDTO : paymentDTOS) {
            pendingAmountTot = pendingAmountTot.add(paymentDTO.getPendingAmount());
        }

        PendingTotal.setText(pendingAmountTot.toString());
    }

    // Set collected amount
    private void setCollectedAmount(List<PaymentDTO> paymentDTOS) {
        BigDecimal collectedAmountTot = new BigDecimal("0.0");

        for (PaymentDTO paymentDTO : paymentDTOS) {
            collectedAmountTot = collectedAmountTot.add(paymentDTO.getPaidAmount());
        }

        collectedTotal.setText(collectedAmountTot.toString());
    }

    @FXML
    private void printInvoice(ActionEvent event) {
        String payId = paymentIdField.getText();

        try {
            PaymentDTO paymentDTO = paymentBO.getPayment(Integer.parseInt(payId));

            invoicePaymentId.setText(payId);
            invoicePatientId.setText(String.valueOf(paymentDTO.getPatientId()));
            invoiceDescription.setText(paymentDTO.getName());
            invoiceDate.setText(paymentDTO.getDate().toString());
            invoicePaidAmount.setText(String.format("%.2f", paymentDTO.getPaidAmount()));
            invoicePendingAmount.setText(String.format("%.2f", paymentDTO.getPendingAmount()));

            AlertMode.info("Invoice is printed successfully");

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Oops! This Payment is not found.");
        }
    }

    // Navigate to Dashboard
    @FXML
    private void navigateDashboard(MouseEvent event) {
        NavigationUtil.navigate(event, "Dashboard.fxml");
    }
}