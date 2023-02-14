package client.core;

import client.model.*;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ModelFactory
{
  private ClientFactory cf;
  Model model;


  public ModelFactory(ClientFactory cf)
  {
    this.cf = cf;
  }

  public Model getModel() throws RemoteException, MalformedURLException
  {
    if (model == null) model = new Model(cf.getClient());
    return model;
  }
  public ModelInterface getModelInterface() throws RemoteException, MalformedURLException
  {
    if (model == null) model = new Model(cf.getClient());
    return model;
  }



}
