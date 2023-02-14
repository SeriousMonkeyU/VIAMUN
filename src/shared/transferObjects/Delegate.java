package  shared.transferObjects;

import java.io.Serializable;

public class Delegate implements Serializable
{
    private String username;
    private String password;

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Delegate(String username, String password)
    {
        this.setUsername(username);
        this.setPassword(password);
    }



    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String toString()
    {
        return username + " " + password;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof Delegate))
        {
            return false;
        }
        Delegate temp = (Delegate) obj;
        return this.password.equals(temp.password) && this.username.equals(temp.username);
    }
}
