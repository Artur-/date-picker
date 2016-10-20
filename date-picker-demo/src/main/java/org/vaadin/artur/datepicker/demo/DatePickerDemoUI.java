package org.vaadin.artur.datepicker.demo;

import java.util.Date;

import javax.servlet.annotation.WebServlet;

import org.vaadin.artur.datepicker.DatePicker;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("DatePicker Add-on Demo")
@SuppressWarnings("serial")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DatePickerDemoUI extends UI {

    private VerticalLayout layout;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DatePickerDemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        GridLayout gl = new GridLayout(1, 4);
        gl.setSpacing(true);
        gl.setMargin(true);
        gl.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        // Initialize our new UI component
        final PopupDateField datePicker = new DatePicker("A date picker");
        datePicker.addValueChangeListener(e -> {
            Notification.show(
                    "datePicker value changed to " + datePicker.getValue());
        });

        final PopupDateField dateField = new PopupDateField("A date field");
        dateField.addValueChangeListener(e -> {
            Notification
                    .show("dateField value changed to " + dateField.getValue());
        });

        Label newCode = new Label("", ContentMode.PREFORMATTED);
        Label oldCode = new Label("", ContentMode.PREFORMATTED);
        newCode.setSizeUndefined();
        oldCode.setSizeUndefined();
        newCode.addStyleName("code");
        oldCode.addStyleName("code");
        newCode.setValue("PopupDateField datePicker =\n"
                + "     new DatePicker(\"A date picker\");\n"
                + "datePicker.addValueChangeListener(e -> {\n"
                + "     Notification.show(\n"
                + "             \"datePicker value changed to \"\n"
                + "              + datePicker.getValue());\n" + "});\n");
        oldCode.setValue("PopupDateField dateField =\n"
                + "     new PopupDateField(\"A date field\");\n"
                + "datePicker.addValueChangeListener(e -> {\n"
                + "     Notification.show(\n"
                + "             \"datePicker value changed to \"\n"
                + "              + datePicker.getValue());\n" + "});\n");

        gl.addComponents(dateField, oldCode, datePicker, newCode);
        if (false) {
            // Shared property data source
            Property<Date> property = new ObjectProperty<>(new Date());
            Label shared = new Label(property);
            shared.setCaption("Shared property data source");
            PopupDateField pdf = new PopupDateField(property);
            pdf.setImmediate(true);
            gl.addComponents(shared, pdf, new DatePicker(property));
        }
        setContent(gl);
    }
}
