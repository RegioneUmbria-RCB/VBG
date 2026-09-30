package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.Date;

public class CdsConvocazioniBean {

    private Date dataconvocazione;
    private String oraconvocazione;
    private Boolean effettiva;

    public Date getDataconvocazione() {

	return dataconvocazione;
    }

    public void setDataconvocazione(Date dataconvocazione) {

	this.dataconvocazione = dataconvocazione;
    }

    public String getOraconvocazione() {

	return oraconvocazione;
    }

    public void setOraconvocazione(String oraconvocazione) {

	this.oraconvocazione = oraconvocazione;
    }

    public Boolean getEffettiva() {

	return effettiva;
    }

    public void setEffettiva(Boolean effettiva) {

	this.effettiva = effettiva;
    }
}
