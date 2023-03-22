package com.developersstack.medex.controller;

import com.developersstack.medex.db.Database;
import com.developersstack.medex.entity.UserDTO;
import com.developersstack.medex.enums.AccountType;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SignupFormController {
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPwd;
    public ToggleGroup accountType;
    public AnchorPane signUpContext;
    public JFXRadioButton rBtnDoctor;

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi();
    }

    public void signUpOnActtion(ActionEvent actionEvent) throws IOException {

        String email = txtEmail.getText().trim().toLowerCase();

//        for (UserDTO dto : Database.userTable) {
//            if (dto.getEmail().equals(email.trim().toLowerCase(Locale.ROOT))) {
//                new Alert(Alert.AlertType.WARNING, String.format("Email (%s) already exists!", email)).show();
//                return;
//            }
//            else{
//
//            }
//
//        }

        Optional<UserDTO> selectUser = Database.userTable.stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst();

        if (selectUser.isPresent()) {
            new Alert(Alert.AlertType.WARNING, "Email is already exits!").show();
            return;
        }

        Database.userTable.add(
                new UserDTO(
                        txtFirstName.getText(),
                        txtLastName.getText(),
                        email,
                        txtPwd.getText(),
                        rBtnDoctor.isSelected() ? AccountType.DOCTOR : AccountType.PATIENT)
        );
        new Alert(Alert.AlertType.CONFIRMATION, "Welcome").show();
        setUi();

    }

    private void setUi() throws IOException {
        Stage stage = (Stage) signUpContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.centerOnScreen();
    }
}
