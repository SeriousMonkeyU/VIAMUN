package  shared.transferObjects;

import java.io.Serializable;

public class Message implements Serializable
{
    private String messageBody;
    private Delegate sender;


    public Message(String messageBody, Delegate delegate)
    {
        this.messageBody = messageBody;
        this.sender = delegate;

    }

    public void setMessageBody(String messageBody)
    {
        this.messageBody = messageBody;
    }
    public void setSender(Delegate sender)
    {
        this.sender = sender;
    }

    public String getMessageBody()
    {
        return messageBody;
    }

    public String getSenderUsername()
    {
        return sender.getUsername();
    }

    public Delegate getSender()
    {
        return sender;
    }

    @Override
    public String toString()
    {
        return sender.getUsername() + ": " + messageBody;
    }
}
