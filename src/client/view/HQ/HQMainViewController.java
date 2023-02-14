package client.view.HQ;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import shared.transferObjects.Newsflash;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class HQMainViewController
{

    private ViewHandler viewHandler;
    private ViewModelFactory viewModelFactory;
    private HQViewModel viewModel;
    private Region root;


    @FXML
    private TextArea newsflashArea;
    @FXML
    private TableView<Newsflash> newsflashTable;
    @FXML
    private TableColumn<Newsflash, String> timeColumn;
    @FXML
    private TableColumn<Newsflash, String> bodyColumn;
    @FXML
    private TableColumn<Newsflash, String> termColumn;
    @FXML
    private TableColumn<Newsflash, String> publicityColumn;

    @FXML
    private ComboBox comboBox;
    @FXML
    Label newsflashesLeft;


    public HQMainViewController()
    {


    }

    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) throws MalformedURLException, RemoteException
    {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.viewModel = viewModelFactory.getHqViewModel();


        comboBox.getItems().add("Approve");
        comboBox.getItems().add("Disapprove");
        comboBox.getItems().add("Rephrase");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        bodyColumn.setCellValueFactory(new PropertyValueFactory<>("body"));
        termColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        publicityColumn.setCellValueFactory(new PropertyValueFactory<>("publicity"));


    }

    public Region getRoot()
    {
        return root;
    }


    public void refresh() throws RemoteException
    {
        newsflashTable.getItems().clear();
        for (int i = 0; i < viewModel.getAllPendingNewsflashes().size(); i++)
        {
            newsflashTable.getItems().add(viewModel.getAllPendingNewsflashes().get(i));
        }
        newsflashesLeft.setText(String.valueOf(viewModel.getAllPendingNewsflashes().size()));

    }

    public void onLogOut() throws MalformedURLException, RemoteException
    {
        viewHandler.openLoginView();
    }

    public void changeStatus() throws RemoteException
    {
        Newsflash newsflash = newsflashTable.getSelectionModel().getSelectedItem();


        if (comboBox.getValue().equals("Approve"))
        {
            if (newsflash != null)
            {
                viewModel.Approve(newsflash);

            } else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Select a newsflash from the list!");
            }
        } else if (comboBox.getValue().equals("Disapprove"))
        {
            if (newsflash != null)
            {
                viewModel.Disapprove(newsflash);
            } else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Select a newsflash from the list!");
            }
        } else if (comboBox.getValue().equals("Rephrase"))
        {
            if (newsflash != null)
            {
                viewModel.Rephrase(newsflash);
            } else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Select a newsflash from the list!");
            }
        } else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Select an action from the list!");
        }
        refresh();
    }


}
