package component.beans.dataobj;

import java.io.Serializable;

public class Coordinate implements Serializable {

    public final int x;
    public final int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Coordinate)){
            return false;
        }
        Coordinate c = (Coordinate) o;
        if (x == c.x && y == c.y){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString(){
        return "[" + x + "," + y + "]";
    }

    public Coordinate cloneCoord() {
        return new Coordinate(x, y);
    }
}

