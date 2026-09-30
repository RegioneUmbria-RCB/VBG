package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metodi {

    @JsonProperty("inserimento")
    private Boolean inserimento;
    @JsonProperty("modifica")
    private Boolean modifica;
    @JsonProperty("elenco")
    private Boolean elenco;

    public Metodi() {

	this.inserimento = false;
	this.modifica = false;
	this.elenco = false;
    }

    public Boolean getInserimento() {

	return inserimento;
    }

    public void setInserimento(Boolean inserimento) {

	this.inserimento = inserimento;
    }

    public Boolean getModifica() {

	return modifica;
    }

    public void setModifica(Boolean modifica) {

	this.modifica = modifica;
    }

    public Boolean getElenco() {

	return elenco;
    }

    public void setElenco(Boolean elenco) {

	this.elenco = elenco;
    }
}
