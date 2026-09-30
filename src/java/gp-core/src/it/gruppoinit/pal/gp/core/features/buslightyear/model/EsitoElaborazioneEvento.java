package it.gruppoinit.pal.gp.core.features.buslightyear.model;

import java.util.ArrayList;
import java.util.List;

public class EsitoElaborazioneEvento {

    private List<OperazioneEventoBean> warnings;
    private List<OperazioneEventoBean> errors;

    public EsitoElaborazioneEvento() {

	super();
    }

    public EsitoElaborazioneEvento(List<OperazioneEventoBean> warningsParam, List<OperazioneEventoBean> errorsParam) {

	this();
	this.errors = errorsParam;
	this.warnings = warningsParam;
    }

    public List<OperazioneEventoBean> getWarnings() {

	if (warnings == null) {
	    warnings = new ArrayList<OperazioneEventoBean>();
	}
	return warnings;
    }

    public List<OperazioneEventoBean> getErrors() {

	if (errors == null) {
	    errors = new ArrayList<OperazioneEventoBean>();
	}
	return errors;
    }

    public boolean isErroreOWarning() {

	return isErrore() || isWarning();
    }

    public boolean isErrore() {

	return !getErrors().isEmpty();
    }

    public boolean isWarning() {

	return !getWarnings().isEmpty();
    }
}
