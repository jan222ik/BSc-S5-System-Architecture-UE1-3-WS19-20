package component.beans.util;

import java.beans.PropertyChangeListener;

public interface BeanMethods extends PropertyChangeListener {
    void update();

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

}
