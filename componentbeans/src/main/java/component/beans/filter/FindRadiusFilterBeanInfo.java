package component.beans.filter;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class FindRadiusFilterBeanInfo extends SimpleBeanInfo {

    private static final Class beanClass;

    static {
        beanClass = FindRadiusFilter.class;
    }

    public FindRadiusFilterBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor threshold = new PropertyDescriptor("threshold", beanClass);
            threshold.setBound(true);
            return new PropertyDescriptor[]{threshold};
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
