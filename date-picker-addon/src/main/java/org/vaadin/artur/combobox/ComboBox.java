package org.vaadin.artur.combobox;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import org.vaadin.artur.datepicker.HasInputPrompt;
import org.vaadin.elements.Element;
import org.vaadin.elements.ElementIntegration;
import org.vaadin.elements.Root;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class ComboBox extends com.vaadin.ui.ComboBox implements HasInputPrompt {

    private Root root;

    public ComboBox() {
        super();

        root = ElementIntegration.getRoot(this);
        setWidth("200px");
        root.importHtml(
                "VAADIN/bower_components/vaadin-combo-box/vaadin-combo-box.html");

        root.bindAttribute("value", "value-changed");

        root.addEventListener("value-changed", arguments -> {
            String key = root.getAttribute("value");
            Object itemId = itemIdMapper.get(key);
            setValue(itemId);
        });

        setAttribute("item-label-path", "text");
        setAttribute("item-value-path", "key");
    }

    public ComboBox(String caption, Collection<?> options) {
        this();
        setCaption(caption);
        setOptions(options);
    }

    public ComboBox(String caption, Container dataSource) {
        this();
        setCaption(caption);
        setContainerDataSource(dataSource);
    }

    public ComboBox(String caption) {
        this();
        setCaption(caption);
    }

    private void setOptions(Collection<?> options) {
        final Container c = new IndexedContainer();
        if (options != null) {
            for (final Iterator<?> i = options.iterator(); i.hasNext();) {
                c.addItem(i.next());
            }
        }
        setContainerDataSource(c);
    }

    @Override
    public void setContainerDataSource(Container newDataSource) {
        super.setContainerDataSource(newDataSource);

        if (getElement() == null) {
            return;
        }

    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);
        updateItems();
    }

    private void updateItems() {
        Stream<JsonValue> foo = getContainerDataSource().getItemIds().stream()
                .map(itemId -> {
                    JsonObject o = Json.createObject();
                    o.put("text", Json.create(getItemCaption(itemId)));
                    o.put("key", itemIdMapper.key(itemId));

                    return o;
                });
        getElement().eval("e.items=$0", toJsonArray(foo));
    }

    private JsonArray toJsonArray(Stream<JsonValue> foo) {
        JsonArray array = Json.createArray();
        foo.forEach(item -> {
            array.set(array.length(), item);
        });

        return array;
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
    protected void setInternalValue(Object newValue) {
        super.setInternalValue(newValue);
        setAttribute("value", itemIdMapper.key(newValue));
    }

    @Override
    public void setCaption(String caption) {
        setAttribute("label", caption);
    }

    @Override
    public Element getElement() {
        return root;
    }

}
