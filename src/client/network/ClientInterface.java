package client.network;

import shared.transferObjects.Message;
import shared.transferObjects.Newsflash;
import shared.transferObjects.Delegate;
import shared.util.Subject;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientInterface extends Subject
{
    void startClient() throws RemoteException;

    void login(Delegate delegate) throws RemoteException;

    void getActiveUsersList(Delegate delegate) throws RemoteException;

    void sendPublicMessage(Message messageToSend) throws RemoteException, SQLException, ClassNotFoundException;

    void disconnect(Delegate delegate) throws RemoteException;

    void receivePublicMessage(Message messageToReceive) throws RemoteException;

    ArrayList<Message> receivePublicMessages() throws RemoteException;

    void sendNewsflash(Newsflash newsflash) throws RemoteException, SQLException, ClassNotFoundException;

    ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getAllNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getAllPendingNewsflashes() throws RemoteException;

    void ApproveNewsflash(Newsflash newsflash) throws RemoteException;

    void DisapproveNewsflash(Newsflash newsflash) throws RemoteException;

    void RephraseNewsflash(Newsflash newsflash) throws RemoteException;

}
