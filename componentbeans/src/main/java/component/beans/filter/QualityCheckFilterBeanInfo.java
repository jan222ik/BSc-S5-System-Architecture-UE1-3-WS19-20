package component.beans.filter;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class QualityCheckFilterBeanInfo extends SimpleBeanInfo {
    private static final Class beanClass;

    static {
        beanClass = QualityCheckFilter.class;
    }

    public QualityCheckFilterBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor expectedCoordinatesFile = new PropertyDescriptor("expectedCoordinatesFile", beanClass);
            PropertyDescriptor accuracy = new PropertyDescriptor("accuracy", beanClass);
            expectedCoordinatesFile.setBound(true);
            accuracy.setBound(true);
            return new PropertyDescriptor[]{expectedCoordinatesFile, accuracy};
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return new PropertyDescriptor[]{};
        }
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return null;
    }
}
