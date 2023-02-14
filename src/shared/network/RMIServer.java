package shared.network;

import shared.transferObjects.Message;
import shared.transferObjects.Newsflash;
import shared.transferObjects.Delegate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RMIServer extends Remote
{
    void login(Delegate delegate, ClientCallback client) throws RemoteException;

    void getActiveUsersList(Delegate delegate, ClientCallback client) throws RemoteException;

    void sendPublicMessage(Message message, ClientCallback client) throws RemoteException, SQLException, ClassNotFoundException;

    ArrayList<Message> loadFromDBS() throws RemoteException;

    void disconnect(Delegate delegate, ClientCallback client) throws RemoteException;

    void sendNewsflash(Newsflash newsflash) throws RemoteException, SQLException, ClassNotFoundException;

    ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getPendingNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getApprovedNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getAllNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getAllPendingNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getAllApprovedNewsflashes() throws RemoteException;

    void ApproveNewsflash(Newsflash newsflash) throws RemoteException;

    void DisapproveNewsflash(Newsflash newsflash) throws RemoteException;

    void RephraseNewsflash(Newsflash newsflash) throws RemoteException;


}
