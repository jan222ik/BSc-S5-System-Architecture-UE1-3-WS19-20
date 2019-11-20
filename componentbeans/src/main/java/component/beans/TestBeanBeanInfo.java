package component.beans;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class TestBeanBeanInfo extends SimpleBeanInfo {

    @Override
    public BeanDescriptor getBeanDescriptor() {
        return super.getBeanDescriptor();
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor name = new PropertyDescriptor("name", TestBean.class);
            PropertyDescriptor number = new PropertyDescriptor("number", TestBean.class);
            return new PropertyDescriptor[]{name, number};
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }
}