package component.beans;

import java.awt.*;
import java.io.Serializable;

public class TestBean implements Serializable {

    private String name;
    private int number;

    public TestBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int value) {
        this.number = value;
    }
}
