package org.vaadin.artur.datepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import org.vaadin.artur.javascriptelement.JavaScriptElement;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.JavaScript;
import com.vaadin.data.HasValue;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ui.UIState.LocaleData;

@JavaScript("vaadin://bower_components/webcomponentsjs/webcomponents-lite.min.js")
@JavaScript("DatePicker.js")
@HtmlImport("vaadin://bower_components/vaadin-date-picker/vaadin-date-picker.html")
public class DatePicker extends JavaScriptElement implements HasValue<Date> {

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "yyyy-MM-dd");

    public DatePicker() {
        setWidth("200px");
        addFunction("valueChanged", arguments -> {
            try {
                String v = arguments.getString(0);
                if ("".equals(v)) {
                    setValue(null, true);
                } else {
                    setValue(dateFormatter.parse(v), true);
                }
            } catch (ParseException e) {
                setValue(null);
            }
        });
    }

    @Override
    protected DatePickerState getState() {
        return (DatePickerState) super.getState();
    }

    @Override
    protected DatePickerState getState(boolean markAsDirty) {
        return (DatePickerState) super.getState(markAsDirty);
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

    private void setLocaleData(Locale locale) {
        PublicLocaleService service = new PublicLocaleService();
        LocaleData data = service.createLocaleData(locale);

        getState().localeData = data;

    }

    public DatePicker(String caption) {
        this();
        setCaption(caption);
    }

    public DatePicker(String caption, Date value) {
        this();
        setCaption(caption);
        // setValue(value);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAttribute("disabled", !enabled);
    }

    @Override
    public boolean isEnabled() {
        return !getBooleanAttribute("disabled");
    }

    @Override
    public boolean isReadOnly() {
        return getBooleanAttribute("readOnly");
    }

    @Override
    public void setRequiredIndicatorVisible(boolean visible) {
        super.setRequiredIndicatorVisible(visible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return super.isRequiredIndicatorVisible();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        setAttribute("readOnly", readOnly);
    }

    @Override
    public void setValue(Date value) {
        setValue(value, false);
    }

    protected void setValue(Date value, boolean userOriginated) {
        if (userOriginated && isReadOnly()) {
            return;
        }
        Date oldValue = this.getValue();
        if (Objects.equals(value, oldValue)) {
            return;
        }
        doSetValue(value);

        ValueChangeEvent<Date> event = new ValueChangeEvent<>(this, oldValue,
                userOriginated);

        fireEvent(event);

    }

    private void doSetValue(Date value) {
        setAttribute("value", dateFormatter, value);
    }

    @Override
    public Date getValue() {
        return getAttribute("value", dateFormatter);
    }

    public void setRangeStart(Date startDate) {
        setAttribute("min", dateFormatter, startDate);
    }

    public void setRangeEnd(Date endDate) {
        setAttribute("max", dateFormatter, endDate);
    }

    @Override
    public String getCaption() {
        return getAttribute("label");
    }

    @Override
    public void setCaption(String caption) {
        setAttribute("label", caption);
    }

    public void setShowISOWeekNumbers(boolean showWeekNumbers) {
        setAttribute("show-week-numbers", showWeekNumbers);
    }

    public boolean isShowISOWeekNumbers() {
        return getBooleanAttribute("show-week-numbers");
    }

    @Override
    public Registration addValueChangeListener(
            ValueChangeListener<Date> listener) {
        return addListener(ValueChangeEvent.class, listener,
                ValueChangeListener.VALUE_CHANGE_METHOD);
    }

    public void setPlaceholder(String placeholder) {
        setAttribute("placeholder", placeholder);

    }
}