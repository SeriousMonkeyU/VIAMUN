package client.view.publicChat;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.Data;
import client.view.ViewController;

import shared.transferObjects.Message;
import shared.transferObjects.RequestType;
import shared.transferObjects.Delegate;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;


import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublicChatController implements ViewController
{
    @FXML
    private ScrollPane messagesScrollPane;
    @FXML
    private ListView<String> activeUsersContainer;
    @FXML
    private VBox messagesContainer;
    @FXML
    private TextField messageInputField;


    private ViewHandler vh;
    private PublicChatViewModel vm;

    @Override
    public void init(ViewHandler vh, ViewModelFactory vmf) throws RemoteException, MalformedURLException
    {
        this.vh = vh;
        this.vm = vmf.getChatsVM();


        messageInputField.textProperty().bindBidirectional(vm.getSentMessageProperty());


        vm.addListener(RequestType.UPDATE_ACTIVE_USERS.toString(), this::updateActiveUsers);
        vm.addListener("Message", this::addReceivedPublicMessage);


        messageInputField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
            {
                try
                {
                    onSendButton();
                } catch (RemoteException | SQLException | ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        });
        vm.getActiveUsersList();
        addReceivedPublicMessages();


    }


    private void addReceivedPublicMessage(PropertyChangeEvent event)
    {
        HBox receivedMessage = (HBox) event.getNewValue();
        Platform.runLater(() ->
        {
            messagesContainer.getChildren().add(receivedMessage);

            messagesContainer.heightProperty().addListener(
                    (observable) ->
                            messagesScrollPane.setVvalue(1.0d)
            );
        });
    }

    private void addReceivedPublicMessages() throws RemoteException
    {
        ArrayList<Message> temp = vm.receivePublicMessages();
        for (int i = 0; i < temp.size(); i++)
        {
           HBox receivedMessage= vm.createMessageContainer(temp.get(i));
            Platform.runLater(() ->
            {
                messagesContainer.getChildren().add(receivedMessage);

                messagesContainer.heightProperty().addListener(
                        (observable) ->
                                messagesScrollPane.setVvalue(1.0d)
                );
            });
        }
    }

    public void updateActiveUsers(PropertyChangeEvent event)
    {
        ArrayList<Delegate> activeDelegates = (ArrayList<Delegate>) event.getNewValue();
        ArrayList<String> userNames = new ArrayList<>();
        for (Delegate i : activeDelegates)
        {
            userNames.add(i.getUsername());
        }


        ObservableList<String> users = FXCollections.observableArrayList(userNames);
        activeUsersContainer.setItems(users);


        try
        {
            activeUsersContainer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            activeUsersContainer.getSelectionModel().selectedItemProperty();
        } catch (ClassCastException e)
        {
            e.printStackTrace();
        }


    }


    public void onSendButton() throws RemoteException, SQLException, ClassNotFoundException
    {
        String imputedMessage = messageInputField.textProperty().getValue();


        if (!imputedMessage.isEmpty())
        {
            if (imputedMessage.length() >= 1000)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText("The message must be less then 1000 characters");
                errorAlert.showAndWait();
            } else
            {
                vm.sendPublicMessage();

                messageInputField.textProperty().setValue("");
            }
        } else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("The message cannot be empty");
            errorAlert.showAndWait();
        }
    }


    public void onDisconnectButton() throws RemoteException, MalformedURLException
    {
        vh.openDelegateView(Data.username);
    }


}
