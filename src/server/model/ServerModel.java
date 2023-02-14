package server.model;

import shared.transferObjects.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import java.rmi.RemoteException;
import java.sql.*;

import java.util.ArrayList;


public class ServerModel implements Serializable, ServerModelInterface
{

    private PropertyChangeSupport support;
    private final NewsflashList list = new NewsflashList();
    private ArrayList<Delegate> delegates;
    private ArrayList<Message> messages;
    int o = 0;

    public ServerModel() throws ClassNotFoundException, SQLException
    {
        support = new PropertyChangeSupport(this);
        messages = new ArrayList<>();

        delegates = new ArrayList<>();

        Class.forName("org.postgresql.Driver");
        DriverManager.registerDriver(new org.postgresql.Driver());
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
        PreparedStatement selectStatement = connection.prepareStatement("SELECT name, password FROM sep_2.\"Delegate\"");
        ResultSet rs = selectStatement.executeQuery();
        while (rs.next())
        {

            String name = rs.getString("name");
            String password = rs.getString("password");
            Delegate temp = new Delegate(name, password);
            delegates.add(temp);


        }
        PreparedStatement selectStatement1 = connection.prepareStatement("SELECT sender,body FROM sep_2.Messages");
        rs = selectStatement1.executeQuery();
        while (rs.next())
        {
            String sender = rs.getString("sender");
            String body = rs.getString("body");
            for (int j = 0; j < delegates.size(); j++)
            {
                if (delegates.get(j).getUsername().equals(sender))
                {
                    Message temp1 = new Message(body, delegates.get(j));
                    messages.add(temp1);
                }
            }


        }
        connection.close();
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(eventName, listener);
    }

    @Override
    public void loginDelegate(Delegate delegate)
    {
        Boolean existingUser = false;
        for (Delegate i : delegates)
        {
            if (i.equals(delegate))
                existingUser = true;
        }

        if (existingUser)
        {

            System.out.println(delegate.getUsername() + " logged in successfully");
            Request loginResultRequest = new Request(RequestType.SUCCESSFUL_LOGIN, delegate);
            support.firePropertyChange(RequestType.SUCCESSFUL_LOGIN.toString(), null, loginResultRequest);

        } else
        {
            System.out.println("login attempt blocked ( " + delegate.getUsername() + " " + delegate.getPassword() + " )");

            Request loginResultRequest = new Request(RequestType.NON_EXISTENT_USERNAME, delegate);
            support.firePropertyChange(RequestType.NON_EXISTENT_USERNAME.toString(), null, loginResultRequest);
        }
    }

    @Override
    public void sendCountriesToClient(Delegate delegate)
    {
        ArrayList<Delegate> countries = new ArrayList<>();
        Delegate temp = new Delegate("HQ", "HQ");
        for (int i = 0; i < delegates.size(); i++)
        {
            if (!delegates.get(i).equals(delegate) && !delegates.get(i).equals(temp))
                countries.add(delegates.get(i));
        }


        Request updateActiveUsersRequest = new Request(RequestType.UPDATE_ACTIVE_USERS, countries);
        support.firePropertyChange(updateActiveUsersRequest.getType().toString(), delegate.getUsername(), countries);


    }


    @Override
    public void sendPublicMessage(Message messageToReceive) throws ClassNotFoundException, SQLException
    {

        support.firePropertyChange(RequestType.RECEIVE_PUBLIC.toString(), null, messageToReceive);
        Class.forName("org.postgresql.Driver");
        DriverManager.registerDriver(new org.postgresql.Driver());
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO sep_2.Messages VALUES (?,?)");
        insertStatement.setString(1, messageToReceive.getSenderUsername());
        insertStatement.setString(2, messageToReceive.getMessageBody());
        insertStatement.executeUpdate();
        connection.close();


    }


    @Override
    public ArrayList<Message> loadFromDBS()
    {
        return messages;
    }

    @Override
    public void sendNewsflash(Newsflash newsflash) throws RemoteException, ClassNotFoundException, SQLException
    {
        o++;
        Class.forName("org.postgresql.Driver");
        DriverManager.registerDriver(new org.postgresql.Driver());
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO sep_2.Newsflashes VALUES (?,?,?,?,?,?)");
        insertStatement.setInt(1, o);
        insertStatement.setString(2, newsflash.getSender());
        insertStatement.setString(3, newsflash.getBody());
        insertStatement.setTimestamp(4, Timestamp.valueOf(newsflash.getTime()));
        insertStatement.setString(5, String.valueOf(newsflash.getTerm()));
        insertStatement.setString(6, String.valueOf(newsflash.getPublicity()));
        insertStatement.executeUpdate();
        connection.close();

        list.addNewsflash(newsflash);

    }

    @Override
    public ArrayList<Newsflash> getNewsflashes(String sender) throws RemoteException
    {
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).getSender().equals(sender))
            {
                returnable.add(list.getList().get(i));
            }
        }
        return returnable;

    }

    @Override
    public ArrayList<Newsflash> getPendingNewsflashes(String sender) throws RemoteException
    {
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).getSender().equals(sender) && list.getList().get(i).getState().equals("Pending"))
            {
                returnable.add(list.getList().get(i));
            }
        }
        return returnable;
    }

    @Override
    public ArrayList<Newsflash> getApprovedNewsflashes(String sender) throws RemoteException
    {
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).getSender().equals(sender) && list.getList().get(i).getState().equals("Approved"))
            {
                returnable.add(list.getList().get(i));
            }
        }
        return returnable;
    }

    @Override
    public ArrayList<Newsflash> getAllNewsflashes() throws RemoteException
    {
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++)
        {

            returnable.add(list.getList().get(i));

        }
        return returnable;
    }

    @Override
    public ArrayList<Newsflash> getAllPendingNewsflashes() throws RemoteException
    {
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).getState().equals("Pending"))
            {
                returnable.add(list.getList().get(i));
            }
        }
        return returnable;
    }

    @Override
    public ArrayList<Newsflash> getAllApprovedNewsflashes() throws RemoteException
    {
        ArrayList<Newsflash> returnable = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).getState().equals("Approved"))
            {
                returnable.add(list.getList().get(i));
            }
        }
        return returnable;
    }

    @Override
    public void ApproveNewsflash(Newsflash newsflash) throws RemoteException
    {
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).equals(newsflash))
            {
                list.getList().get(i).setState(new NewsflashApproved());
            }
        }
    }

    @Override
    public void DisapproveNewsflash(Newsflash newsflash) throws RemoteException
    {
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).equals(newsflash))
            {
                list.getList().get(i).setState(new NewsflashDisapproved());
            }
        }
    }

    @Override
    public void RephraseNewsflash(Newsflash newsflash) throws RemoteException
    {
        System.out.println(newsflash.getBody() + "Rephrase");
        for (int i = 0; i < list.getSize(); i++)
        {
            if (list.getList().get(i).equals(newsflash))
            {
                System.out.println(newsflash.getBody() + "Rephrased");
                list.getList().get(i).setState(new NewsflashRephrase());
            }
        }
    }

    @Override
    public void disconnect(Delegate delegate)
    {


    }


}
