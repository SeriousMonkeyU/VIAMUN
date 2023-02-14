package client.view.delegate;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.Data;
import shared.transferObjects.Newsflash;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import shared.transferObjects.Delegate;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DelegateMainViewController
{
    private ViewHandler viewHandler;
    private DelegateMainViewModel viewModel;
    private Region root;
    private String user;


    @FXML
    private TextArea newsflashArea;
    @FXML
    private RadioButton isLong;
    @FXML
    private RadioButton isSecret;
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
    private TableColumn<Newsflash, String> approvedColumn;


    public DelegateMainViewController()
    {


    }

    public void init(ViewHandler vh, ViewModelFactory vmf) throws RemoteException, MalformedURLException
    {
        this.viewHandler = vh;
        this.viewModel = vmf.getDelegateMainViewModel();
        this.user = Data.username;


        newsflashArea.setWrapText(true);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        bodyColumn.setCellValueFactory(new PropertyValueFactory<>("body"));
        termColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
        publicityColumn.setCellValueFactory(new PropertyValueFactory<>("publicity"));
        approvedColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusName());


    }


    public Region getRoot()
    {
        return root;
    }

    @FXML
    public void sendNewsflash() throws RemoteException, SQLException, ClassNotFoundException
    {
        if (newsflashArea.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Warning!");
            alert.setContentText("Body must not be empty!");
            alert.showAndWait();
        } else
        {

            try
            {

                Newsflash newsflash = new Newsflash(LocalDateTime.now(), user, newsflashArea.getText(), isLong.isSelected(), isSecret.isSelected());
                viewModel.sendNewsflash(newsflash);
                newsflashTable.getItems().add(newsflash);

            } catch (RemoteException e)
            {
                e.printStackTrace();
            }
            try
            {
                clearInput();
            } catch (RemoteException e)
            {
                e.printStackTrace();
            }


        }


    }

    public void clearInput() throws RemoteException
    {
        newsflashArea.setText("");
        isLong.setSelected(false);
        isSecret.setSelected(false);

    }

    public void onLogOut() throws RemoteException, MalformedURLException
    {
        Delegate delegate1 = new Delegate(Data.username, Data.password);
        viewModel.disconnect(delegate1);
        viewHandler.openLoginView();
    }

    @FXML
    public void goChat() throws MalformedURLException, RemoteException
    {
        viewHandler.openPublicChatView();
    }

    public void refresh() throws RemoteException
    {

        newsflashTable.getItems().clear();
        newsflashTable.getItems().addAll(viewModel.getList());

    }


}
