package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.RiferimentoCatastaleType;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CatastoValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return RiferimentoCatastaleType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	RiferimentoCatastaleType cat = (RiferimentoCatastaleType) obj;
	if (StringUtils.isBlank(cat.getTipoCatasto())) {
	    e.rejectValue("catasto.tipoCatasto", "error.tipo-catasto-obbligatorio");
	}
	if (StringUtils.isBlank(cat.getFoglio())) {
	    e.rejectValue("catasto.foglio", "error.foglio-obbligatorio");
	}
	if (StringUtils.isBlank(cat.getParticella())) {
	    e.rejectValue("catasto.particella", "error.particella-obbligatoria");
	}
    }
}
