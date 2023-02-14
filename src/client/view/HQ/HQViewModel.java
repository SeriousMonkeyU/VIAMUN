package client.view.HQ;

import client.model.ModelInterface;
import shared.transferObjects.Newsflash;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class HQViewModel
{
    private ModelInterface model;
    private ObservableList<Newsflash> list;

    public HQViewModel(ModelInterface model)
    {
        this.model = model;
        list = FXCollections.observableArrayList();

    }

    public ArrayList<Newsflash> getAllPendingNewsflashes() throws RemoteException
    {
        return model.getPendingNewsflashes();
    }

    public ArrayList<Newsflash> getAllNewsflashes() throws RemoteException
    {
        return model.getAllNewsflashes();
    }

    public void onLogOut()
    {

    }

    public void Approve(Newsflash newsflash) throws RemoteException
    {
        model.Approve(newsflash);
    }

    public void Disapprove(Newsflash newsflash) throws RemoteException
    {
        model.Disapprove(newsflash);
    }

    public void Rephrase(Newsflash newsflash) throws RemoteException
    {
        model.Rephrase(newsflash);
    }
}
