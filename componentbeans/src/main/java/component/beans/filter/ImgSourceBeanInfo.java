package component.beans.filter;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ImgSourceBeanInfo extends SimpleBeanInfo {

    private static final Class beanClass;

    static {
        beanClass = ImgSource.class;
    }

    public ImgSourceBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor imgPath = new PropertyDescriptor("imgPath", beanClass);
            imgPath.setBound(true);
            return new PropertyDescriptor[]{imgPath};
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
