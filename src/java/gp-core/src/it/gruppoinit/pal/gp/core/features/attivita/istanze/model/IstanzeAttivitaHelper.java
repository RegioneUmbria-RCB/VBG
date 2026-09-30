package it.gruppoinit.pal.gp.core.features.attivita.istanze.model;

import java.util.Date;

public class IstanzeAttivitaHelper {

    private Integer codiceistanza;
    private String software;
    private Date data;
    private Date datavalidita;
    private Date dataprotocollo;
    private String numeroprotocollo;
    private String azione;
    private String nomeattivita;
    private Integer iattivitaordine;
    private String richiedente;
    private int fkcodcomportamento;

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public Date getDatavalidita() {

	return datavalidita;
    }

    public void setDatavalidita(Date datavalidita) {

	this.datavalidita = datavalidita;
    }

    public Date getDataprotocollo() {

	return dataprotocollo;
    }

    public void setDataprotocollo(Date dataprotocollo) {

	this.dataprotocollo = dataprotocollo;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	this.numeroprotocollo = numeroprotocollo;
    }

    public String getAzione() {

	return azione;
    }

    public void setAzione(String azione) {

	this.azione = azione;
    }

    public String getNomeattivita() {

	return nomeattivita;
    }

    public void setNomeattivita(String nomeattivita) {

	this.nomeattivita = nomeattivita;
    }

    public Integer getIattivitaordine() {

	return iattivitaordine;
    }

    public void setIattivitaordine(Integer iattivitaordine) {

	this.iattivitaordine = iattivitaordine;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public int getFkcodcomportamento() {

	return fkcodcomportamento;
    }

    public void setFkcodcomportamento(int fkcodcomportamento) {

	this.fkcodcomportamento = fkcodcomportamento;
    }

    public boolean isIstanzaRappresentativa() {

	return this.datavalidita != null;
    }
}
