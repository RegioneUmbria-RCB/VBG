package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pagamento", propOrder = { "stato", "iuv", "codiceAvviso", "timeStampIns", "numeroOperazione", "numeroDocumento", "timeStampInizio","timeStampUpd",
	"scadenzaBloccante", "importoVersamento", "causaleVersamento", "importoVersamentoConPenale", "rata", "qrCode", "codiceTassonomia",
	"timeStampScadenza", "progressivoPosizione", "anagraficaPagatore" })
public class Pagamento {

    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codice_avviso")
    private String codiceAvviso;
    @XmlElement(name = "time_stamp_ins")
    private String timeStampIns;
    @XmlElement(name = "numero_operazione")
    private String numeroOperazione;
    @XmlElement(name = "numero_documento")
    private String numeroDocumento;
    @XmlElement(name = "time_stamp_inizio")
    private String timeStampInizio;    
    @XmlElement(name = "time_stamp_upd")
    private String timeStampUpd;
    @XmlElement(name = "scadenza_bloccante")
    private String scadenzaBloccante;
    @XmlElement(name = "importo_versamento")
    private String importoVersamento;
    @XmlElement(name = "causale_versamento")
    private String causaleVersamento;
    @XmlElement(name = "importo_versamento_con_penale")
    private String importoVersamentoConPenale;
    @XmlElement(name = "rata")
    private Integer rata;
    @XmlElement(name = "qr_code")
    private String qrCode;
    @XmlElement(name = "codice_tassonomia")
    private String codiceTassonomia;
    @XmlElement(name = "time_stamp_scadenza")
    private String timeStampScadenza;
    @XmlElement(name = "progressivo_posizione")
    private String progressivoPosizione;
    @XmlElement(name = "anagrafica_pagatore")
    private AnagraficaPagatore anagraficaPagatore;

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
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

    public String getTimeStampIns() {

	return timeStampIns;
    }

    public void setTimeStampIns(String timeStampIns) {

	this.timeStampIns = timeStampIns;
    }        

    public String getTimeStampUpd() {
		
    return timeStampUpd;
	}

	public void setTimeStampUpd(String timeStampUpd) {
	
	this.timeStampUpd = timeStampUpd;
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

    public String getTimeStampInizio() {

	return timeStampInizio;
    }

    public void setTimeStampInizio(String timeStampInizio) {

	this.timeStampInizio = timeStampInizio;
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

    public Integer getRata() {

	return rata;
    }

    public void setRata(Integer rata) {

	this.rata = rata;
    }

    public String getQrCode() {

	return qrCode;
    }

    public void setQrCode(String qrCode) {

	this.qrCode = qrCode;
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

    public String getTimeStampScadenza() {

	return timeStampScadenza;
    }

    public void setTimeStampScadenza(String timeStampScadenza) {

	this.timeStampScadenza = timeStampScadenza;
    }

    public String getProgressivoPosizione() {

	return progressivoPosizione;
    }

    public void setProgressivoPosizione(String progressivoPosizione) {

	this.progressivoPosizione = progressivoPosizione;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
