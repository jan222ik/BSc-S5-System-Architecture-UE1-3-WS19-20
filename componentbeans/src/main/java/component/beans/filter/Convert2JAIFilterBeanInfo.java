package component.beans.filter;

import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class Convert2JAIFilterBeanInfo extends SimpleBeanInfo {
    private static final Class beanClass;

    static {
        beanClass = Convert2JAIFilter.class;
    }

    public Convert2JAIFilterBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return null;
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return null;
    }
}
