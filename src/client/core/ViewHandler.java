package client.core;

import client.model.Data;
import client.view.HQ.HQMainViewController;
import client.view.delegate.DelegateMainViewController;
import client.view.publicChat.PublicChatController;
import client.view.login.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ViewHandler
{
    private ViewModelFactory vmf;

    private Stage primaryStage;
    private Scene loginScene;
    private Scene publicChatScene;
    private Scene delegateScene;


    public ViewHandler(ViewModelFactory vmf, Stage primaryStage)
    {
        this.vmf = vmf;
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
    }

    public void start() throws RemoteException, MalformedURLException
    {
        openLoginView();
        primaryStage.show();

    }

    public void openLoginView() throws RemoteException, MalformedURLException
    {
        FXMLLoader loader = new FXMLLoader();
        if (loginScene == null)
        {
            Parent root = getRootByPath("../view/login/LoginView.fxml", loader);
            LoginController controller = loader.getController();
            controller.init(this, vmf);
            loginScene = new Scene(root);
        }
        loginScene.getStylesheets().add(getClass().getResource("../../shared/stylesheets/styles.css").toExternalForm());
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
    }

    public void openPublicChatView() throws RemoteException, MalformedURLException
    {
        FXMLLoader loader = new FXMLLoader();

        if (publicChatScene == null)
        {
            Parent root = getRootByPath("../view/publicChat/PublicChatView.fxml", loader);
            PublicChatController controller = loader.getController();
            controller.init(this, vmf);
            publicChatScene = new Scene(root);

        }
        publicChatScene.getStylesheets().add(getClass().getResource("../../shared/stylesheets/styles.css").toExternalForm());
        primaryStage.setTitle(Data.username);
        primaryStage.setScene(publicChatScene);
    }


    public void openDelegateView(String t1) throws RemoteException, MalformedURLException

    {
        FXMLLoader loader = new FXMLLoader();

        if (delegateScene == null)
        {
            Parent root = getRootByPath("../view/delegate/DelegateMain.fxml", loader);
            DelegateMainViewController controller = loader.getController();
            controller.init(this, vmf);
            delegateScene = new Scene(root);
        }
        delegateScene.getStylesheets().add(getClass().getResource("../../shared/stylesheets/styles.css").toExternalForm());
        primaryStage.setTitle(t1);
        primaryStage.setScene(delegateScene);

    }

    public void openHQView(String t1) throws RemoteException, MalformedURLException

    {
        FXMLLoader loader = new FXMLLoader();

        if (publicChatScene == null)
        {
            Parent root = getRootByPath("../view/HQ/HQMain.fxml", loader);
            HQMainViewController controller = loader.getController();
            controller.init(this, vmf);
            publicChatScene = new Scene(root);
        }
        publicChatScene.getStylesheets().add(getClass().getResource("../../shared/stylesheets/styles.css").toExternalForm());
        primaryStage.setTitle("HQ");
        primaryStage.setScene(publicChatScene);

    }

    private Parent getRootByPath(String path, FXMLLoader loader)
    {
        loader.setLocation(getClass().getResource(path));
        Parent root = null;
        try
        {
            root = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return root;
    }


}
