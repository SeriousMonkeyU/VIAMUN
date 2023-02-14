package  shared.transferObjects;

import javafx.beans.value.ObservableValue;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public  class Newsflash implements Serializable {
    private NewsflashState newsflashState;

    private LocalDateTime time;
    private String sender;
    private String body;
    private boolean term;
    private boolean publicity;

    public Newsflash(LocalDateTime time, String sender, String body, boolean term, boolean publicity){
        this.time = time;
        this.sender = sender;
        this.body = body;
        this.term = term;
        this.publicity = publicity;
        this.newsflashState = new NewsflashPending();
    }

    public Newsflash() {

    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public boolean getPublicity() {
        return publicity;
    }

    public boolean getTerm() {
        return term;
    }

    public String getState(){
        return newsflashState.status();
    }

    public ObservableValue<String> getStatusName(){
        return newsflashState.getStateName();
    }

    public void setState(NewsflashState newsflashState){
        this.newsflashState = newsflashState;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Newsflash newsflash = (Newsflash) o;
        return term == ((Newsflash) o).term && body.equals(((Newsflash) o).body) && publicity == ((Newsflash) o).publicity;
    }


}
