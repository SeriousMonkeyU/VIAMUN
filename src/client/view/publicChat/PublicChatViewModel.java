package client.view.publicChat;

import client.model.Data;
import client.model.ModelInterface;
import shared.transferObjects.Message;
import shared.transferObjects.RequestType;
import shared.transferObjects.Delegate;
import shared.util.Subject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublicChatViewModel implements Subject
{
    private ModelInterface model;

    private StringProperty sentMessageProperty;

    ArrayList<Label> activeUsersLabels;
    private String username;
    private String lastSender;

    private PropertyChangeSupport support;

    public PublicChatViewModel(ModelInterface model)
    {
        support = new PropertyChangeSupport(this);

        this.model = model;

        sentMessageProperty = new SimpleStringProperty("");
        lastSender = "";

        model.addListener(RequestType.UPDATE_ACTIVE_USERS.toString(), this::updateActiveUsers);
        model.addListener("Message", this::receivePublicMessage);
        username = Data.username;

    }

    private void receivePublicMessage(PropertyChangeEvent event)
    {
        Message receivedMessage = (Message) event.getNewValue();
        support.firePropertyChange("Message", null, createMessageContainer(receivedMessage));

    }

    public ArrayList<Message> receivePublicMessages() throws RemoteException
    {
        return model.getMessages();

    }



    private void updateActiveUsers(PropertyChangeEvent event)
    {
        ArrayList<Delegate> receivedDelegateList = (ArrayList<Delegate>) event.getNewValue();

        activeUsersLabels = new ArrayList<>();

        support.firePropertyChange(RequestType.UPDATE_ACTIVE_USERS.toString(), null, receivedDelegateList);

    }

    public HBox createMessageContainer(Message message)
    {
        Delegate sender = message.getSender();


        HBox hBoxContainer = new HBox();
        hBoxContainer.getStyleClass().add("hBoxMessageContainer");
        VBox vBoxContainer = new VBox();
        vBoxContainer.getStyleClass().add("vBoxMessageContainer");
        HBox avatarPlusUsernameHBox = new HBox();

        if (message.getSenderUsername().equals(username))
        {
            hBoxContainer.getStyleClass().add("right");
            vBoxContainer.getStyleClass().add("right");
            avatarPlusUsernameHBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }

        if (!message.getSenderUsername().equals(lastSender))
        {
            Label usernameLabel = new Label(message.getSenderUsername());
            usernameLabel.getStyleClass().add("messageUsernameLabel");

        }

        Label messageBodyLabel = new Label(message.getMessageBody());
        messageBodyLabel.getStyleClass().add("messageBodyLabel");
        messageBodyLabel.setWrapText(true);
        messageBodyLabel.setMaxWidth(300);
        vBoxContainer.getChildren().add(messageBodyLabel);

        hBoxContainer.getChildren().add(vBoxContainer);

        lastSender = message.getSenderUsername();

        return hBoxContainer;
    }

    public StringProperty getSentMessageProperty()
    {
        return sentMessageProperty;
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(eventName, listener);
    }

    public void getActiveUsersList() throws RemoteException
    {
        model.getActiveUsersList();
    }

    public void sendPublicMessage() throws RemoteException, SQLException, ClassNotFoundException
    {
        model.sendPublicMessage(sentMessageProperty.getValue());

    }


}
