package it.gruppoinit.pal.gp.core.service.exception;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.ExceptionUtils;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;

public class MercatiAppException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5209215849301256655L;
    private String codice;
    private String descrizione;

    private MercatiAppException() {

	super();
    }

    public MercatiAppException(String errore, Throwable throwable) {

	super(errore, throwable);
	this.codice = "50001";
	this.descrizione = errore;
    }

    public MercatiAppException(String errore) {

	super(errore);
	this.codice = "50001";
	this.descrizione = errore;
    }

    public MercatiAppException(Throwable throwable) {

	super(throwable);
	this.codice = "50001";
	this.descrizione = ExceptionUtils.getRootCause(throwable).getMessage();
    }

    public MercatiAppException(String codice, String descrizione) {

	this();
	this.codice = codice;
	this.descrizione = descrizione;
    }

    public CodiceDescrizioneBean getErrore() {

	CodiceDescrizioneBean ret = new CodiceDescrizioneBean();
	ret.setCodice(this.codice);
	ret.setDescrizione(this.descrizione);
	return ret;
    }
}
