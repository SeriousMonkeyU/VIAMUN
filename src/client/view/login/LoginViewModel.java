package client.view.login;

import client.model.ModelInterface;
import shared.transferObjects.RequestType;
import shared.util.Subject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;

public class LoginViewModel implements Subject
{
    private ModelInterface model;

    private PropertyChangeSupport support;

    private StringProperty errorLabelProperty;
    private StringProperty usernameProperty;


    public LoginViewModel(ModelInterface model)
    {
        support = new PropertyChangeSupport(this);

        this.model = model;

        errorLabelProperty = new SimpleStringProperty("");
        usernameProperty = new SimpleStringProperty();

        model.addListener(RequestType.SUCCESSFUL_LOGIN.toString(), this::firePropertyForward);
        model.addListener(RequestType.NON_EXISTENT_USERNAME.toString(), this::updateErrorLabel);


    }


    private void updateErrorLabel(PropertyChangeEvent event)
    {
        Platform.runLater(() ->
                {
                    errorLabelProperty.setValue("Invalid Credentials. Try Again!");
                }
        );
    }

    public void login(String username, String password) throws RemoteException
    {
        model.login(username, password);
    }

    private void firePropertyForward(PropertyChangeEvent event)
    {
        support.firePropertyChange(event);
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabelProperty;
    }

    public StringProperty getUsernameProperty()
    {
        return usernameProperty;
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


}
