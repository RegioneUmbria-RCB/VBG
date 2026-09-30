package it.gruppoinit.pal.gp.core.features.attivita.snapshots.model;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.features.datidinamici.model.CampoDinamico;

public class AttivitaDyn2DatiSnapshot {

    private CampoDinamico campo;
    private int indice;
    private int indiceMolteplicita;
    private String valore;
    private String valoredecodificato;

    public AttivitaDyn2DatiSnapshot(Dyn2Campi campo, int indice, int indiceMolteplicita, String valore, String valoredecodificato) {

	super();
	this.campo = new CampoDinamico(campo);
	this.indice = indice;
	this.indiceMolteplicita = indiceMolteplicita;
	this.valore = valore;
	this.valoredecodificato = valoredecodificato;
    }

    public CampoDinamico getCampo() {

	return campo;
    }

    public int getIndice() {

	return indice;
    }

    public int getIndiceMolteplicita() {

	return indiceMolteplicita;
    }

    public String getValore() {

	return valore;
    }

    public String getValoredecodificato() {

	return valoredecodificato;
    }
}
