package org.vaadin.artur.datepicker.client;

import org.vaadin.artur.datepicker.DatePicker;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Element;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.LayoutManager;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

/**
 * Connector for &lt;vaadin-date-picker&gt;, exists mainly to be able to define
 * the root element tag.
 */
@Connect(DatePicker.class)
public class DatePickerDummyConnector extends AbstractComponentConnector
        implements Paintable {

    private static boolean implementationChecked = false;

    @Override
    protected void init() {
        if (!implementationChecked) {
            implementationChecked = true;
            Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
                @Override
                public boolean execute() {
                    if (isCustomElementUpgraded(getWidget().getElement())) {
                        LayoutManager layoutManager = LayoutManager
                                .get(getConnection());
                        layoutManager.setEverythingNeedsMeasure();
                        layoutManager.layoutNow();
                        return false;
                    } else {
                        return true;
                    }

                }
            }, 100);
        }
    }

    public static boolean isCustomElementUpgraded(Element element) {
        Object data = element.getPropertyObject("__data__");
        return (data != null);
    }

    @Override
    public FakeVaadinDatePicker getWidget() {
        return (FakeVaadinDatePicker) super.getWidget();
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
    }
}
