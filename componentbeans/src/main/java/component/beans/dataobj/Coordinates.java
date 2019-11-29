package component.beans.dataobj;

import java.io.Serializable;
import java.util.List;

public class Coordinates implements Serializable {
    private List<Coordinate> coordinates;
    private ImgDTO imgDTO;

    public Coordinates(List<Coordinate> coordinates, ImgDTO imgDTO) {
        this.coordinates = coordinates;
        this.imgDTO = imgDTO;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public ImgDTO getImgDTO() {
        return imgDTO;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
