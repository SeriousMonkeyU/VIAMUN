package  server;

import  server.model.ServerModel;
import  server.model.ServerModelInterface;
import  server.network.RMIServerImplementation;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class RunServer {
    public static void main(String[] args) throws RemoteException,
            AlreadyBoundException, SQLException, ClassNotFoundException
    {
        ServerModelInterface serverModel = new ServerModel();
        RMIServerImplementation server = new RMIServerImplementation(serverModel);
        System.out.println("Server started");
        server.startServer();
    }
}
