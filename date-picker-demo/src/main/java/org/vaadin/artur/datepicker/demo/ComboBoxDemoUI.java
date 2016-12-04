package org.vaadin.artur.datepicker.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.vaadin.artur.combobox.ComboBox;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;

@Theme("demo")
@Title("DatePicker Add-on Demo")
@SuppressWarnings("serial")
@Viewport("user-scalable=no,initial-scale=1.0")
public class ComboBoxDemoUI extends UI {

    @WebServlet(value = "/combobox/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ComboBoxDemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    public static class Person {
        private String first, last;
        private int age;
        private boolean alive;

        public Person(String first, String last, int age, boolean alive) {
            this.first = first;
            this.last = last;
            this.age = age;
            this.alive = alive;
        }

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        public String getFullName() {
            return getFirst() + " " + getLast();
        }

        public int getAge() {
            return age;
        }

        public boolean isAlive() {
            return alive;
        }
    }

    @Override
    protected void init(VaadinRequest request) {
        GridLayout gl = new GridLayout(2, 4);
        gl.setSpacing(true);
        gl.setMargin(true);
        gl.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        List<Person> personList = new ArrayList<>();
        personList.add(new Person("John", "Doe", 82, true));
        personList.add(new Person("Jeff", "Heff", 34, true));

        // Initialize our new UI component
        BeanItemContainer<Person> persons = new BeanItemContainer<>(
                Person.class);
        persons.addAll(personList);

        ComboBox comboBox = new ComboBox("A combo box", persons) {
            @Override
            public String getItemCaption(Object itemId) {
                Person p = (Person) itemId;
                return p.getFullName();
            }
        };
        comboBox.setValue(comboBox.getItemIds().iterator().next());

        gl.addComponents(comboBox);
        gl.space();

        setContent(gl);
    }

}
