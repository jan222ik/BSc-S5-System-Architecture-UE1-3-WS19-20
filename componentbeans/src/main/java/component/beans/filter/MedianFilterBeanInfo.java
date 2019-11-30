package component.beans.filter;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class MedianFilterBeanInfo extends SimpleBeanInfo {
    private static final Class beanClass;

    static {
        beanClass = MedianFilter.class;
    }

    public MedianFilterBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor ksize = new PropertyDescriptor("ksize", beanClass);
            ksize.setBound(true);
            return new PropertyDescriptor[]{ksize};
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
