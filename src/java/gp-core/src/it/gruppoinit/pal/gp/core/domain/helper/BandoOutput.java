package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;

public class BandoOutput {

    private Istanzedyn2dati istanzedyn2datiRif;
    private Istanzedyn2dati istanzedyn2datiOut;
    private String tipocalcolo;

    public Istanzedyn2dati getIstanzedyn2datiRif() {

	return istanzedyn2datiRif;
    }

    public void setIstanzedyn2datiRif(Istanzedyn2dati istanzedyn2datiRif) {

	this.istanzedyn2datiRif = istanzedyn2datiRif;
    }

    public Istanzedyn2dati getIstanzedyn2datiOut() {

	return istanzedyn2datiOut;
    }

    public void setIstanzedyn2datiOut(Istanzedyn2dati istanzedyn2datiOut) {

	this.istanzedyn2datiOut = istanzedyn2datiOut;
    }

    public String getTipocalcolo() {

	return tipocalcolo;
    }

    public void setTipocalcolo(String tipocalcolo) {

	this.tipocalcolo = tipocalcolo;
    }
}
