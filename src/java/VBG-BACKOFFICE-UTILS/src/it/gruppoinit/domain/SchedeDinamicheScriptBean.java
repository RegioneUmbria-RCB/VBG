package it.gruppoinit.domain;

import java.io.Serializable;

public class SchedeDinamicheScriptBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3052868680391859471L;
    private String evento;
    private String base64Script;

    public SchedeDinamicheScriptBean() {

	super();
    }

    public SchedeDinamicheScriptBean(String evento, String base64Script) {

	this();
	this.evento = evento;
	this.base64Script = base64Script;
    }

    public String getEvento() {

	return evento;
    }

    public void setEvento(String evento) {

	this.evento = evento;
    }

    public String getBase64Script() {

	return base64Script;
    }

    public void setBase64Script(String base64Script) {

	this.base64Script = base64Script;
    }
}
