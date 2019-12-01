package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.GUI;
import component.beans.util.SetterHelper;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PopUpDisplay implements PropertyChangeListener {

    private GUI.GUIView frame = null;

    public PopUpDisplay() {
        SetterHelper.initOpenCV();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Method: propertyChange in Class: PopUpDisplay PropName: " + evt.getPropertyName() + " NewValue: " + evt.getNewValue());
        SetterHelper.ifNullableClass(evt.getNewValue(), ImgDTO.class, () -> {
            System.out.println("NULL");
            //TODO Set View to null msg
        }, () -> {
            ImgDTO dto = (ImgDTO) evt.getNewValue();
            if (frame != null) {
                GUI.displayImage(frame, dto.getImage(), "GUI for: " + evt.getPropertyName());
            } else {
                frame = GUI.displayImage(dto.getImage(), "GUI for: " + evt.getPropertyName());
            }
        });
        SetterHelper.ifNullableClass(evt.getNewValue(), LinkedList.class, () -> {
            System.out.println("NULL");
            //TODO set view to null
        }, () -> {
            List<?> list = (List<?>) evt.getNewValue();
            String cat = (!list.isEmpty()) ? "<html><p> " + list.stream().map(o -> {
                if (o != null) {
                    return o.toString();
                } else {
                    return "Null";
                }
            }).collect(Collectors.joining(" <br/> ")) + " </p></html>" : "Empty List";
            if (frame != null) {
                GUI.displayImage(frame, "GUI for: " + evt.getPropertyName(), cat);
            } else {
                frame = GUI.displayImage("GUI for: " + evt.getPropertyName(), cat);
            }
        });
    }
}
