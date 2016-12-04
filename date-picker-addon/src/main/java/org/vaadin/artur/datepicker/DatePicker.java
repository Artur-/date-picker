package org.vaadin.artur.datepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.vaadin.elements.Element;
import org.vaadin.elements.ElementIntegration;
import org.vaadin.elements.Root;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.Property;
import com.vaadin.server.LocaleService;
import com.vaadin.shared.ui.ui.UIState.LocaleData;
import com.vaadin.ui.PopupDateField;

import elemental.json.Json;
import elemental.json.JsonArray;

@JavaScript("vaadin://bower_components/webcomponentsjs/webcomponents-lite.min.js")
public class DatePicker extends PopupDateField implements HasInputPrompt {

    private Root root;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "yyyy-MM-dd");

    public DatePicker() {
        root = ElementIntegration.getRoot(this);
        setWidth("200px");
        root.importHtml(
                "VAADIN/bower_components/vaadin-date-picker/vaadin-date-picker.html");

        root.eval("e.style.paddingBottom='8px'");
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

    @Override
    public void attach() {
        super.attach();
        setLocaleData(getLocale());
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
        if (isAttached()) {
            setLocaleData(getLocale());
        }
    }

    /**
     * Hack to be able to use createLocaleData.
     */
    private class PublicLocaleService extends LocaleService {

        public PublicLocaleService() {
            super(null, null);
        }

        @Override
        public LocaleData createLocaleData(Locale locale) {
            return super.createLocaleData(locale);
        }
    }

    private void setLocaleData(Locale locale) {
        PublicLocaleService service = new PublicLocaleService();
        LocaleData data = service.createLocaleData(locale);

        int firstDayOfWeek = data.firstDayOfWeek;

        // Uses setInterval to wait until the element has been upgraded
        root.eval(
                "var elem = e;" //
                        + "var id = setInterval(function() {" //
                        + "   if (elem.set) {" //
                        + "     elem.set('i18n.firstDayOfWeek',$0);" //
                        + "     elem.set('i18n.monthNames',$1);" //
                        + "     elem.set('i18n.weekdays',$2);" //
                        + "     elem.set('i18n.weekdaysShort',$3);" //
                        + "     clearInterval(id);" //
                        + "   }" //
                        + "},1);",
                firstDayOfWeek, createArray(data.monthNames),
                createArray(data.dayNames), createArray(data.shortDayNames));

    }

    private JsonArray createArray(String[] data) {
        JsonArray json = Json.createArray();
        for (String value : data) {
            json.set(json.length(), value);
        }
        return json;

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
        setAttribute("value", dateFormatter, newValue);
    }

    @Override
    public void setRangeStart(Date startDate) {
        super.setRangeStart(startDate);
        setAttribute("min", dateFormatter, startDate);
    }

    @Override
    public void setRangeEnd(Date endDate) {
        super.setRangeEnd(endDate);
        setAttribute("max", dateFormatter, endDate);
    }

    @Override
    public void setCaption(String caption) {
        setAttribute("label", caption);
    }

    @Override
    public void setShowISOWeekNumbers(boolean showWeekNumbers) {
        setAttribute("show-week-numbers", showWeekNumbers);
    }

    @Override
    public Element getElement() {
        return root;
    }

}
