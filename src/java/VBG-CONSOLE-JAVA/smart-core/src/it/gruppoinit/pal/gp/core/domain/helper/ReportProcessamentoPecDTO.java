package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportProcessamentoPecDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 563393801094474998L;
    private String errore;
    private String avviso;
    private List<String> erroriPec = new ArrayList<String>();
    private int numeroPecProcessate = 0;
    private int numeroPecConErrore = -1;
    private String messaggio = "";

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public String getAvviso() {

	return avviso;
    }

    public void setAvviso(String avviso) {

	this.avviso = avviso;
    }

    public List<String> getErroriPec() {

	return erroriPec;
    }

    public void setErroriPec(List<String> erroriPec) {

	this.erroriPec = erroriPec;
    }

    public int getNumeroPecProcessate() {

	return numeroPecProcessate;
    }

    public void setNumeroPecProcessate(int numeroPecProcessate) {

	this.numeroPecProcessate = numeroPecProcessate;
    }

    public int getNumeroPecConErrore() {

	return numeroPecConErrore == -1 ? this.erroriPec.size() : numeroPecConErrore;
    }

    public void setNumeroPecConErrore(int numeroPecConErrore) {

	this.numeroPecConErrore = numeroPecConErrore;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }
}
