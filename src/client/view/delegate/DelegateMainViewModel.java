package  client.view.delegate;

import  client.model.Data;
import  client.model.ModelInterface;
import  shared.transferObjects.Newsflash;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.transferObjects.Delegate;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class DelegateMainViewModel
{
    private ModelInterface model;
    private String country;
    private ObservableList<Newsflash> list;

    public DelegateMainViewModel(ModelInterface model) throws RemoteException
    {

        this.model = model;
        this.country = Data.username;


        list = FXCollections.observableArrayList();
        list.addAll(model.getNewsflashes(country));

    }


    public ObservableList<Newsflash> getList() throws RemoteException
    {
        list.clear();
        list.addAll(model.getNewsflashes(country));
        return list;
    }

    public String getCountry()
    {
        return country;
    }

    public void addNewsflash(Newsflash newsflash) throws RemoteException
    {
        list.add(newsflash);

    }

    public void loadFromModel(Newsflash newsflash) throws RemoteException
    {


        list.add(newsflash);


    }
public void disconnect(Delegate delegate) throws RemoteException
{
    model.disconnect(delegate);
}

    public synchronized void sendNewsflash(Newsflash newsflash) throws RemoteException, SQLException, ClassNotFoundException
    {



        try
        {
            model.sendNewsflash(newsflash);
            loadFromModel(newsflash);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }


    }
}
