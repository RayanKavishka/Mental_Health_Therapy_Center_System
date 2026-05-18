package lk.ijse.mental_health_therapy_center_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center_system.dto.UserDTO;
import lk.ijse.mental_health_therapy_center_system.exception.InvalidUserException;
import lk.ijse.mental_health_therapy_center_system.exception.MissingFieldsException;
import lk.ijse.mental_health_therapy_center_system.util.AlertMode;
import lk.ijse.mental_health_therapy_center_system.util.NavigationUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    private ComboBox<String> userRoleComboBox;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Admin");
        obList.add("Receptionist");

        userRoleComboBox.setItems(obList);
    }

    @FXML
    private void handleSignIn(MouseEvent event) {
        String role = userRoleComboBox.getSelectionModel().getSelectedItem();
        String userName = userNameField.getText();
        String password = passwordField.getText();

        try {
            if (userRoleComboBox.getSelectionModel().isEmpty()) {
                throw new MissingFieldsException("Please select user Role");

            } else if (userName.isEmpty()) {
                throw new MissingFieldsException("Please Type user name");

            } else if (password.isEmpty()) {
                throw new MissingFieldsException("Please type password");

            } else {
                if (userBO.checkCredentials(new UserDTO(
                        userName,
                        password,
                        role
                ))) {
                    NavigationUtil.navigate(event, "Dashboard.fxml");
                    DashboardController.userRole = role;
                    AlertMode.info("Login successfully!");
                    clearFields();

                } else {
                    throw new InvalidUserException("Oops! Invalid credentials. try again!");
                }
            }

        } catch (MissingFieldsException | InvalidUserException me) {
            AlertMode.error(me.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            AlertMode.error("Something went wrong!");
        }
    }

    private void clearFields() {
        userRoleComboBox.getSelectionModel().clearSelection();
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    private void navigateToSignUp(MouseEvent event) {
        NavigationUtil.navigate(event, "SignUp.fxml");
    }
}