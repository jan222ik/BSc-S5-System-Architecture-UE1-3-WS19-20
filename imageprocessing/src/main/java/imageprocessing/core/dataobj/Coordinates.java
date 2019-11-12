package imageprocessing.core.dataobj;

import imageprocessing.framework.pmp.img.Coordinate;

import java.util.List;

public class Coordinates {
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
}
