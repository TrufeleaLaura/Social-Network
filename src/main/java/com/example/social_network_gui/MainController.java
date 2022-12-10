package com.example.social_network_gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import social_network.Observer.Observer;
import social_network.domain.*;
import social_network.domain.UserDTOFriend;
import social_network.service.AppService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainController implements Observer {
    private ObservableList<User> usersList = FXCollections.observableArrayList();
    private ObservableList<UserDTOFriend> friendList = FXCollections.observableArrayList();

    private ObservableList<User> friendRequestsList = FXCollections.observableArrayList();
    @FXML
    public Label connectedUserLabel;
    @FXML
    public Label emailUserLabel;
    @FXML
    public TextField searchUserTextField;
    private AppService service;
    private User loggedUser = null;
    @FXML
    public Button logoutButton;
    @FXML
    public TableColumn<User, String> NameColumn;
    @FXML
    public TableColumn<User, String> emailColumn;
    @FXML
    public TableColumn<UserDTOFriend, String> friendNameColumn;
    @FXML
    public TableColumn<UserDTOFriend, String> friendsSinceColumn;
    @FXML
    public TableView<UserDTOFriend> friendsTableView;
    @FXML
    public ListView<User> friendRequestsListView;
    @FXML
    public TableView<User> usersTable;
    public void setService(AppService service, String email) {

        //  friendRequestsPane.setVisible(visibleFriendrequestsPane);
        this.service = service;
        this.service.addObserver(this);
        this.loggedUser = service.getUserByEmail(email);

        connectedUserLabel.setText(loggedUser.getName());
        emailUserLabel.setText(loggedUser.getEmail());
        initLists();
    }

    @Override
    public void update() {
         initLists();
    }
    public void logOut() throws IOException{
        Main main = new Main();
        main.switchToLogIn("login.fxml");
    }


    @FXML
    public void initialize() {
        friendNameColumn.setCellValueFactory(new PropertyValueFactory<UserDTOFriend, String>("name_user"));
        friendsSinceColumn.setCellValueFactory(new PropertyValueFactory<UserDTOFriend, String>("friendsSince"));


        NameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        usersTable.setItems(usersList);
        friendRequestsListView.setItems(friendRequestsList);
        friendsTableView.setItems(friendList);


        //searchUserTextField.textProperty().addListener(o -> onSearchUserTextField());
    }
    private void initLists() {
        HashMap<User, String> friendsOfUser = service.getFriends(loggedUser.getId());
        List<UserDTOFriend> friendsTemp = new ArrayList<>();
        for (User user : friendsOfUser.keySet()) {
            friendsTemp.add(new UserDTOFriend(user,friendsOfUser.get(user) ));
        }
        friendList.setAll(friendsTemp);


        List<User> friendReqTemp = new ArrayList<>();
        List<User> friendReqOfUsers = service.getFriendRequests(loggedUser.getId());
        for (User user : friendReqOfUsers) {
            friendReqTemp.add(user);
        }

        friendRequestsList.setAll(friendReqTemp);



        List<User> allUsers = service.getAllUsers();
        List<User> allUsersTempList = allUsers.stream()
                .filter(user -> user.getId() != loggedUser.getId())
                .collect(Collectors.toList());

        usersList.setAll(allUsersTempList);

    }
    /* @FXML
    public void onSearchUserTextField() {
        List<User> users = service.getAllUsers();
        List<User> usersTemp = new ArrayList<>();
        for (User user : users) {
            String name = user.getName();
            if (name.startsWith(searchUserTextField.getText()) && user.getId() != loggedUser.getId())
                usersTemp.add(user);
        }
        usersList.setAll(usersTemp);
        usersTable.setItems(usersList);
    }*/
  /*
    private String email;
    @FXML
    public AnchorPane friendRequestsPane;
    @FXML
    public Button acceptFriendButton;
    @FXML
    public Button rejectFriendButton;

    @FXML
    public Button removeFriendButton;

    @FXML
    public Button addFriendshipButton;








    @FXML
    public Button acceptButton;
    @FXML
    public Button showFriendRequestsButton;












    @FXML
    public void onAcceptButton() {

        try {
            long id = friendRequestsListView.getSelectionModel().getSelectedItem().getId();
            service.addFriendship(loggedUser.getId(), id);
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No user selected!", ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }




    @FXML
    public void onAddFriendButton() {
        try {
            User userToAskFriendship = usersTable.getSelectionModel().getSelectedItem();
            service.addFriendship(loggedUser.getId(), userToAskFriendship.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sent friendrequest", ButtonType.OK);
            alert.show();

        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No user selected!", ButtonType.OK);
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }

    }






    }*/

}

