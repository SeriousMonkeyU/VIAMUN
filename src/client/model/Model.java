package client.model;

import client.network.ClientInterface;
import shared.transferObjects.Message;
import shared.transferObjects.Newsflash;
import shared.transferObjects.RequestType;
import shared.transferObjects.Delegate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Model implements Serializable, ModelInterface
{
    private ClientInterface client;
    private PropertyChangeSupport support;


    private Delegate delegate;


    public Model(ClientInterface client) throws RemoteException
    {
        support = new PropertyChangeSupport(this);

        this.client = client;
        client.startClient();

        client.addListener(RequestType.SUCCESSFUL_LOGIN.toString(), this::firePropertyForward);
        client.addListener(RequestType.NON_EXISTENT_USERNAME.toString(), this::firePropertyForward);
        client.addListener(RequestType.UPDATE_ACTIVE_USERS.toString(), this::firePropertyForward);
        client.addListener("Message", this::firePropertyForward);

        delegate = new Delegate("", "");


    }

    private void firePropertyForward(PropertyChangeEvent propertyChangeEvent)
    {
        if (propertyChangeEvent.getPropertyName().equals(RequestType.SUCCESSFUL_LOGIN.toString()))
        {
            Delegate delegate = (Delegate) propertyChangeEvent.getNewValue();
            this.delegate.setUsername(delegate.getUsername());
            this.delegate.setPassword(delegate.getPassword());

        }

        support.firePropertyChange(propertyChangeEvent);

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

    @Override
    public void login(String username, String password) throws RemoteException
    {
        delegate.setUsername(username);
        delegate.setPassword(password);
        client.login(delegate);

    }

    @Override
    public void getActiveUsersList() throws RemoteException
    {
        client.getActiveUsersList(delegate);
    }

    @Override
    public ArrayList<Message> getMessages() throws RemoteException
    {
      return   client.receivePublicMessages();

    }

    @Override
    public void sendPublicMessage(String value) throws RemoteException, SQLException, ClassNotFoundException
    {

        Message messageToSend = new Message(value, delegate);
        client.sendPublicMessage(messageToSend);
    }


    @Override
    public void getUsername()
    {
        support.firePropertyChange("USERNAME", null, delegate.getUsername());
    }


    @Override
    public void disconnect(Delegate delegate) throws RemoteException
    {
        client.disconnect(delegate);
    }

    public Delegate getUser()
    {

        return delegate;
    }

    @Override
    public ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException
    {
        return client.getNewsflashes(sender);
    }

    @Override
    public ArrayList<Newsflash> getApprovedNewsflashes(String sender)
    {
        return null;
    }

    @Override
    public ArrayList<Newsflash> getPendingNewsflashes(String sender)
    {
        return null;
    }

    @Override
    public void sendNewsflash(Newsflash newsflash) throws RemoteException, SQLException, ClassNotFoundException
    {
        client.sendNewsflash(newsflash);
    }

    @Override
    public Newsflash createNewsflash(String sender, LocalDateTime time, String body, boolean term, boolean publicity)
    {
        return null;
    }

    @Override
    public ArrayList<Newsflash> getAllNewsflashes() throws RemoteException
    {
        return client.getAllNewsflashes();
    }

    @Override
    public ArrayList<Newsflash> getApprovedNewsflashes()
    {
        return null;
    }

    @Override
    public ArrayList<Newsflash> getPendingNewsflashes() throws RemoteException
    {
        return client.getAllPendingNewsflashes();
    }

    @Override
    public ArrayList<Newsflash> getNewsflashesByCountry(String sender)
    {
        return null;
    }

    @Override
    public ArrayList<Newsflash> getApprovedNewsflashesByCountry(String sender)
    {
        return null;
    }

    @Override
    public void Approve(Newsflash newsflash) throws RemoteException
    {
        client.ApproveNewsflash(newsflash);
    }

    @Override
    public void Disapprove(Newsflash newsflash) throws RemoteException
    {
        client.DisapproveNewsflash(newsflash);
    }

    @Override
    public void Rephrase(Newsflash newsflash) throws RemoteException
    {
        client.RephraseNewsflash(newsflash);
    }


}
