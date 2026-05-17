package lk.ijse.mental_health_therapy_center_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.mental_health_therapy_center_system.bo.BOFactory;
import lk.ijse.mental_health_therapy_center_system.bo.custom.UserBO;
import lk.ijse.mental_health_therapy_center_system.dto.UserDTO;
import lk.ijse.mental_health_therapy_center_system.util.AlertMode;
import lk.ijse.mental_health_therapy_center_system.util.NavigationUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private PasswordField confirmPassField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    private ComboBox<String> userRoleComboBox;

    private final static String USER_NAME_REGEX = "^[a-zA-Z0-9_]{4,15}$";
    private final static String PASSWORD_REGEX = "^.{5,}$";

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Admin");
        obList.add("Receptionist");

        userRoleComboBox.setItems(obList);
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        String role = userRoleComboBox.getSelectionModel().getSelectedItem();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPassField.getText();

        if (userRoleComboBox.getSelectionModel().isEmpty()) {
            AlertMode.error("Please select user Role");

        } else if (!userName.matches(USER_NAME_REGEX)) {
            AlertMode.error("Username must be 4–15 characters and contain only letters, numbers, or underscore");
            userNameField.clear();

        } else if (!password.matches(PASSWORD_REGEX)) {
            AlertMode.error("Password must be at least 5 characters and contain no spaces");
            passwordField.clear();

        } else if (!password.equals(confirmPassword)) {
            AlertMode.error("Password and confirm password must be same");
            confirmPassField.clear();

        } else {
            try {
                if (userBO.saveUser(new UserDTO(
                        userName,
                        password,
                        role
                ))) {
                    AlertMode.info("Account created successfully!");
                    clearFields();
                }

            } catch (Exception e) {
                e.printStackTrace();
                AlertMode.error("Something went wrong!");
            }
        }
    }

    private void clearFields() {
        userRoleComboBox.getSelectionModel().clearSelection();
        userNameField.clear();
        passwordField.clear();
        confirmPassField.clear();
    }

    @FXML
    private void navigateToSignIn(MouseEvent event) {
        NavigationUtil.navigate(event, "SignIn.fxml");
    }
}
