package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Contiene il messaggio tecnico Ricevuta Telematica restituito da pagoPA, da cui vengono estratti gli elementi utili a
 * definire lo stato di pagamento\"
 **/
public class RicevutaPagamentoType {

    /**
     * Data e ora di produzione della RT
     **/
    private Date dataOraMessaggioRicevuta = null;
    /**
     * Dettaglio delle singole voci di una ricevuta di pagamento (da 1 a 5)
     **/
    private List<VoceRtType> dettaglioVociPagamento = new ArrayList<VoceRtType>();

    /**
     * <pre>
     *  
    0. Pagamento eseguito 
    1. Pagamento non eseguito 
    2. Pagamento parzialmente eseguito 
    3. Decorrenza termini 
    4. Decorrenza termini parziale
     * </pre>
     * 
     * @author riccardob
     *
     */
    @XmlType(name = "EsitoPagamentoEnum")
    @XmlEnum(String.class)
    public enum EsitoPagamentoEnum {

	@XmlEnumValue("0")
	_0(String.valueOf("0")),
	@XmlEnumValue("1")
	_1(String.valueOf("1")),
	@XmlEnumValue("2")
	_2(String.valueOf("2")),
	@XmlEnumValue("3")
	_3(String.valueOf("3")),
	@XmlEnumValue("4")
	_4(String.valueOf("4"));

	private String value;

	EsitoPagamentoEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static EsitoPagamentoEnum fromValue(String v) {

	    for (EsitoPagamentoEnum b : EsitoPagamentoEnum.values()) {
		if (String.valueOf(b.value).equals(v)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    private EsitoPagamentoEnum esitoPagamento = null;
    /**
     * Identificativo univoco nella data con cui viene prodotta la RT
     **/
    private String identificativoMessaggioRicevuta = null;
    private ImportoType importoTotalePagato = null;
    private IstitutoAttestanteType istitutoAttestante = null;
    private byte[] xmlRt = null;

    /**
     * Data e ora di produzione della RT
     * 
     * @return dataOraMessaggioRicevuta
     **/
    @XmlElement(name = "data_ora_messaggio_ricevuta")
    public Date getDataOraMessaggioRicevuta() {

	return dataOraMessaggioRicevuta;
    }

    public void setDataOraMessaggioRicevuta(Date dataOraMessaggioRicevuta) {

	this.dataOraMessaggioRicevuta = dataOraMessaggioRicevuta;
    }

    public RicevutaPagamentoType dataOraMessaggioRicevuta(Date dataOraMessaggioRicevuta) {

	this.dataOraMessaggioRicevuta = dataOraMessaggioRicevuta;
	return this;
    }

    /**
     * Dettaglio delle singole voci di una ricevuta di pagamento (da 1 a 5)
     * 
     * @return dettaglioVociPagamento
     **/
    @XmlElement(name = "dettaglio_voci_pagamento")
    public List<VoceRtType> getDettaglioVociPagamento() {

	return dettaglioVociPagamento;
    }

    public void setDettaglioVociPagamento(List<VoceRtType> dettaglioVociPagamento) {

	this.dettaglioVociPagamento = dettaglioVociPagamento;
    }

    public RicevutaPagamentoType dettaglioVociPagamento(List<VoceRtType> dettaglioVociPagamento) {

	this.dettaglioVociPagamento = dettaglioVociPagamento;
	return this;
    }

    public RicevutaPagamentoType addDettaglioVociPagamentoItem(VoceRtType dettaglioVociPagamentoItem) {

	this.dettaglioVociPagamento.add(dettaglioVociPagamentoItem);
	return this;
    }

    /**
     * Get esitoPagamento
     * 
     * @return esitoPagamento
     **/
    @XmlElement(name = "esito_pagamento")
    public String getEsitoPagamento() {

	if (esitoPagamento == null) {
	    return null;
	}
	return esitoPagamento.value();
    }

    public void setEsitoPagamento(String esitoPagamento) {

	this.esitoPagamento = EsitoPagamentoEnum.fromValue(esitoPagamento);
    }

    public void setEsitoPagamento(EsitoPagamentoEnum esitoPagamento) {

	this.esitoPagamento = esitoPagamento;
    }

    public RicevutaPagamentoType esitoPagamento(EsitoPagamentoEnum esitoPagamento) {

	this.esitoPagamento = esitoPagamento;
	return this;
    }

    /**
     * Identificativo univoco nella data con cui viene prodotta la RT
     * 
     * @return identificativoMessaggioRicevuta
     **/
    @XmlElement(name = "identificativo_messaggio_ricevuta")
    public String getIdentificativoMessaggioRicevuta() {

	return identificativoMessaggioRicevuta;
    }

    public void setIdentificativoMessaggioRicevuta(String identificativoMessaggioRicevuta) {

	this.identificativoMessaggioRicevuta = identificativoMessaggioRicevuta;
    }

    public RicevutaPagamentoType identificativoMessaggioRicevuta(String identificativoMessaggioRicevuta) {

	this.identificativoMessaggioRicevuta = identificativoMessaggioRicevuta;
	return this;
    }

    /**
     * Get importoTotalePagato
     * 
     * @return importoTotalePagato
     **/
    @XmlElement(name = "importo_totale_pagato")
    public ImportoType getImportoTotalePagato() {

	return importoTotalePagato;
    }

    public void setImportoTotalePagato(ImportoType importoTotalePagato) {

	this.importoTotalePagato = importoTotalePagato;
    }

    public RicevutaPagamentoType importoTotalePagato(ImportoType importoTotalePagato) {

	this.importoTotalePagato = importoTotalePagato;
	return this;
    }

    /**
     * Get istitutoAttestante
     * 
     * @return istitutoAttestante
     **/
    @XmlElement(name = "istituto_attestante")
    public IstitutoAttestanteType getIstitutoAttestante() {

	return istitutoAttestante;
    }

    public void setIstitutoAttestante(IstitutoAttestanteType istitutoAttestante) {

	this.istitutoAttestante = istitutoAttestante;
    }

    public RicevutaPagamentoType istitutoAttestante(IstitutoAttestanteType istitutoAttestante) {

	this.istitutoAttestante = istitutoAttestante;
	return this;
    }

    /**
     * Get xmlRt
     * 
     * @return xmlRt
     **/
    @XmlElement(name = "xml_rt")
    public byte[] getXmlRt() {

	return xmlRt;
    }

    public void setXmlRt(byte[] xmlRt) {

	this.xmlRt = xmlRt;
    }

    public RicevutaPagamentoType xmlRt(byte[] xmlRt) {

	this.xmlRt = xmlRt;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class RicevutaPagamentoType {\n");
	sb.append("    dataOraMessaggioRicevuta: ").append(toIndentedString(dataOraMessaggioRicevuta)).append("\n");
	sb.append("    dettaglioVociPagamento: ").append(toIndentedString(dettaglioVociPagamento)).append("\n");
	sb.append("    esitoPagamento: ").append(toIndentedString(esitoPagamento)).append("\n");
	sb.append("    identificativoMessaggioRicevuta: ").append(toIndentedString(identificativoMessaggioRicevuta)).append("\n");
	sb.append("    importoTotalePagato: ").append(toIndentedString(importoTotalePagato)).append("\n");
	sb.append("    istitutoAttestante: ").append(toIndentedString(istitutoAttestante)).append("\n");
	sb.append("    xmlRt: ").append(toIndentedString(xmlRt)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
