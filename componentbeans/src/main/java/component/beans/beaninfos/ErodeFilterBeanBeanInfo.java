package component.beans.beaninfos;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ErodeFilterBeanBeanInfo extends SimpleBeanInfo {

    private static final Class beanClass;

    static {
        beanClass = ErodeFilterBeand.class;
    }

    public ErodeFilterBeanBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor shapeType = new PropertyDescriptor("shapeType", beanClass);
            PropertyDescriptor kernalSize = new PropertyDescriptor("kernalSize", beanClass);
            shapeType.setBound(true);
            kernalSize.setBound(true);
            return new PropertyDescriptor[]{shapeType, kernalSize};
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

