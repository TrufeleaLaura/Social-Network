package com.example.social_network_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import social_network.Validators.FriendshipValidator;
import social_network.Validators.UserValidator;
import social_network.domain.User;
import social_network.repository.database.*;
import social_network.service.*;

import java.io.IOException;


public class Main extends Application {

    String url = "jdbc:postgresql://localhost:5432/social_network";
    String username = "postgres";
    String password = "postgres";

    DBUserRepo repoUser = new DBUserRepo(url , username , password);

    DBMessageRepo repoMessage=new DBMessageRepo(url , username , password);
    FriendshipValidator frval=new FriendshipValidator();
    UserValidator userval=new UserValidator();
    DBFriendshipRepo repoFriendship = new DBFriendshipRepo(url, username, password);

    FriendshipService friendshipService = new FriendshipService(repoFriendship, repoUser);
    UserService userService = new UserService(repoUser, repoFriendship);

    AppService mainservice = new AppService(repoUser,repoFriendship,userval,frval, repoMessage);


    private static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        AnchorPane root=loader.load();
        LogInController controller=loader.getController();
        controller.setLogInService(userService);
        currentStage.setScene(new Scene(root, 559, 311));
        currentStage.setTitle("Log in");
        currentStage.show();
    }


    public void switchToLogIn(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        AnchorPane root=loader.load();
        LogInController controller=loader.getController();
        controller.setLogInService(userService);
        currentStage.setScene(new Scene(root, 559, 311));
        currentStage.setTitle("Log in");
        currentStage.show();
    }

    public void switchToSignUp(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        AnchorPane root=loader.load();
        SignUpController controller=loader.getController();
        controller.setSignUpService(userService);
        currentStage.setScene(new Scene(root, 474, 500));
        currentStage.setTitle("Sign up");
        currentStage.show();
    }

    public void switchToChat(String fxml, User user) throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        AnchorPane root=loader.load();
        MessageController controller=loader.getController();
        controller.setService(mainservice,user);
        currentStage.setScene(new Scene(root, 474, 500));
        currentStage.setTitle("Chat");
        currentStage.show();
    }


    public void switchToMain(String fxml,String email) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        AnchorPane root=loader.load();
        MainController controller=loader.getController();
        controller.setService(mainservice,email);
        currentStage.setScene(new Scene(root, 670, 440));
        currentStage.setTitle("Main page");
        currentStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}