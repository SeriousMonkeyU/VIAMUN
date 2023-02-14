package server.network;

import server.model.ServerModelInterface;
import shared.network.ClientCallback;
import shared.network.RMIServer;
import shared.transferObjects.*;

import java.beans.PropertyChangeEvent;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RMIServerImplementation implements RMIServer
{

    private Map<String, ClientCallback> clientCallbackMap;

    private ServerModelInterface serverModel;

    private boolean started = false;

    public RMIServerImplementation(ServerModelInterface serverModel) throws RemoteException, SQLException, ClassNotFoundException
    {
        int i = 0;
        ArrayList<Delegate> delegates = new ArrayList<>();

        UnicastRemoteObject.exportObject(this, 0);

        this.serverModel = serverModel;


        clientCallbackMap = new HashMap<>();


        this.serverModel.addListener(RequestType.SUCCESSFUL_LOGIN.toString(), this::loginResult);
        this.serverModel.addListener(RequestType.NON_EXISTENT_USERNAME.toString(), this::loginResult);
        this.serverModel.addListener(RequestType.GET_ACTIVE_USERS.toString(), this::sendActiveUsersList);
        this.serverModel.addListener(RequestType.UPDATE_ACTIVE_USERS.toString(), this::broadcastActiveUsers);
        this.serverModel.addListener(RequestType.RECEIVE_PUBLIC.toString(), this::broadcastPublicMessage);
    }

    public void startServer() throws RemoteException, AlreadyBoundException
    {
        Registry registry = LocateRegistry.createRegistry(1099);

        registry.bind("VIAMUN", this);
    }

    private void broadcastPublicMessage(PropertyChangeEvent event)
    {
        for (ClientCallback client : clientCallbackMap.values())
        {
            try
            {

                client.receivePublicMessage((Message) (event.getNewValue()));
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void broadcastActiveUsers(PropertyChangeEvent event)
    {
        for (ClientCallback client : clientCallbackMap.values())
        {
            try
            {
                client.receiveActiveUsers((ArrayList<Delegate>) event.getNewValue());


            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void sendActiveUsersList(PropertyChangeEvent event)
    {
        String clientToSendTo = (String) event.getOldValue();

        try
        {
            clientCallbackMap.get(clientToSendTo).activeUsersListResult((ArrayList<Delegate>) event.getNewValue());

        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    private void loginResult(PropertyChangeEvent event)
    {


        Delegate delegateToSendTo = (Delegate) ((Request) event.getNewValue()).getArg();
        Request loginResultRequest = (Request) event.getNewValue();
        try
        {
            if (loginResultRequest.getType().equals(RequestType.SUCCESSFUL_LOGIN))
                clientCallbackMap.get(delegateToSendTo.getUsername()).successfulLoginResult((Delegate) ((Request) event.getNewValue()).getArg());
            else if (loginResultRequest.getType().equals((RequestType.NON_EXISTENT_USERNAME)))
            {
                clientCallbackMap.get(delegateToSendTo.getUsername()).blockedLoginResult((Delegate) ((Request) event.getNewValue()).getArg());
            }
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void login(Delegate delegate, ClientCallback client) throws RemoteException
    {
        clientCallbackMap.put(delegate.getUsername(), client);
        serverModel.loginDelegate(delegate);
    }

    @Override
    public void getActiveUsersList(Delegate delegate, ClientCallback client) throws RemoteException
    {
        serverModel.sendCountriesToClient(delegate);
    }

    @Override
    public void sendPublicMessage(Message messageToSend, ClientCallback client) throws
            RemoteException, SQLException, ClassNotFoundException
    {

        serverModel.sendPublicMessage(messageToSend);
    }

    @Override
    public ArrayList<Message> loadFromDBS() throws RemoteException
    {
        return serverModel.loadFromDBS();
    }


    @Override
    public void disconnect(Delegate delegate, ClientCallback client) throws RemoteException
    {
        clientCallbackMap.remove(delegate.getUsername());
        serverModel.disconnect(delegate);
    }

    @Override
    public void sendNewsflash(Newsflash newsflash) throws RemoteException, SQLException, ClassNotFoundException
    {
        serverModel.sendNewsflash(newsflash);

    }

    @Override
    public ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException
    {
        return serverModel.getNewsflashes(sender);

    }

    @Override
    public ArrayList<Newsflash> getPendingNewsflashes(String sender) throws RemoteException
    {
        return serverModel.getPendingNewsflashes(sender);
    }

    @Override
    public ArrayList<Newsflash> getApprovedNewsflashes(String sender) throws RemoteException
    {
        return serverModel.getApprovedNewsflashes(sender);
    }

    @Override
    public ArrayList<Newsflash> getAllNewsflashes() throws RemoteException
    {
        return serverModel.getAllNewsflashes();
    }

    @Override
    public ArrayList<Newsflash> getAllPendingNewsflashes() throws RemoteException
    {
        return serverModel.getAllPendingNewsflashes();
    }

    @Override
    public ArrayList<Newsflash> getAllApprovedNewsflashes() throws RemoteException
    {
        return serverModel.getAllApprovedNewsflashes();
    }

    @Override
    public void ApproveNewsflash(Newsflash newsflash) throws RemoteException
    {
        serverModel.ApproveNewsflash(newsflash);
    }

    @Override
    public void DisapproveNewsflash(Newsflash newsflash) throws RemoteException
    {
        serverModel.DisapproveNewsflash(newsflash);
    }

    @Override
    public void RephraseNewsflash(Newsflash newsflash) throws RemoteException
    {
        serverModel.RephraseNewsflash(newsflash);
    }


}
