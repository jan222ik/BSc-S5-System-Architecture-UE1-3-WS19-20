package component.beans.filter;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class SinkImplBeanInfo extends SimpleBeanInfo {
    private static final Class beanClass;

    static {
        beanClass = SinkImpl.class;
    }

    public SinkImplBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor outputPath = new PropertyDescriptor("outputPath", beanClass);
            PropertyDescriptor accuracy = new PropertyDescriptor("accuracy", beanClass);
            outputPath.setBound(true);
            accuracy.setBound(true);
            return new PropertyDescriptor[]{outputPath, accuracy};
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
