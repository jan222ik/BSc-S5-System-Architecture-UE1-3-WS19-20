package component.beans.filter;

import component.beans.dataobj.Coordinates;

public class FilterFirstDisk {

    private int removeFirstN;

    public FilterFirstDisk() {
    }

    private Coordinates process(Coordinates entity) {
        entity.setCoordinates(entity.getCoordinates().subList(removeFirstN, entity.getCoordinates().size()));
        return entity;
    }
}
