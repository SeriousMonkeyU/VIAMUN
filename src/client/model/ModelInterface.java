package client.model;

import shared.transferObjects.Message;
import shared.transferObjects.Newsflash;
import shared.transferObjects.Delegate;
import shared.util.Subject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ModelInterface extends Subject, Remote
{
    void login(String username, String password) throws RemoteException;

    void getActiveUsersList() throws RemoteException;

    void sendPublicMessage(String value) throws RemoteException, SQLException, ClassNotFoundException;


    ArrayList<Message> getMessages() throws RemoteException;

    void getUsername();

    void disconnect(Delegate delegate) throws RemoteException;


    Delegate getUser();

    ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getApprovedNewsflashes(String sender) throws RemoteException;

    ArrayList<Newsflash> getPendingNewsflashes(String sender) throws RemoteException;

    void sendNewsflash(Newsflash newsflash) throws RemoteException, SQLException, ClassNotFoundException;

    Newsflash createNewsflash(String sender, LocalDateTime time, String body, boolean term, boolean publicity);

    ArrayList<Newsflash> getAllNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getApprovedNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getPendingNewsflashes() throws RemoteException;

    ArrayList<Newsflash> getNewsflashesByCountry(String sender) throws RemoteException;

    ArrayList<Newsflash> getApprovedNewsflashesByCountry(String sender);

    void Approve(Newsflash newsflash) throws RemoteException;

    void Disapprove(Newsflash newsflash) throws RemoteException;

    void Rephrase(Newsflash newsflash) throws RemoteException;

}
