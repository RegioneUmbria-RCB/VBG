package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DettaglioRichiestaPagamentoImmediatoType {

    private Boolean autenticazione = null;
    /**
     * contiene il dettaglio della richiesta per il pagamento immediato
     **/
    private byte[] datiContabili = null;
    private String descrizionePagamento = null;
    private List<DettaglioVocePagamentoImmediatoType> dettaglioVociPagamento = null;
    private ImportoType importoTotale = null;
    private String urlAbbandono = null;
    private String urlKo = null;
    private String urlOk = null;

    /**
     * Get autenticazione
     * 
     * @return autenticazione
     **/
    @XmlElement(name = "autenticazione")
    public Boolean isAutenticazione() {

	return autenticazione;
    }

    public void setAutenticazione(Boolean autenticazione) {

	this.autenticazione = autenticazione;
    }

    public DettaglioRichiestaPagamentoImmediatoType autenticazione(Boolean autenticazione) {

	this.autenticazione = autenticazione;
	return this;
    }

    /**
     * contiene il dettaglio della richiesta per il pagamento immediato
     * 
     * @return datiContabili
     **/
    @XmlElement(name = "dati_contabili")
    public byte[] getDatiContabili() {

	return datiContabili;
    }

    public void setDatiContabili(byte[] datiContabili) {

	this.datiContabili = datiContabili;
    }

    public DettaglioRichiestaPagamentoImmediatoType datiContabili(byte[] datiContabili) {

	this.datiContabili = datiContabili;
	return this;
    }

    /**
     * Get descrizionePagamento
     * 
     * @return descrizionePagamento
     **/
    @XmlElement(name = "descrizione_pagamento")
    public String getDescrizionePagamento() {

	return descrizionePagamento;
    }

    public void setDescrizionePagamento(String descrizionePagamento) {

	this.descrizionePagamento = descrizionePagamento;
    }

    public DettaglioRichiestaPagamentoImmediatoType descrizionePagamento(String descrizionePagamento) {

	this.descrizionePagamento = descrizionePagamento;
	return this;
    }

    /**
     * Get dettaglioVociPagamento
     * 
     * @return dettaglioVociPagamento
     **/
    @XmlElement(name = "dettaglio_voci_pagamento")
    public List<DettaglioVocePagamentoImmediatoType> getDettaglioVociPagamento() {

	return dettaglioVociPagamento;
    }

    public void setDettaglioVociPagamento(List<DettaglioVocePagamentoImmediatoType> dettaglioVociPagamento) {

	this.dettaglioVociPagamento = dettaglioVociPagamento;
    }

    public DettaglioRichiestaPagamentoImmediatoType dettaglioVociPagamento(List<DettaglioVocePagamentoImmediatoType> dettaglioVociPagamento) {

	this.dettaglioVociPagamento = dettaglioVociPagamento;
	return this;
    }

    public DettaglioRichiestaPagamentoImmediatoType addDettaglioVociPagamentoItem(DettaglioVocePagamentoImmediatoType dettaglioVociPagamentoItem) {

	this.dettaglioVociPagamento.add(dettaglioVociPagamentoItem);
	return this;
    }

    /**
     * Get importoTotale
     * 
     * @return importoTotale
     **/
    @XmlElement(name = "importo_totale")
    public ImportoType getImportoTotale() {

	return importoTotale;
    }

    public void setImportoTotale(ImportoType importoTotale) {

	this.importoTotale = importoTotale;
    }

    public DettaglioRichiestaPagamentoImmediatoType importoTotale(ImportoType importoTotale) {

	this.importoTotale = importoTotale;
	return this;
    }

    /**
     * Get urlAbbandono
     * 
     * @return urlAbbandono
     **/
    @XmlElement(name = "url_abbandono")
    public String getUrlAbbandono() {

	return urlAbbandono;
    }

    public void setUrlAbbandono(String urlAbbandono) {

	this.urlAbbandono = urlAbbandono;
    }

    public DettaglioRichiestaPagamentoImmediatoType urlAbbandono(String urlAbbandono) {

	this.urlAbbandono = urlAbbandono;
	return this;
    }

    /**
     * Get urlKo
     * 
     * @return urlKo
     **/
    @XmlElement(name = "url_ko")
    public String getUrlKo() {

	return urlKo;
    }

    public void setUrlKo(String urlKo) {

	this.urlKo = urlKo;
    }

    public DettaglioRichiestaPagamentoImmediatoType urlKo(String urlKo) {

	this.urlKo = urlKo;
	return this;
    }

    /**
     * Get urlOk
     * 
     * @return urlOk
     **/
    @XmlElement(name = "url_ok")
    public String getUrlOk() {

	return urlOk;
    }

    public void setUrlOk(String urlOk) {

	this.urlOk = urlOk;
    }

    public DettaglioRichiestaPagamentoImmediatoType urlOk(String urlOk) {

	this.urlOk = urlOk;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class DettaglioRichiestaPagamentoImmediatoType {\n");
	sb.append("    autenticazione: ").append(toIndentedString(autenticazione)).append("\n");
	sb.append("    datiContabili: ").append(toIndentedString(datiContabili)).append("\n");
	sb.append("    descrizionePagamento: ").append(toIndentedString(descrizionePagamento)).append("\n");
	sb.append("    dettaglioVociPagamento: ").append(toIndentedString(dettaglioVociPagamento)).append("\n");
	sb.append("    importoTotale: ").append(toIndentedString(importoTotale)).append("\n");
	sb.append("    urlAbbandono: ").append(toIndentedString(urlAbbandono)).append("\n");
	sb.append("    urlKo: ").append(toIndentedString(urlKo)).append("\n");
	sb.append("    urlOk: ").append(toIndentedString(urlOk)).append("\n");
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
