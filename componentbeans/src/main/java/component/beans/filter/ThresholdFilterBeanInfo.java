package component.beans.filter;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ThresholdFilterBeanInfo extends SimpleBeanInfo {
    private static final Class beanClass;

    static {
        beanClass = ThresholdFilter.class;
    }

    public ThresholdFilterBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor thresh = new PropertyDescriptor("thresh", beanClass);
            PropertyDescriptor type = new PropertyDescriptor("type", beanClass);
            thresh.setBound(true);
            type.setBound(true);
            return new PropertyDescriptor[]{thresh, type};
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
