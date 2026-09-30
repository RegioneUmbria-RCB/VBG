package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import it.gruppoinit.pal.gp.core.features.datidinamici.model.CampoDinamico;

public class CampoDinamicoAttivita extends CampoDinamico {

    private Integer indice;
    private Integer indiceMolteplicita;
    private String valore;
    private String valoreDecodificato;

    public Integer getIndice() {

	return indice;
    }

    public void setIndice(Integer indice) {

	this.indice = indice;
    }

    public Integer getIndiceMolteplicita() {

	return indiceMolteplicita;
    }

    public void setIndiceMolteplicita(Integer indiceMolteplicita) {

	this.indiceMolteplicita = indiceMolteplicita;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getValoreDecodificato() {

	return valoreDecodificato;
    }

    public void setValoreDecodificato(String valoreDecodificato) {

	this.valoreDecodificato = valoreDecodificato;
    }
}
