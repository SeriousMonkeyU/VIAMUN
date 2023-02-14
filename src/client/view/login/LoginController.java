package client.view.login;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.Data;
import client.view.ViewController;
import shared.transferObjects.RequestType;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;

import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.rmi.RemoteException;


public class LoginController implements ViewController
{

    private ViewHandler vh;
    private LoginViewModel vm;

    @FXML
    private Label loginErrorLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;


    String inputtedUsername;
    String inputtedPassword;

    @Override
    public void init(ViewHandler vh, ViewModelFactory vmf) throws RemoteException, MalformedURLException
    {
        this.vh = vh;
        this.vm = vmf.getLoginVM();


        vm.addListener(RequestType.SUCCESSFUL_LOGIN.toString(), this::continueLogin);

        loginErrorLabel.textProperty().bind(vm.getErrorLabelProperty());

        passwordTextField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
            {
                try
                {
                    onLoginButton();
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }


    private void continueLogin(PropertyChangeEvent event)
    {
        Platform.runLater(() ->
        {
            try
            {
                if (inputtedUsername.equals("HQ"))
                {
                    vh.openHQView("HQ");
                } else
                {
                    vh.openDelegateView(inputtedUsername);
                }

            } catch (RemoteException | MalformedURLException e)
            {
                e.printStackTrace();
            }
        });

    }

    public void onLoginButton() throws RemoteException
    {
        inputtedUsername = usernameTextField.getText();
        inputtedPassword = passwordTextField.getText();
        if (!inputtedUsername.isEmpty())
        {
            Data.username = inputtedUsername;
            Data.password = inputtedPassword;
            vm.login(inputtedUsername, inputtedPassword);
            usernameTextField.textProperty().set("");
            passwordTextField.textProperty().set("");


        }
    }


}
