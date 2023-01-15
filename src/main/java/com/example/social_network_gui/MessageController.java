package com.example.social_network_gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import social_network.Observer.Observer;
import social_network.domain.Message;
import social_network.domain.User;
import social_network.domain.UserDTOFriend;
import social_network.service.AppService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MessageController implements Observer {

    public TextField typeMessage;
    private String name;

    private ObservableList<String> friendList = FXCollections.observableArrayList();

    private ObservableList<Message> msjList = FXCollections.observableArrayList();

    private AppService service;

    private User loggedUser = null;
    @FXML
    public Button backButton;
    @FXML
    public Button sendButton;
    @FXML
    public TableColumn<Message, String> MessageColumn;
    @FXML
    public TableColumn<Message, String> nameColumn;
    @FXML
    public TableColumn<Message, String> dataColumn;

    @FXML
    public ListView<String> friendsListView;
    @FXML
    public TableView<Message> messages;

    private int index;
    private int number;
    private static String name_index;



    public void setService(AppService service, User user) {
        this.service = service;
        this.service.addObserver(this);
        this.loggedUser = user;
        this.index=0;
        initLists(index);
    }

    private void initLists(int index) {
        HashMap<User, String> friendsOfUser = service.getFriends(loggedUser.getId());
        List<String> friends = new ArrayList<>();
        for (User user : friendsOfUser.keySet()) {
            friends.add(user.getName());
        }

        friendList.setAll(friends);

        name_index=service.findUserByName(friends.get(index)).getName();
        List<Message> allmessages = service.getMessage(loggedUser.getName(),name_index);
        msjList.setAll(allmessages);
        messages.setItems(msjList);

    }


    @FXML
    public void initialize() {
        MessageColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("message"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("from"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("time"));

        friendsListView.setItems(friendList);
        friendsListView.getSelectionModel().select(index);
        messages.setItems(msjList);

        friendsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                index= friendsListView.getSelectionModel().getSelectedIndex();
                initLists(index);

        });
    }

    @Override
    public void update() {
        initLists(index);
    }
    public void back_main() throws IOException {
        Main main = new Main();
        main.switchToMain("main_.fxml", loggedUser.getEmail());
    }
    public void send_message()  {
        String message = typeMessage.getText();
        int number=index;
        service.addMessage(message,loggedUser.getName(),name_index);
        initLists(number);
        index=number;
        typeMessage.setText("");

    }



    //long id = friendRequestsListView.getSelectionModel().getSelectedItem().getId();
}
