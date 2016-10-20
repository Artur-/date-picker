package org.vaadin.artur.datepicker.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

public class FakeVaadinDatePicker extends Widget {

    public FakeVaadinDatePicker() {
        setElement(Document.get().createElement("vaadin-date-picker"));
    }
}
