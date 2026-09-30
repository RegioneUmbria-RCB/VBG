package it.gruppoinit.pal.gp.core.features.autorizzazioni.auditing;

public class ChiusuraAutomaticaAutorizzazioniLogger extends AbstractAutorizzazioniLogger {

    public ChiusuraAutomaticaAutorizzazioniLogger(String autore, String messaggio) {

	super(autore);
	this.messaggio = messaggio;
    }

    public ChiusuraAutomaticaAutorizzazioniLogger setMessaggio(String messaggio) {

	this.messaggio = messaggio;
	return this;
    }
}
