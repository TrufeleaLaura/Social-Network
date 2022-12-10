package com.example.social_network_gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import social_network.repository.database.DBUserRepo;
import social_network.service.UserService;

import java.io.IOException;

public class LogInController {
   UserService srv;

    public void setLogInService(UserService srv) {
        this.srv = srv;
    }

    @FXML
    private TextField logInEmail;

    @FXML
    private TextField logInName;

    @FXML
    private Label eroare;



    public void logIn() throws IOException {
        String email = logInEmail.getText();
        String nume = logInName.getText();

        if (email.isEmpty() || nume.isEmpty()){
            eroare.setText("Date invalide");
            eroare.setVisible(true);
        }
        else if (srv.searchUtilizator(email) == null) {
            eroare.setText("Cont inexistent");
            eroare.setVisible(true);
        }
        else if (!srv.searchUtilizator(email).getName().equals(String.valueOf(logInName.getText()))){
            eroare.setText("Nume incorect");
            eroare.setVisible(true);
        }
       else{
            Main main = new Main();
            main.switchToMain("main_.fxml",email);
        }
    }

    public void signUp() throws IOException{
        Main main = new Main();
        main.switchToSignUp("sign_up.fxml");
    }
}
