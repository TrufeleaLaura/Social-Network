package com.example.social_network_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import social_network.exceptions.ExistingException;
import social_network.service.UserService;
import social_network.domain.User;
import social_network.Validators.UserValidator;
import social_network.exceptions.ValidationException;
import social_network.service.*;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    UserService srv;
   // UserValidator val;

    @FXML
    private TextField signUpEmail;
    @FXML
    private TextField signUpNume;
    @FXML
    private TextField signUpId;

    @FXML
    private Label eroare;

    public void setSignUpService(UserService service) {
        this.srv=service;
    }

    public void logIn() throws IOException{
        Main main = new Main();
        main.switchToLogIn("login.fxml");
    }

    public void signUp() throws  IOException {
        String email = signUpEmail.getText();
        String nume = signUpNume.getText();

        if (email.isEmpty() || nume.isEmpty()) {
            eroare.setText("Date invalide");
            eroare.setVisible(true);
        }
        Long id = Long.parseLong(signUpId.getText());
        if (srv.searchUtilizator_id(id) != null) {
            eroare.setText("Date invalide");
            eroare.setVisible(true);
        } else {
            try {

                srv.addUserID(id, nume, email);
                Main main = new Main();
                main.switchToLogIn("login.fxml");
            } catch (ValidationException e) {
                eroare.setText("Date invalide");
                eroare.setVisible(true);
            }
        }
    }
    }


