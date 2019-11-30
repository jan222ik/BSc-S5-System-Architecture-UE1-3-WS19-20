package component.beans.filter;

import component.beans.dataobj.Coordinate;
import component.beans.dataobj.Coordinates;
import component.beans.dataobj.ImgDTO;
import component.beans.util.BeanMethods;
import component.beans.util.CacheHelper;
import component.beans.util.SetterHelper;

import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Convert2JAIFilter implements BeanMethods {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private final HashMap<Coordinate, Boolean> general = new HashMap<>();
    private final LinkedList<ArrayList<Coordinate>> figures = new LinkedList<>();
    private javax.media.jai.PlanarImage image;
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    public Convert2JAIFilter() {
    }

    private Coordinates process() {
        ImgDTO entity = cacheHelper.getCache();
        if (entity != null) {
            BufferedImage bufferedImage = entity.getImage();
            PlanarImage planarImage = PlanarImage.wrapRenderedImage(bufferedImage);
            planarImage.setProperty("offsetX", entity.getShiftedX());
            planarImage.setProperty("offsetY", entity.getShiftedY());
            return new Coordinates(processInternal(planarImage), entity);
        } else {
            return null;
        }
    }

    @Override
    public void update() {
        Coordinates process = process();
        mPcs.firePropertyChange("jiaNew", null, process);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SetterHelper.ifClass(evt.getNewValue(), ImgDTO.class, () -> {
            cacheHelper.setCache((ImgDTO) evt.getNewValue(), ImgDTO::cloneDTO);
            update();
        });
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    private List<Coordinate> processInternal(PlanarImage planarImage) {
        image = planarImage;
        BufferedImage bi = planarImage.getAsBufferedImage();

        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                int p = bi.getRaster().getSample(x, y, 0);
                if (p == 255 && general.containsKey(new Coordinate(x, y)) == false) {
                    getNextFigure(bi, x, y);        //if there is a not visited white pixel, save all pixels belonging to the same figure
                }
            }
        }

        return calculateCentroids();    //calculate the centroids of all figures
    }

    private void getNextFigure(BufferedImage img, int x, int y) {
        ArrayList<Coordinate> figure = new ArrayList<>();
        general.put(new Coordinate(x, y), true);
        figure.add(new Coordinate(x, y));

        addConnectedComponents(img, figure, x, y);

        figures.add(figure);
    }

    private void addConnectedComponents(BufferedImage img, ArrayList<Coordinate> figure, int x, int y) {
        if (x - 1 >= 0 && general.containsKey((new Coordinate(x - 1, y))) == false && img.getRaster().getSample(x - 1, y, 0) == 255) {
            general.put(new Coordinate(x - 1, y), true);
            figure.add(new Coordinate(x - 1, y));
            addConnectedComponents(img, figure, x - 1, y);
        }
        if (x + 1 < img.getWidth() && general.containsKey((new Coordinate(x + 1, y))) == false && img.getRaster().getSample(x + 1, y, 0) == 255) {
            general.put(new Coordinate(x + 1, y), true);
            figure.add(new Coordinate(x + 1, y));
            addConnectedComponents(img, figure, x + 1, y);
        }
        if (y - 1 >= 0 && general.containsKey((new Coordinate(x, y - 1))) == false && img.getRaster().getSample(x, y - 1, 0) == 255) {
            general.put(new Coordinate(x, y - 1), true);
            figure.add(new Coordinate(x, y - 1));
            addConnectedComponents(img, figure, x, y - 1);
        }
        if (y + 1 < img.getHeight() && general.containsKey((new Coordinate(x, y + 1))) == false && img.getRaster().getSample(x, y + 1, 0) == 255) {
            general.put(new Coordinate(x, y + 1), true);
            figure.add(new Coordinate(x, y + 1));
            addConnectedComponents(img, figure, x, y + 1);
        }
    }

    private List<Coordinate> calculateCentroids() {
        ArrayList<Coordinate> centroids = new ArrayList<>();
        int i = 0;
        for (ArrayList<Coordinate> figure : figures) {
            LinkedList<Integer> xValues = new LinkedList<>();
            LinkedList<Integer> yValues = new LinkedList<>();

            for (Coordinate c : figure) {
                xValues.add(c.x);
                yValues.add(c.y);
            }

            Collections.sort(xValues);
            Collections.sort(yValues);

            int xMedian = xValues.get(xValues.size() / 2);
            int yMedian = yValues.get(yValues.size() / 2);

            centroids.add(new Coordinate(xMedian + (Integer) image.getProperty("offsetX"), yMedian + (Integer) image.getProperty("offsetY")));

            i++;
        }
        return centroids;
    }
}
