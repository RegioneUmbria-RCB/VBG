package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

public class AutorizzazioniAttivitaHelper {

    private Integer codiceIstanza;
    private Integer codiceAutorizzazione;
    private String numeroIstanza;
    private String numeroAutorizzazione;
    private Date dataAutorizzazione;
    private String comune;
    private String registroAutorizzazione;
    private boolean stato;
    private Date dataCessazione;
    private boolean subentrata;

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public String getNumeroAutorizzazione() {

	return numeroAutorizzazione;
    }

    public void setNumeroAutorizzazione(String numeroAutorizzazione) {

	this.numeroAutorizzazione = numeroAutorizzazione;
    }

    public Date getDataAutorizzazione() {

	return dataAutorizzazione;
    }

    public void setDataAutorizzazione(Date dataAutorizzazione) {

	this.dataAutorizzazione = dataAutorizzazione;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getRegistroAutorizzazione() {

	return registroAutorizzazione;
    }

    public void setRegistroAutorizzazione(String registroAutorizzazione) {

	this.registroAutorizzazione = registroAutorizzazione;
    }

    public boolean isStato() {

	return stato;
    }

    public void setStato(boolean stato) {

	this.stato = stato;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }

    public boolean isSubentrata() {

	return subentrata;
    }

    public void setSubentrata(boolean subentrata) {

	this.subentrata = subentrata;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceAutorizzazione() {

	return codiceAutorizzazione;
    }

    public void setCodiceAutorizzazione(Integer codiceAutorizzazione) {

	this.codiceAutorizzazione = codiceAutorizzazione;
    }
}
