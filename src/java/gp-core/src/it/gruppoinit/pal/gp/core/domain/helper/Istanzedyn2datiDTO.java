package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;

public class Istanzedyn2datiDTO {

    private Istanzedyn2datiId id;
    private String valore;
    private String valoredecodificato;
    private Integer molteplicita;

    public Istanzedyn2datiId getId() {

	return id;
    }

    public void setId(Istanzedyn2datiId id) {

	this.id = id;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getValoredecodificato() {

	return valoredecodificato;
    }

    public void setValoredecodificato(String valoredecodificato) {

	this.valoredecodificato = valoredecodificato;
    }

    public Integer getMolteplicita() {

	return molteplicita;
    }

    public void setMolteplicita(Integer molteplicita) {

	this.molteplicita = molteplicita;
    }
}
