package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagamentoInAttesaRequest", propOrder = { "idFlusso", "iuv", "codiceAvviso", "posizione", "stato", "numeroOperazione",
	"numeroDocumento", "dataInizio", "dataScadenza", "scadenzaBloccante", "importoVersamento", "causaleVersamento", "importoVersamentoConPenale",
	"rata", "note", "codiceTassonomia", "anagraficaPagatore" })
public class PagamentoInAttesaRequest {

    @XmlElement(name = "id_flusso")
    private String idFlusso;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codice_avviso")
    private String codiceAvviso;
    @XmlElement(name = "posizione")
    private String posizione;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "numero_operazione")
    private String numeroOperazione;
    @XmlElement(name = "numero_documento")
    private String numeroDocumento;
    @XmlElement(name = "data_inizio")
    private String dataInizio;
    @XmlElement(name = "data_scadenza")
    private String dataScadenza;
    @XmlElement(name = "scadenza_bloccante")
    private String scadenzaBloccante;
    @XmlElement(name = "importo_versamento")
    private String importoVersamento;
    @XmlElement(name = "causale_versamento")
    private String causaleVersamento;
    @XmlElement(name = "importo_versamento_con_penale")
    private String importoVersamentoConPenale;
    @XmlElement(name = "rata")
    private int rata;
    @XmlElement(name = "note")
    private String note;
    @XmlElement(name = "codice_tassonomia")
    private String codiceTassonomia;
    @XmlElement(name = "anagrafica_pagatore")
    private AnagraficaPagatore anagraficaPagatore;

    public String getIdFlusso() {

	return idFlusso;
    }

    public void setIdFlusso(String idFlusso) {

	this.idFlusso = idFlusso;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getPosizione() {

	return posizione;
    }

    public void setPosizione(String posizione) {

	this.posizione = posizione;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getNumeroOperazione() {

	return numeroOperazione;
    }

    public void setNumeroOperazione(String numeroOperazione) {

	this.numeroOperazione = numeroOperazione;
    }

    public String getNumeroDocumento() {

	return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {

	this.numeroDocumento = numeroDocumento;
    }

    public String getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(String dataInizio) {

	this.dataInizio = dataInizio;
    }

    public String getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getScadenzaBloccante() {

	return scadenzaBloccante;
    }

    public void setScadenzaBloccante(String scadenzaBloccante) {

	this.scadenzaBloccante = scadenzaBloccante;
    }

    public String getImportoVersamento() {

	return importoVersamento;
    }

    public void setImportoVersamento(String importoVersamento) {

	this.importoVersamento = importoVersamento;
    }

    public String getCausaleVersamento() {

	return causaleVersamento;
    }

    public void setCausaleVersamento(String causaleVersamento) {

	this.causaleVersamento = causaleVersamento;
    }

    public String getImportoVersamentoConPenale() {

	return importoVersamentoConPenale;
    }

    public void setImportoVersamentoConPenale(String importoVersamentoConPenale) {

	this.importoVersamentoConPenale = importoVersamentoConPenale;
    }

    public int getRata() {

	return rata;
    }

    public void setRata(int rata) {

	this.rata = rata;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getCodiceTassonomia() {

	return codiceTassonomia;
    }

    public void setCodiceTassonomia(String codiceTassonomia) {

	this.codiceTassonomia = codiceTassonomia;
    }

    public AnagraficaPagatore getAnagraficaPagatore() {

	return anagraficaPagatore;
    }

    public void setAnagraficaPagatore(AnagraficaPagatore anagraficaPagatore) {

	this.anagraficaPagatore = anagraficaPagatore;
    }
}
