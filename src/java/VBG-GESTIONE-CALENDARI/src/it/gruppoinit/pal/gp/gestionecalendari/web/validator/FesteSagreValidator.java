package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.core.domain.FesteSagre;
import it.gruppoinit.pal.gp.core.helper.EntityUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FesteSagreValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return FesteSagre.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	FesteSagre fs = (FesteSagre) obj;
	if (StringUtils.isBlank(fs.getDenominazione())) {
	    e.rejectValue("festeSagre.denominazione", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getLuogoSvolgimento())) {
	    e.rejectValue("festeSagre.luogoSvolgimento", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getOrganizzatore())) {
	    e.rejectValue("festeSagre.organizzatore", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getTipologia())) {
	    e.rejectValue("festeSagre.tipologia", "validation.campo-obbligatorio");
	}
	if (fs.getAl() == null) {
	    e.rejectValue("festeSagre.al", "validation.campo-obbligatorio");
	}
	if (fs.getDal() == null) {
	    e.rejectValue("festeSagre.dal", "validation.campo-obbligatorio");
	}
	if (EntityUtils.isNestedPropertyBlank(fs.getComuneSvolgimento(), "codicecomune")) {
	    e.rejectValue("festeSagre.comuneSvolgimento.codicecomune", "validation.campo-obbligatorio");
	}
	if (EntityUtils.isNestedPropertyBlank(fs.getComune(), "codicecomune")) {
	    e.rejectValue("festeSagre.comune.codicecomune", "validation.campo-obbligatorio");
	}
	if (fs.getAl() != null && fs.getDal() != null) {
	    if (fs.getDal().compareTo(fs.getAl()) > 0) {
		e.rejectValue("festeSagre.al", "validation.rispettare-ordine-temporale");
	    }
	}
	
	if(Utilities.calculateDifferenceInDays(fs.getDal(), fs.getAl())+1>10)
		{
	    e.rejectValue("festeSagre.al", "validation.rispettare-massimo-gg-sagre");
		}
	
	
    }
}
