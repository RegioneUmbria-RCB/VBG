package it.gruppoinit.domain;

import java.io.Serializable;

public class CampiDinamiciScript implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3477719550860572973L;
    private String evento;
    private String scriptBase64;

    public CampiDinamiciScript() {

	super();
    }

    public CampiDinamiciScript(String evento, String scriptBase64) {

	this();
	this.evento = evento;
	this.scriptBase64 = scriptBase64;
    }

    public String getEvento() {

	return evento;
    }

    public void setEvento(String evento) {

	this.evento = evento;
    }

    public String getScriptBase64() {

	return scriptBase64;
    }

    public void setScriptBase64(String scriptBase64) {

	this.scriptBase64 = scriptBase64;
    }
}
