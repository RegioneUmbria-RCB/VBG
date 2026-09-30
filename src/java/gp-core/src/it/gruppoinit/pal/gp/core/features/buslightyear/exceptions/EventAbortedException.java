package it.gruppoinit.pal.gp.core.features.buslightyear.exceptions;

import it.gruppoinit.pal.gp.core.exception.ExceptionUtils;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

public class EventAbortedException extends Exception {

    /**
     * 
     */
    static final long serialVersionUID = 8885156687626336573L;
    private String sottoscrittore;
    private IEvent eventoAbortito;
    private EsitoElaborazioneEvento esito;

    public EventAbortedException() {

	super();
    }

    public EventAbortedException(String message, String sottoscrittore, IEvent eventoAbortito) {

	super(message);
	this.sottoscrittore = sottoscrittore;
	this.eventoAbortito = eventoAbortito;
    }

    public EventAbortedException(Exception e, String sottoscrittore, IEvent eventoAbortito) {

	super(ExceptionUtils.getRootCause(e));
	this.sottoscrittore = sottoscrittore;
	this.eventoAbortito = eventoAbortito;
    }

    public EventAbortedException(Exception e, String sottoscrittore, IEvent eventoAbortito, EsitoElaborazioneEvento esito) {

	this(e, sottoscrittore, eventoAbortito);
	this.esito = esito;
    }

    public EventAbortedException(EsitoElaborazioneEvento esito, IEvent eventoAbortito) {

	this.eventoAbortito = eventoAbortito;
	this.esito = esito;
    }

    public String getSottoscrittore() {

	return sottoscrittore;
    }

    public IEvent getEventoAbortito() {

	return eventoAbortito;
    }

    public EsitoElaborazioneEvento getEsito() {

	return esito;
    }
}
