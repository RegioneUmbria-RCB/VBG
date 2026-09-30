package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio;

import java.math.BigDecimal;
import java.util.Date;

public class ValoreLivelloServizio {

    private Date inizioValiditaLivelloMercato;
    private Date fineValiditaLivelloMercato;
    private Date inizioValiditaLivelloPosteggio;
    private Date fineValiditaLivelloPosteggio;
    private String segnaposto;
    private BigDecimal tariffa;
    private BigDecimal quantita;

    public Date getInizioValiditaLivelloMercato() {

	return inizioValiditaLivelloMercato;
    }

    public void setInizioValiditaLivelloMercato(Date inizioValiditaLivelloMercato) {

	this.inizioValiditaLivelloMercato = inizioValiditaLivelloMercato;
    }

    public Date getFineValiditaLivelloMercato() {

	return fineValiditaLivelloMercato;
    }

    public void setFineValiditaLivelloMercato(Date fineValiditaLivelloMercato) {

	this.fineValiditaLivelloMercato = fineValiditaLivelloMercato;
    }

    public Date getInizioValiditaLivelloPosteggio() {

	return inizioValiditaLivelloPosteggio;
    }

    public void setInizioValiditaLivelloPosteggio(Date inizioValiditaLivelloPosteggio) {

	this.inizioValiditaLivelloPosteggio = inizioValiditaLivelloPosteggio;
    }

    public Date getFineValiditaLivelloPosteggio() {

	return fineValiditaLivelloPosteggio;
    }

    public void setFineValiditaLivelloPosteggio(Date fineValiditaLivelloPosteggio) {

	this.fineValiditaLivelloPosteggio = fineValiditaLivelloPosteggio;
    }

    public String getSegnaposto() {

	return segnaposto;
    }

    public void setSegnaposto(String segnaposto) {

	this.segnaposto = segnaposto;
    }

    public BigDecimal getTariffa() {

	return tariffa;
    }

    public void setTariffa(BigDecimal tariffa) {

	this.tariffa = tariffa;
    }

    public BigDecimal getQuantita() {

	return quantita;
    }

    public void setQuantita(BigDecimal quantita) {

	this.quantita = quantita;
    }
}
