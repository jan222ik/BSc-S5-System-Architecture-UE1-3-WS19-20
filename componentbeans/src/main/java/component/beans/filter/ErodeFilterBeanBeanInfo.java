package component.beans.filter;

import java.beans.*;

public class ErodeFilterBeanBeanInfo extends SimpleBeanInfo {

    private static final Class beanClass;

    static {
        beanClass = ErodeFilterBean.class;
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
