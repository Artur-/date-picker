org_vaadin_artur_datepicker_DatePicker = function() {
	var connector = this;
	var e = this.getElement();
	e.style.paddingBottom = '8px';

	e.addEventListener("value-changed", function(event) {
		connector.valueChanged(e.value);
	});

	this.onStateChange = function() {
		var state = this.getState();
		var attrs = state.attributes;
		var localeData = state.localeData;

		if (attrs) {
			Object.keys(attrs).forEach(
					function(attr) {
						if (e.getAttribute(attr) != attrs[attr]) {
							if (attrs[attr] == null) {
								e.removeAttribute(attr);
								console.log("removing attribute " + attr);
							} else {
								e.setAttribute(attr, attrs[attr]);
								console.log("setting attribute " + attr + "="
										+ attrs[attr]);
							}
						}
					});
		}
		if (localeData) {
			e.i18n.firstDayOfWeek = localeData.firstDayOfWeek;
			e.set('i18n.monthNames', localeData.monthNames);
			e.set('i18n.weekdays', localeData.dayNames);
			e.set('i18n.weekdaysShort', localeData.shortDayNames);
		}
	}
}

org_vaadin_artur_datepicker_DatePicker.tag = "vaadin-date-picker";