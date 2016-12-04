package org.vaadin.artur.datepicker;

import java.text.DateFormat;
import java.util.Date;

import org.vaadin.elements.Element;

public interface HasElement {
    public Element getElement();

    default public void setAttribute(String attr, DateFormat dateFormatter,
            Date date) {
        if (date != null) {
            getElement().setAttribute(attr, dateFormatter.format(date));
        } else {
            getElement().removeAttribute(attr);
        }
    }

    default public void setAttribute(String attr, String value) {
        if (value != null) {
            getElement().setAttribute(attr, value);
        } else {
            getElement().removeAttribute(attr);
        }
    }

    default public void setAttribute(String attr, boolean onOff) {
        if (onOff) {
            getElement().setAttribute(attr, true);
        } else {
            getElement().removeAttribute(attr);
        }
    }

}
