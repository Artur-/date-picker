package org.vaadin.artur.datepicker;

public interface HasInputPrompt extends HasElement {
    default public void setInputPrompt(String inputPrompt) {
        getElement().setAttribute("placeholder", inputPrompt);
    }

}
