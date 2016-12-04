package org.vaadin.artur.combobox.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

public class FakeVaadinComboBox extends Widget {

    public FakeVaadinComboBox() {
        setElement(Document.get().createElement("vaadin-combo-box"));
    }
}
