package  shared.transferObjects;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class NewsflashPending extends NewsflashState implements Serializable {
    @Override
    public String status() {
        return "Pending";
    }

    public void Approve(Newsflash newsflash) {
        newsflash.setState(new NewsflashApproved());
    }

    public void Disapprove(Newsflash newsflash) {
        newsflash.setState(new NewsflashDisapproved());
    }

    public void Rephrase(Newsflash newsflash) {
        newsflash.setState(new NewsflashRephrase());
    }

    public ObservableValue<String> getStateName() {
        return new ObservableValue<String>() {
            @Override
            public void addListener(ChangeListener<? super String> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> changeListener) {

            }

            @Override
            public String getValue() {
                return status();
            }

            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }
        };
    }
}
