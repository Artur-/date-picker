package org.vaadin.artur.datepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.vaadin.elements.ElementIntegration;
import org.vaadin.elements.Root;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Property;
import com.vaadin.ui.PopupDateField;

@JavaScript("vaadin://bower_components/webcomponentsjs/webcomponents-lite.min.js")
public class DatePicker extends PopupDateField {

    private Root root;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "yyyy-MM-dd");

    public DatePicker() {
        root = ElementIntegration.getRoot(this);
        setWidth("200px");
        root.importHtml(
                "VAADIN/bower_components/vaadin-date-picker/vaadin-date-picker.html");

        root.bindAttribute("value", "value-changed");

        root.addEventListener("value-changed", arguments -> {
            try {
                String v = root.getAttribute("value");
                if ("".equals(v)) {
                    setValue(null);
                } else {
                    setValue(dateFormatter.parse(v));
                }
            } catch (ParseException e) {
                setValue(null);
            }
        });
    }

    public DatePicker(Property<Date> dataSource)
            throws IllegalArgumentException {
        this();
        setPropertyDataSource(dataSource);
    }

    public DatePicker(String caption) {
        this();
        setCaption(caption);
    }

    public DatePicker(String caption, Date value) {
        this();
        setCaption(caption);
        setValue(value);
    }

    public DatePicker(String caption, Property<Date> dataSource) {
        this();
        setCaption(caption);
        setPropertyDataSource(dataSource);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAttribute("disabled", !enabled);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        setAttribute("readOnly", readOnly);
    }

    @Override
    protected void setInternalValue(Date newValue) {
        super.setInternalValue(newValue);
        if (newValue == null) {
            root.removeAttribute("value");
        } else {
            root.setAttribute("value", dateFormatter.format(newValue));
        }
    }

    @Override
    public void setRangeStart(Date startDate) {
        super.setRangeStart(startDate);
        setAttribute("min", startDate);
    }

    @Override
    public void setRangeEnd(Date endDate) {
        super.setRangeEnd(endDate);
        setAttribute("max", endDate);
    }

    @Override
    public void setCaption(String caption) {
        setAttribute("label", caption);
    }

    @Override
    public void setInputPrompt(String inputPrompt) {
        setAttribute("placeholder", inputPrompt);
    }

    private void setAttribute(String attr, Date date) {
        if (date != null) {
            root.setAttribute(attr, dateFormatter.format(date));
        } else {
            root.removeAttribute(attr);
        }
    }

    private void setAttribute(String attr, String value) {
        if (value != null) {
            root.setAttribute(attr, value);
        } else {
            root.removeAttribute(attr);
        }
    }

    private void setAttribute(String attr, boolean onOff) {
        if (onOff) {
            root.setAttribute(attr, true);
        } else {
            root.removeAttribute(attr);
        }
    }

}
