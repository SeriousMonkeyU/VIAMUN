package client.core;

import client.view.HQ.HQViewModel;
import client.view.delegate.DelegateMainViewModel;
import client.view.publicChat.PublicChatViewModel;
import client.view.login.LoginViewModel;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ViewModelFactory
{
    private ModelFactory mf;
    private LoginViewModel loginVM;
    private PublicChatViewModel chatsVM;
    private DelegateMainViewModel delegateMainViewModel;
    private HQViewModel hqViewModel;

    public ViewModelFactory(ModelFactory mf)
    {
        this.mf = mf;
    }

    public LoginViewModel getLoginVM() throws RemoteException, MalformedURLException
    {
        if (loginVM == null) loginVM = new LoginViewModel(mf.getModel());
        return loginVM;
    }

    public PublicChatViewModel getChatsVM() throws RemoteException, MalformedURLException
    {
        if (chatsVM == null) chatsVM = new PublicChatViewModel(mf.getModel());
        return chatsVM;
    }

    public DelegateMainViewModel getDelegateMainViewModel() throws MalformedURLException, RemoteException
    {
        if (delegateMainViewModel == null) delegateMainViewModel = new DelegateMainViewModel(mf.getModel());
        return delegateMainViewModel;
    }

    public HQViewModel getHqViewModel() throws MalformedURLException, RemoteException
    {
        if (hqViewModel == null) hqViewModel = new HQViewModel(mf.getModel());
        return hqViewModel;
    }

}
