package client.network;


import shared.network.ClientCallback;
import shared.network.RMIServer;
import shared.transferObjects.*;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class RMIClient implements ClientInterface, ClientCallback
{

    private RMIServer server;

    private PropertyChangeSupport support;

    public RMIClient() throws MalformedURLException
    {

        try
        {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void startClient()
    {
        try
        {
            support = new PropertyChangeSupport(this);

            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            server = (RMIServer) registry.lookup("VIAMUN");
            System.out.println("client started and connected to the server");

        } catch (RemoteException | NotBoundException e)
        {
            System.out.println("Could not connected to server. Try again later");
        }
    }

    @Override
    public void login(Delegate delegate)
    {
        try
        {

            server.login(delegate, this);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void getActiveUsersList(Delegate delegate)
    {
        try
        {
            server.getActiveUsersList(delegate, this);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPublicMessage(Message messageToSend) throws SQLException, ClassNotFoundException
    {
        try
        {

            server.sendPublicMessage(messageToSend, this);

        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void disconnect(Delegate delegate)
    {
        try
        {
            server.disconnect(delegate, this);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
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
    public void successfulLoginResult(Delegate delegate)
    {

        support.firePropertyChange(RequestType.SUCCESSFUL_LOGIN.toString(), null, delegate);
    }

    @Override
    public void blockedLoginResult(Delegate delegate)
    {
        support.firePropertyChange(RequestType.NON_EXISTENT_USERNAME.toString(), null, delegate);
    }

    @Override
    public void activeUsersListResult(ArrayList<Delegate> activeDelegates) throws RemoteException
    {
        support.firePropertyChange(RequestType.GET_ACTIVE_USERS.toString(), null, activeDelegates);
    }

    @Override
    public void receiveActiveUsers(ArrayList<Delegate> activeDelegates) throws RemoteException
    {

        support.firePropertyChange(RequestType.UPDATE_ACTIVE_USERS.toString(), null, activeDelegates);
    }


    @Override
    public void receivePublicMessage(Message messageToReceive) throws RemoteException
    {

        support.firePropertyChange("Message", null, messageToReceive);

    }

    @Override
    public ArrayList<Message> receivePublicMessages() throws RemoteException
    {
        return server.loadFromDBS();
    }




    public void sendNewsflash(Newsflash newsflash) throws RemoteException, SQLException, ClassNotFoundException
    {
        server.sendNewsflash(newsflash);
    }

    @Override
    public ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException
    {
        return server.getNewsflashes(sender);
    }


    public ArrayList<Newsflash> getApprovedNewsflashes(String sender) throws RemoteException
    {
        return server.getApprovedNewsflashes(sender);
    }


    public ArrayList<Newsflash> getPendingNewsflashes(String sender) throws RemoteException
    {
        return server.getPendingNewsflashes(sender);
    }

    public ArrayList<Newsflash> getAllNewsflashes() throws RemoteException
    {
        return server.getAllNewsflashes();
    }

    @Override
    public ArrayList<Newsflash> getAllPendingNewsflashes() throws RemoteException
    {
        return server.getAllPendingNewsflashes();
    }

    @Override
    public void ApproveNewsflash(Newsflash newsflash) throws RemoteException
    {
        server.ApproveNewsflash(newsflash);
    }

    @Override
    public void DisapproveNewsflash(Newsflash newsflash) throws RemoteException
    {
        server.DisapproveNewsflash(newsflash);
    }

    @Override
    public void RephraseNewsflash(Newsflash newsflash) throws RemoteException
    {
        server.RephraseNewsflash(newsflash);
    }


    public ArrayList<Newsflash> getAllApprovedNewsflashes() throws RemoteException
    {
        return server.getAllApprovedNewsflashes();
    }


    public ArrayList<Newsflash> getPendingNewsflashes() throws RemoteException
    {
        return server.getAllPendingNewsflashes();
    }

}
