package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Contiene il messaggio tecnico pagoPA di Richiesta Pagamento Telematico RPT Dalla RPT vengono estratti i dati che
 * possono variare da una RPT all'altra per lo stesso pagamento, in quanto dipendenti dal soggetto versante
 * (eventualmente diverso dal debitore) e dalle sue scelte (PSP e modalita' di pagamento)
 **/
public class RichiestaPagamentoType {

    /**
     * Bank Identifier Code della banca di addebito, definito secondo lo standard ISO 9362
     **/
    private String bicAddebito = null;
    /**
     * identificatore univoco del contesto di pagamento (generato dal PSP o dall'ente creditore)
     **/
    private String codiceContestoPagamento = null;
    /**
     * data di pagamento
     **/
    private Date dataEsecuzionePagamento = null;
    /**
     * Data e ora di attivazione della RPT
     **/
    private Date dataOraMessaggioRichiesta = null;
    /**
     * Dettaglio delle singole voci di una ricevuta di pagamento (da 1 a 5)
     **/
    private List<VoceRptType> dettaglioVociPagamento = new ArrayList<VoceRptType>();
    /**
     * International Bank Account Number del conto da addebitare, definito secondo lo standard ISO 13616 obbligatorio se
     * tipo_versamento = AD
     **/
    private String ibanAddebito = null;
    /**
     * Identificativo univoco nella data con cui viene attivata la RPT
     **/
    private String identificativoMessaggioRichiesta = null;
    /**
     * Identificativo Univoco di Versamento (IUV)
     **/
    private String identificativoUnivocoVersamento = null;

    @XmlType(name = "TipoVersamentoEnum")
    @XmlEnum(String.class)
    public enum TipoVersamentoEnum {

	@XmlEnumValue("BBT")
	BBT(String.valueOf("BBT")),
	@XmlEnumValue("BP")
	BP(String.valueOf("BP")),
	@XmlEnumValue("AD")
	AD(String.valueOf("AD")),
	@XmlEnumValue("CP")
	CP(String.valueOf("CP")),
	@XmlEnumValue("PO")
	PO(String.valueOf("PO")),
	@XmlEnumValue("OBEP")
	OBEP(String.valueOf("OBEP"));

	private String value;

	TipoVersamentoEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}

	@Override
	public String toString() {

	    return String.valueOf(value);
	}

	public static TipoVersamentoEnum fromValue(String v) {

	    for (TipoVersamentoEnum b : TipoVersamentoEnum.values()) {
		if (String.valueOf(b.value).equals(v)) {
		    return b;
		}
	    }
	    return null;
	}
    }

    private TipoVersamentoEnum tipoVersamento = null;
    private SoggettoPagatoreType versante = null;
    private byte[] xmlRpt = null;

    /**
     * Bank Identifier Code della banca di addebito, definito secondo lo standard ISO 9362
     * 
     * @return bicAddebito
     **/
    @XmlElement(name = "bic_addebito")
    public String getBicAddebito() {

	return bicAddebito;
    }

    public void setBicAddebito(String bicAddebito) {

	this.bicAddebito = bicAddebito;
    }

    public RichiestaPagamentoType bicAddebito(String bicAddebito) {

	this.bicAddebito = bicAddebito;
	return this;
    }

    /**
     * identificatore univoco del contesto di pagamento (generato dal PSP o dall&#39;ente creditore)
     * 
     * @return codiceContestoPagamento
     **/
    @XmlElement(name = "codice_contesto_pagamento")
    public String getCodiceContestoPagamento() {

	return codiceContestoPagamento;
    }

    public void setCodiceContestoPagamento(String codiceContestoPagamento) {

	this.codiceContestoPagamento = codiceContestoPagamento;
    }

    public RichiestaPagamentoType codiceContestoPagamento(String codiceContestoPagamento) {

	this.codiceContestoPagamento = codiceContestoPagamento;
	return this;
    }

    /**
     * data di pagamento
     * 
     * @return dataEsecuzionePagamento
     **/
    @XmlElement(name = "data_esecuzione_pagamento")
    public Date getDataEsecuzionePagamento() {

	return dataEsecuzionePagamento;
    }

    public void setDataEsecuzionePagamento(Date dataEsecuzionePagamento) {

	this.dataEsecuzionePagamento = dataEsecuzionePagamento;
    }

    public RichiestaPagamentoType dataEsecuzionePagamento(Date dataEsecuzionePagamento) {

	this.dataEsecuzionePagamento = dataEsecuzionePagamento;
	return this;
    }

    /**
     * Data e ora di attivazione della RPT
     * 
     * @return dataOraMessaggioRichiesta
     **/
    @XmlElement(name = "data_ora_messaggio_richiesta")
    public Date getDataOraMessaggioRichiesta() {

	return dataOraMessaggioRichiesta;
    }

    public void setDataOraMessaggioRichiesta(Date dataOraMessaggioRichiesta) {

	this.dataOraMessaggioRichiesta = dataOraMessaggioRichiesta;
    }

    public RichiestaPagamentoType dataOraMessaggioRichiesta(Date dataOraMessaggioRichiesta) {

	this.dataOraMessaggioRichiesta = dataOraMessaggioRichiesta;
	return this;
    }

    /**
     * Dettaglio delle singole voci di una ricevuta di pagamento (da 1 a 5)
     * 
     * @return dettaglioVociPagamento
     **/
    @XmlElement(name = "dettaglio_voci_pagamento")
    public List<VoceRptType> getDettaglioVociPagamento() {

	return dettaglioVociPagamento;
    }

    public void setDettaglioVociPagamento(List<VoceRptType> dettaglioVociPagamento) {

	this.dettaglioVociPagamento = dettaglioVociPagamento;
    }

    public RichiestaPagamentoType dettaglioVociPagamento(List<VoceRptType> dettaglioVociPagamento) {

	this.dettaglioVociPagamento = dettaglioVociPagamento;
	return this;
    }

    public RichiestaPagamentoType addDettaglioVociPagamentoItem(VoceRptType dettaglioVociPagamentoItem) {

	this.dettaglioVociPagamento.add(dettaglioVociPagamentoItem);
	return this;
    }

    /**
     * International Bank Account Number del conto da addebitare, definito secondo lo standard ISO 13616 obbligatorio se
     * tipo_versamento &#x3D; AD
     * 
     * @return ibanAddebito
     **/
    @XmlElement(name = "iban_addebito")
    public String getIbanAddebito() {

	return ibanAddebito;
    }

    public void setIbanAddebito(String ibanAddebito) {

	this.ibanAddebito = ibanAddebito;
    }

    public RichiestaPagamentoType ibanAddebito(String ibanAddebito) {

	this.ibanAddebito = ibanAddebito;
	return this;
    }

    /**
     * Identificativo univoco nella data con cui viene attivata la RPT
     * 
     * @return identificativoMessaggioRichiesta
     **/
    @XmlElement(name = "identificativo_messaggio_richiesta")
    public String getIdentificativoMessaggioRichiesta() {

	return identificativoMessaggioRichiesta;
    }

    public void setIdentificativoMessaggioRichiesta(String identificativoMessaggioRichiesta) {

	this.identificativoMessaggioRichiesta = identificativoMessaggioRichiesta;
    }

    public RichiestaPagamentoType identificativoMessaggioRichiesta(String identificativoMessaggioRichiesta) {

	this.identificativoMessaggioRichiesta = identificativoMessaggioRichiesta;
	return this;
    }

    /**
     * Identificativo Univoco di Versamento (IUV)
     * 
     * @return identificativoUnivocoVersamento
     **/
    @XmlElement(name = "identificativo_univoco_versamento")
    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    public RichiestaPagamentoType identificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	return this;
    }

    /**
     * Get tipoVersamento
     * 
     * @return tipoVersamento
     **/
    @XmlElement(name = "tipo_versamento")
    public String getTipoVersamento() {

	if (tipoVersamento == null) {
	    return null;
	}
	return tipoVersamento.value();
    }

    public void setTipoVersamento(String tipoVersamento) {

	this.tipoVersamento = TipoVersamentoEnum.fromValue(tipoVersamento);
    }

    public void setTipoVersamento(TipoVersamentoEnum tipoVersamento) {

	this.tipoVersamento = tipoVersamento;
    }

    public RichiestaPagamentoType tipoVersamento(TipoVersamentoEnum tipoVersamento) {

	this.tipoVersamento = tipoVersamento;
	return this;
    }

    /**
     * Get versante
     * 
     * @return versante
     **/
    @XmlElement(name = "versante")
    public SoggettoPagatoreType getVersante() {

	return versante;
    }

    public void setVersante(SoggettoPagatoreType versante) {

	this.versante = versante;
    }

    public RichiestaPagamentoType versante(SoggettoPagatoreType versante) {

	this.versante = versante;
	return this;
    }

    /**
     * Get xmlRpt
     * 
     * @return xmlRpt
     **/
    @XmlElement(name = "xml_rpt")
    public byte[] getXmlRpt() {

	return xmlRpt;
    }

    public void setXmlRpt(byte[] xmlRpt) {

	this.xmlRpt = xmlRpt;
    }

    public RichiestaPagamentoType xmlRpt(byte[] xmlRpt) {

	this.xmlRpt = xmlRpt;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class RichiestaPagamentoType {\n");
	sb.append("    bicAddebito: ").append(toIndentedString(bicAddebito)).append("\n");
	sb.append("    codiceContestoPagamento: ").append(toIndentedString(codiceContestoPagamento)).append("\n");
	sb.append("    dataEsecuzionePagamento: ").append(toIndentedString(dataEsecuzionePagamento)).append("\n");
	sb.append("    dataOraMessaggioRichiesta: ").append(toIndentedString(dataOraMessaggioRichiesta)).append("\n");
	sb.append("    dettaglioVociPagamento: ").append(toIndentedString(dettaglioVociPagamento)).append("\n");
	sb.append("    ibanAddebito: ").append(toIndentedString(ibanAddebito)).append("\n");
	sb.append("    identificativoMessaggioRichiesta: ").append(toIndentedString(identificativoMessaggioRichiesta)).append("\n");
	sb.append("    identificativoUnivocoVersamento: ").append(toIndentedString(identificativoUnivocoVersamento)).append("\n");
	sb.append("    tipoVersamento: ").append(toIndentedString(tipoVersamento)).append("\n");
	sb.append("    versante: ").append(toIndentedString(versante)).append("\n");
	sb.append("    xmlRpt: ").append(toIndentedString(xmlRpt)).append("\n");
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
