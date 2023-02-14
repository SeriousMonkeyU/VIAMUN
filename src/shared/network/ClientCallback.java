package shared.network;

import shared.transferObjects.Message;
import shared.transferObjects.Delegate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientCallback extends Remote
{
    void successfulLoginResult(Delegate delegate) throws RemoteException;

    void blockedLoginResult(Delegate delegate) throws RemoteException;

    void activeUsersListResult(ArrayList<Delegate> activeDelegates) throws RemoteException;

    void receiveActiveUsers(ArrayList<Delegate> activeDelegates) throws RemoteException;

    void receivePublicMessage(Message messageToReceive) throws RemoteException;

}
