package component.beans.filter;

public class Main {
    public static void main(String[] args) {
        ImgSource imgSource = new ImgSource();
        ROIFilter roiFilter = new ROIFilter();
        ThresholdFilter thresholdFilter = new ThresholdFilter();
        MedianFilter medianFilter = new MedianFilter();
        ErodeFilterBean erodeFilterBean = new ErodeFilterBean();
        Convert2JAIFilter convert2JAIFilter = new Convert2JAIFilter();
        QualityCheckFilter qualityCheckFilter = new QualityCheckFilter();
        FindRadiusFilter findRadiusFilter = new FindRadiusFilter();
        SinkImpl sink = new SinkImpl();

        imgSource.addPropertyChangeListener(roiFilter);
        imgSource.addPropertyChangeListener(getGUI());

        roiFilter.addPropertyChangeListener(thresholdFilter);
        roiFilter.addPropertyChangeListener(getGUI());

        thresholdFilter.addPropertyChangeListener(medianFilter);
        thresholdFilter.addPropertyChangeListener(getGUI());

        medianFilter.addPropertyChangeListener(erodeFilterBean);
        medianFilter.addPropertyChangeListener(getGUI());

        erodeFilterBean.addPropertyChangeListener(convert2JAIFilter);
        erodeFilterBean.addPropertyChangeListener(getGUI());

        convert2JAIFilter.addPropertyChangeListener(qualityCheckFilter);
        convert2JAIFilter.addPropertyChangeListener(getGUI());

        qualityCheckFilter.addPropertyChangeListener(findRadiusFilter);
        qualityCheckFilter.addPropertyChangeListener(getGUI());

        findRadiusFilter.addPropertyChangeListener(sink);
        findRadiusFilter.addPropertyChangeListener(getGUI());

        imgSource.update();
    }

    private static PopUpDisplay getGUI() {
        return new PopUpDisplay();
    }
}
