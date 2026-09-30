package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;
import it.gruppoinit.pal.gp.core.helper.EntityUtils;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.FiereMostrePeriodiCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniCommand;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FiereMostreValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return ManifestazioniCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	ManifestazioniCommand command = (ManifestazioniCommand) obj;
	if (StringUtils.isBlank(command.getFiereMostre().getDenominazione())) {
	    e.rejectValue("fiereMostre.denominazione", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(command.getFiereMostre().getLuogoSvolgimento())) {
	    e.rejectValue("fiereMostre.luogoSvolgimento", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(command.getFiereMostre().getOrganizzatore())) {
	    e.rejectValue("fiereMostre.organizzatore", "validation.campo-obbligatorio");
	}
	List<FiereMostrePeriodiCommand> periodi = command.getPeriodi();
	if (periodi.isEmpty()) {
	    if (command.getPeriodo().getDal() == null) {
		e.rejectValue("periodo.dal", "validation.campo-obbligatorio");
	    }
	    if (command.getPeriodo().getAl() == null) {
		e.rejectValue("periodo.al", "validation.campo-obbligatorio");
	    }
	    if (command.getPeriodo().getDal() != null && command.getPeriodo().getAl() != null) {
		if (command.getPeriodo().getDal().after(command.getPeriodo().getAl())) {
		    e.rejectValue("periodo.al", "validation.rispettare-ordine-temporale");
		}
	    }
	} else {
	    int numPeriodiVuoti = 0;
	    for (int i = 0; i < periodi.size(); i++) {
		if (periodi.get(i).getDal() == null && periodi.get(i).getAl() == null) {
		    numPeriodiVuoti++;
		}
		if (periodi.get(i).getDal() != null) {
		    if (periodi.get(i).getAl() == null) {
			e.rejectValue("periodi[" + i + "].al", "validation.campo-obbligatorio");
		    } else {
			if (periodi.get(i).getDal().after(periodi.get(i).getAl())) {
			    e.rejectValue("periodi[" + i + "].al", "validation.rispettare-ordine-temporale");
			}
		    }
		} else {
		    if (periodi.get(i).getAl() != null) {
			e.rejectValue("periodi[" + i + "].dal", "validation.campo-obbligatorio");
		    }
		}
	    }
	    if (command.getPeriodo().getDal() == null && command.getPeriodo().getAl() == null) {
		numPeriodiVuoti++;
	    }
	    if (command.getPeriodo().getDal() == null) {
		if (command.getPeriodo().getAl() != null) {
		    e.rejectValue("periodo.dal", "validation.campo-obbligatorio");
		}
	    } else {
		if (command.getPeriodo().getAl() == null) {
		    e.rejectValue("periodo.al", "validation.campo-obbligatorio");
		} else {
		    if (command.getPeriodo().getDal().after(command.getPeriodo().getAl())) {
			e.rejectValue("periodo.al", "validation.rispettare-ordine-temporale");
		    }
		}
	    }
	    if (numPeriodiVuoti == periodi.size() + 1) {
		e.rejectValue("periodi[0].al", "validation.almeno-un-periodo-obbligatorio");
	    }
	}
	if (EntityUtils.isNestedPropertyBlank(command.getFiereMostre().getComuneSvolgimento(), "codicecomune")) {
	    e.rejectValue("fiereMostre.comuneSvolgimento.codicecomune", "validation.campo-obbligatorio");
	}
	if (EntityUtils.isNestedPropertyBlank(command.getFiereMostre().getComune(), "codicecomune")) {
	    e.rejectValue("fiereMostre.comune.codicecomune", "validation.campo-obbligatorio");
	}
	// TODO mancano alcuni campi
	FiereMostreMerceologie merceologia = command.getMerceologia();
	if (StringUtils.isEmpty(command.getMerceologia().getMerceologia())) {
	    if (command.getMerceologie().isEmpty()) {
		e.rejectValue("merceologia.merceologia", "validation.campo-obbligatorio");
	    }
	} else {
	    if ("(28) Altro (specificare sotto il settore)".equals(merceologia.getMerceologia())) {
		if (StringUtils.isEmpty(command.getMerceologiaAltro().getMerceologia())) {
		    e.rejectValue("merceologia.merceologia", "validation.campo-altro-obbligatorio");
		}
	    }
	}
	if (StringUtils.isBlank(command.getFiereMostre().getClassificazione())) {
	    e.rejectValue("fiereMostre.classificazione", "validation.campo-obbligatorio");
	}
    }
}
