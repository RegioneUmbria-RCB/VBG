package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniSearchFilter;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ManifestazioniSearchFilterValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return ManifestazioniSearchFilter.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	ManifestazioniSearchFilter filter = (ManifestazioniSearchFilter) obj;
	if (StringUtils.isBlank(filter.getTipoManifestazione())) {
	    e.rejectValue("denominazione", "validation.tipo-manifestaione-non-specificato");
	}
    }
}
