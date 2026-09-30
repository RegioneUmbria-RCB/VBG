package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
  * Contiene il dettaglio di un singolo pagamento presente nell'Archivio Pagamenti in Attesa: per ogni pagamento e' necessario specificare da 1 a 5 voci di dettaglio, ognuna della quali puo' descrivere - il pagamento di un debito nei confronti dell'Ente Creditore - il pagamento di una marca da bollo digitale Sono i dati con cui vengono generate le RPT, che per? possono contenere anche dati specifici della singola RPT raccolti a run-time in funzione delle scelte operate dal versante 
 **/

public class DettaglioPagamentoType  {
  
  
 /**
   * Data effettiva del pagamento - secondo il formato ISO 8601 (YYYY-MM-DD) 
  **/
  private Date dataPagamento = null;

  
 /**
   * contiene i dati in formato xml per la riconciliazione_ascot 
  **/
  private byte[] datiContabili = null;

  
 /**
   * Elenco delle singole voci di pagamento (da 1 a 5) 
  **/
  private List<DettaglioVocePagamentoType> dettaglioVociPagamento = new ArrayList<DettaglioVocePagamentoType>();

  
  private ImportoType importoTotale = null;

  
 /**
   * Data entro cui effettuare il pagamento - secondo il formato ISO 8601 (YYYY-MM-DD) 
  **/
  private Date scadenza = null;

  
 /**
   * Data di scadenza avviso - secondo il formato ISO 8601 (YYYY-MM-DD) 
  **/
  private Date scadenzaAvviso = null;
 /**
   * Data effettiva del pagamento - secondo il formato ISO 8601 (YYYY-MM-DD) 
   * @return dataPagamento
  **/
  @XmlElement(name="data_pagamento")
  public Date getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public DettaglioPagamentoType dataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
    return this;
  }

 /**
   * contiene i dati in formato xml per la riconciliazione_ascot 
   * @return datiContabili
  **/
  @XmlElement(name="dati_contabili")
  public byte[] getDatiContabili() {
    return datiContabili;
  }

  public void setDatiContabili(byte[] datiContabili) {
    this.datiContabili = datiContabili;
  }

  public DettaglioPagamentoType datiContabili(byte[] datiContabili) {
    this.datiContabili = datiContabili;
    return this;
  }

 /**
   * Elenco delle singole voci di pagamento (da 1 a 5) 
   * @return dettaglioVociPagamento
  **/
  @XmlElement(name="dettaglio_voci_pagamento")
  public List<DettaglioVocePagamentoType> getDettaglioVociPagamento() {
    return dettaglioVociPagamento;
  }

  public void setDettaglioVociPagamento(List<DettaglioVocePagamentoType> dettaglioVociPagamento) {
    this.dettaglioVociPagamento = dettaglioVociPagamento;
  }

  public DettaglioPagamentoType dettaglioVociPagamento(List<DettaglioVocePagamentoType> dettaglioVociPagamento) {
    this.dettaglioVociPagamento = dettaglioVociPagamento;
    return this;
  }

  public DettaglioPagamentoType addDettaglioVociPagamentoItem(DettaglioVocePagamentoType dettaglioVociPagamentoItem) {
    this.dettaglioVociPagamento.add(dettaglioVociPagamentoItem);
    return this;
  }

 /**
   * Get importoTotale
   * @return importoTotale
  **/
  @XmlElement(name="importo_totale")
  public ImportoType getImportoTotale() {
    return importoTotale;
  }

  public void setImportoTotale(ImportoType importoTotale) {
    this.importoTotale = importoTotale;
  }

  public DettaglioPagamentoType importoTotale(ImportoType importoTotale) {
    this.importoTotale = importoTotale;
    return this;
  }

 /**
   * Data entro cui effettuare il pagamento - secondo il formato ISO 8601 (YYYY-MM-DD) 
   * @return scadenza
  **/
  @XmlElement(name="scadenza")
  public Date getScadenza() {
    return scadenza;
  }

  public void setScadenza(Date scadenza) {
    this.scadenza = scadenza;
  }

  public DettaglioPagamentoType scadenza(Date scadenza) {
    this.scadenza = scadenza;
    return this;
  }

 /**
   * Data di scadenza avviso - secondo il formato ISO 8601 (YYYY-MM-DD) 
   * @return scadenzaAvviso
  **/
  @XmlElement(name="scadenza_avviso")
  public Date getScadenzaAvviso() {
    return scadenzaAvviso;
  }

  public void setScadenzaAvviso(Date scadenzaAvviso) {
    this.scadenzaAvviso = scadenzaAvviso;
  }

  public DettaglioPagamentoType scadenzaAvviso(Date scadenzaAvviso) {
    this.scadenzaAvviso = scadenzaAvviso;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DettaglioPagamentoType {\n");
    
    sb.append("    dataPagamento: ").append(toIndentedString(dataPagamento)).append("\n");
    sb.append("    datiContabili: ").append(toIndentedString(datiContabili)).append("\n");
    sb.append("    dettaglioVociPagamento: ").append(toIndentedString(dettaglioVociPagamento)).append("\n");
    sb.append("    importoTotale: ").append(toIndentedString(importoTotale)).append("\n");
    sb.append("    scadenza: ").append(toIndentedString(scadenza)).append("\n");
    sb.append("    scadenzaAvviso: ").append(toIndentedString(scadenzaAvviso)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

