package org.vaadin.artur.datepicker.demo;

import java.util.Date;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import org.vaadin.artur.datepicker.DatePicker;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.HasValue.ValueChangeEvent;
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
        localeDatePicker.setPlaceholder(
                "Date picker using selected locale, input prompt and week numbers");
        localeDatePicker.setShowISOWeekNumbers(true);
        localeDatePicker.addValueChangeListener(e -> {
            Notification.show("datePicker value changed to " + e.getValue());
        });

        gl.addComponents(localeDatePicker);
        ComboBox<Locale> localeSelect = new ComboBox<>("Locale");
        localeSelect.setItems(Locale.ENGLISH, new Locale("fi", "FI"),
                Locale.CANADA, Locale.CHINA, Locale.GERMANY, Locale.JAPANESE,
                Locale.KOREAN);
        localeSelect.setItemCaptionGenerator(Locale::getDisplayName);

        localeSelect.addValueChangeListener(event -> {

            // localeDatePicker.setLocale(localeSelect.getValue());
            localeDatePicker.setLocale(event.getValue());
        });

        localeSelect.setValue(new Locale("fi", "FI"));
        gl.addComponent(localeSelect);

        setContent(gl);
    }

    private void showValue(ValueChangeEvent<Date> e) {
        Notification.show("datePicker value changed to " + e.getValue());

    }
}
