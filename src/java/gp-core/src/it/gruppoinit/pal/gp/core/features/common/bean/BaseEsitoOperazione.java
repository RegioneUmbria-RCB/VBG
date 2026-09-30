package it.gruppoinit.pal.gp.core.features.common.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseEsitoOperazione {

    public BaseEsitoOperazione() {

	super();
    }

    public enum ESITO {
	SUCCESS,
	WARNING,
	ERROR
    }

    private ESITO esito;

    @XmlElement(name = "esito")
    public String getEsito() {

	return esito.name();
    }

    @XmlElement(name = "errori")
    private List<String> errori = null;
    @XmlElement(name = "warnings")
    private List<String> warnings = null;

    public List<String> getErrori() {

	if (errori == null) {
	    errori = new ArrayList<String>();
	}
	return errori;
    }

    public List<String> getWarnings() {

	if (warnings == null) {
	    warnings = new ArrayList<String>();
	}
	return warnings;
    }

    public BaseEsitoOperazione(ESITO esito) {

	this();
	this.esito = esito;
    }

    public BaseEsitoOperazione addError(String error) {

	getErrori().add(error);
	return this;
    }

    public String restituisciErroriComeString(String lineSeparator) {

	StringBuilder sb = new StringBuilder();
	List<String> s = getErrori();
	for (String e : s) {
	    sb.append(lineSeparator).append(e);
	}
	return sb.toString();
    }
}
