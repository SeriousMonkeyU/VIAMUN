package server.model;

import shared.transferObjects.Message;
import shared.transferObjects.Delegate;
import shared.transferObjects.Newsflash;
import shared.util.Subject;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ServerModelInterface extends Subject
{
    void loginDelegate(Delegate delegate);

    void sendCountriesToClient(Delegate delegate);

    void sendPublicMessage(Message message) throws ClassNotFoundException, SQLException;

    ArrayList<Message>loadFromDBS();

    void sendNewsflash(Newsflash newsflash) throws RemoteException, ClassNotFoundException, SQLException;

    ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getPendingNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getApprovedNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getAllNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getAllPendingNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getAllApprovedNewsflashes() throws RemoteException;

    void ApproveNewsflash(Newsflash newsflash) throws RemoteException;

    void DisapproveNewsflash(Newsflash newsflash) throws RemoteException;

    void RephraseNewsflash(Newsflash newsflash) throws RemoteException;

    void disconnect(Delegate delegateDisconnecting);
}
