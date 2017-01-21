package org.vaadin.artur.javascriptelement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.ui.AbstractJavaScriptComponent;

public class JavaScriptElement extends AbstractJavaScriptComponent {

    protected void setAttribute(String attribute, boolean value) {
        setAttribute(attribute, value ? "" : null);
    }

    protected void setAttribute(String attribute, String value) {
        getState().attributes.put(attribute, value);
    }

    protected String getAttribute(String attribute) {
        return getState(false).attributes.get(attribute);
    }

    protected boolean hasAttribute(String attribute) {
        return getAttribute(attribute) != null;
    }

    protected boolean getBooleanAttribute(String attr) {
        return hasAttribute(attr);
    }

    protected void setAttribute(String attribute,
            SimpleDateFormat dateFormatter, Date date) {
        if (date == null) {
            setAttribute(attribute, null);
        } else {
            setAttribute(attribute, dateFormatter.format(date));
        }

    }

    protected Date getAttribute(String attr, SimpleDateFormat dateFormatter) {
        String v = getAttribute(attr);
        if (v == null) {
            return null;
        } else {
            try {
                return dateFormatter.parse(v);
            } catch (ParseException e) {
                return null;
            }
        }

    }

    @Override
    protected JavaScriptElementState getState() {
        return (JavaScriptElementState) super.getState();
    }

    @Override
    protected JavaScriptElementState getState(boolean markAsDirty) {
        return (JavaScriptElementState) super.getState(markAsDirty);
    }

}
