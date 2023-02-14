package client.core;

import client.network.ClientInterface;
import client.network.RMIClient;

import java.net.MalformedURLException;

public class ClientFactory
{
    private ClientInterface client;

    public ClientInterface getClient() throws MalformedURLException
    {
        if (client == null) client = new RMIClient();
        return client;
    }
}
