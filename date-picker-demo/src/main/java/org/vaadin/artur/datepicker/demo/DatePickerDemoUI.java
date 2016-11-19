package org.vaadin.artur.datepicker.demo;

import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import org.vaadin.artur.datepicker.DatePicker;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@Theme("demo")
@Title("DatePicker Add-on Demo")
@SuppressWarnings("serial")
@Viewport("user-scalable=no,initial-scale=1.0")
public class DatePickerDemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DatePickerDemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        GridLayout gl = new GridLayout(2, 4);
        gl.setSpacing(true);
        gl.setMargin(true);
        gl.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        // Initialize our new UI component
        DatePicker datePicker = new DatePicker(
                "Browser locale date picker using caption");
        datePicker.addValueChangeListener(this::showValue);

        gl.addComponents(datePicker);
        gl.space();

        DatePicker localeDatePicker = new DatePicker();
        localeDatePicker.setWidth("500px");
        localeDatePicker.setInputPrompt(
                "Date picker using selected locale, input prompt and week numbers");
        localeDatePicker.setShowISOWeekNumbers(true);
        localeDatePicker.addValueChangeListener(e -> {
            Notification.show("datePicker value changed to "
                    + e.getProperty().getValue());
        });

        gl.addComponents(localeDatePicker);
        ComboBox localeSelect = new ComboBox("Locale");
        localeSelect.addItem(Locale.ENGLISH);
        localeSelect.addItem(new Locale("fi", "FI"));
        localeSelect.addItem(Locale.CANADA);
        localeSelect.addItem(Locale.CHINA);
        localeSelect.addItem(Locale.GERMANY);
        localeSelect.addItem(Locale.JAPANESE);
        localeSelect.addItem(Locale.KOREAN);

        localeSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                localeDatePicker.setLocale((Locale) localeSelect.getValue());
            }
        });

        localeSelect.setValue(new Locale("fi", "FI"));
        gl.addComponent(localeSelect);

        setContent(gl);
    }

    private void showValue(Property.ValueChangeEvent e) {
        Notification.show(
                "datePicker value changed to " + e.getProperty().getValue());

    }
}
