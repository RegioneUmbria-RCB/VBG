package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Movimenti;

public class MovimentiHelper {

    private Movimenti movimento;
    private boolean effettuaChiusura;
    private boolean cdsCommissione;
    private boolean rilascioAutorizzazione;
    private boolean movimentoAvvio;
    private boolean eseguito;

    public MovimentiHelper() {

	this.movimento = new Movimenti();
    }

    public Movimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(Movimenti movimento) {

	this.movimento = movimento;
    }

    public boolean isEffettuaChiusura() {

	return effettuaChiusura;
    }

    public void setEffettuaChiusura(boolean effettuaChiusura) {

	this.effettuaChiusura = effettuaChiusura;
    }

    public boolean isCdsCommissione() {

	return cdsCommissione;
    }

    public void setCdsCommissione(boolean cdsCommissione) {

	this.cdsCommissione = cdsCommissione;
    }

    public boolean isRilascioAutorizzazione() {

	return rilascioAutorizzazione;
    }

    public void setRilascioAutorizzazione(boolean rilascioAutorizzazione) {

	this.rilascioAutorizzazione = rilascioAutorizzazione;
    }

    public boolean isMovimentoAvvio() {

	return movimentoAvvio;
    }

    public void setMovimentoAvvio(boolean movimentoAvvio) {

	this.movimentoAvvio = movimentoAvvio;
    }

    public boolean isEseguito() {

	return eseguito;
    }

    public void setEseguito(boolean eseguito) {

	this.eseguito = eseguito;
    }
}
