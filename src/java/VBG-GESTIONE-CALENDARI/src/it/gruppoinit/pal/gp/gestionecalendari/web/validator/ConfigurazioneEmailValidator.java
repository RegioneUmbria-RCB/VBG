package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneEmail;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ConfigurazioneEmailValidator implements Validator {

    public boolean supports(Class clazz) {

	return ConfigurazioneEmail.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	ConfigurazioneEmail fs = (ConfigurazioneEmail) obj;
	if (StringUtils.isBlank(fs.getMailFrom())) {
	    e.rejectValue("mailFrom", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getMailTo())) {
	    e.rejectValue("mailTo", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getMailCorpo())) {
	    e.rejectValue("mailCorpo", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getMailOggetto())) {
	    e.rejectValue("mailOggetto", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getMailPort())) {
	    e.rejectValue("mailPort", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getMailProtocoll())) {
	    e.rejectValue("mailProtocoll", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getMailServer())) {
	    e.rejectValue("mailServer", "validation.campo-obbligatorio");
	}
    }
}
