package  client.view;

import  client.core.ViewHandler;
import  client.core.ViewModelFactory;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public interface ViewController
{
  public void init(ViewHandler vh, ViewModelFactory vmf) throws RemoteException, MalformedURLException;
}
