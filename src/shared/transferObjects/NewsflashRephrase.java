package  shared.transferObjects;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

public class NewsflashRephrase extends NewsflashState implements Serializable {
    @Override public String status(){
        return "Please rephrase yourself!";
    }
    public ObservableValue<String> getStateName(){
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
