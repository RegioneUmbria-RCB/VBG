package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.helper.DomandeSTCScadenzarioDTO;

public class DomandeStcFilter {

    private String ordineDataistanza;
    private boolean ordineImpostato;
    private DomandeSTCScadenzarioDTO domandeSTCScadenzarioDTO;

    public String getOrdineDataistanza() {

	return ordineDataistanza;
    }

    public void setOrdineDataistanza(String ordineDataistanza) {

	this.ordineDataistanza = ordineDataistanza;
    }

    public boolean isOrdineImpostato() {

	return ordineImpostato;
    }

    public void setOrdineImpostato(boolean ordineImpostato) {

	this.ordineImpostato = ordineImpostato;
    }

    public DomandeSTCScadenzarioDTO getDomandeSTCScadenzarioDTO() {

	return domandeSTCScadenzarioDTO;
    }

    public void setDomandeSTCScadenzarioDTO(DomandeSTCScadenzarioDTO domandeSTCScadenzarioDTO) {

	this.domandeSTCScadenzarioDTO = domandeSTCScadenzarioDTO;
    }
}
