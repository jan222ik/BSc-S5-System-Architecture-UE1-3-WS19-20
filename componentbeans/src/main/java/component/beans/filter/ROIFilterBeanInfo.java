package component.beans.filter;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ROIFilterBeanInfo extends SimpleBeanInfo {
    private static final Class beanClass;

    static {
        beanClass = ROIFilter.class;
    }

    public ROIFilterBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor x = new PropertyDescriptor("x", beanClass);
            PropertyDescriptor y = new PropertyDescriptor("y", beanClass);
            PropertyDescriptor width = new PropertyDescriptor("width", beanClass);
            PropertyDescriptor height = new PropertyDescriptor("height", beanClass);
            x.setBound(true);
            y.setBound(true);
            width.setBound(true);
            height.setBound(true);
            return new PropertyDescriptor[]{x, y, width, height};
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
