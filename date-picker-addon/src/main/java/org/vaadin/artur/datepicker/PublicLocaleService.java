package org.vaadin.artur.datepicker;

import java.util.Locale;

import com.vaadin.server.LocaleService;
import com.vaadin.shared.ui.ui.UIState.LocaleData;

/**
 * Hack to be able to use createLocaleData.
 */
public class PublicLocaleService extends LocaleService {

    public PublicLocaleService() {
        super(null, null);
    }

    @Override
    public LocaleData createLocaleData(Locale locale) {
        return super.createLocaleData(locale);
    }
}