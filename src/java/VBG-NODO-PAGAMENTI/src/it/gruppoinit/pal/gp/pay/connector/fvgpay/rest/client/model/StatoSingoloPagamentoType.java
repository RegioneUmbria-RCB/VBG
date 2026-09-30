package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
  * Ad ogni pagamento viene attribuito un identificativo univoco pagoPA e si trova in ogni momento in uno stato che riassume quanto e' gia' successo: le diverse operazioni richieste - richieste ed esecuzione di pagamento - eventuale richiesta ed esecuzione di revoca dettagliano la storia del pagamento ed i loro effetti vengono riportati nel dettaglio_pagamento 
 **/

public class StatoSingoloPagamentoType  {
  
  
  private String codiceAvvisoPagamento = null;

  
  private DettaglioPagamentoType dettaglioPagamento = null;

  
  private DettaglioRevocaType dettaglioRevoca = null;

  
  private List<FlussoPagamentoType> flussiPagamento = null;

  
  private FlussoRevocaType flussoRevoca = null;

  
 /**
   * Identificativo Univoco di Versamento (IUV)
  **/
  private String iuv = null;

  
 /**
   * Definisce i possibili esiti di un pagamento, riferiti ad un singolo iuv 
  **/
  private String statoPagamento = null;
 /**
   * Get codiceAvvisoPagamento
   * @return codiceAvvisoPagamento
  **/
  @XmlElement(name="codice_avviso_pagamento")
  public String getCodiceAvvisoPagamento() {
    return codiceAvvisoPagamento;
  }

  public void setCodiceAvvisoPagamento(String codiceAvvisoPagamento) {
    this.codiceAvvisoPagamento = codiceAvvisoPagamento;
  }

  public StatoSingoloPagamentoType codiceAvvisoPagamento(String codiceAvvisoPagamento) {
    this.codiceAvvisoPagamento = codiceAvvisoPagamento;
    return this;
  }

 /**
   * Get dettaglioPagamento
   * @return dettaglioPagamento
  **/
  @XmlElement(name="dettaglio_pagamento")
  public DettaglioPagamentoType getDettaglioPagamento() {
    return dettaglioPagamento;
  }

  public void setDettaglioPagamento(DettaglioPagamentoType dettaglioPagamento) {
    this.dettaglioPagamento = dettaglioPagamento;
  }

  public StatoSingoloPagamentoType dettaglioPagamento(DettaglioPagamentoType dettaglioPagamento) {
    this.dettaglioPagamento = dettaglioPagamento;
    return this;
  }

 /**
   * Get dettaglioRevoca
   * @return dettaglioRevoca
  **/
  @XmlElement(name="dettaglio_revoca")
  public DettaglioRevocaType getDettaglioRevoca() {
    return dettaglioRevoca;
  }

  public void setDettaglioRevoca(DettaglioRevocaType dettaglioRevoca) {
    this.dettaglioRevoca = dettaglioRevoca;
  }

  public StatoSingoloPagamentoType dettaglioRevoca(DettaglioRevocaType dettaglioRevoca) {
    this.dettaglioRevoca = dettaglioRevoca;
    return this;
  }

 /**
   * Get flussiPagamento
   * @return flussiPagamento
  **/
  @XmlElement(name="flussi_pagamento")
  public List<FlussoPagamentoType> getFlussiPagamento() {
    return flussiPagamento;
  }

  public void setFlussiPagamento(List<FlussoPagamentoType> flussiPagamento) {
    this.flussiPagamento = flussiPagamento;
  }

  public StatoSingoloPagamentoType flussiPagamento(List<FlussoPagamentoType> flussiPagamento) {
    this.flussiPagamento = flussiPagamento;
    return this;
  }

  public StatoSingoloPagamentoType addFlussiPagamentoItem(FlussoPagamentoType flussiPagamentoItem) {
    this.flussiPagamento.add(flussiPagamentoItem);
    return this;
  }

 /**
   * Get flussoRevoca
   * @return flussoRevoca
  **/
  @XmlElement(name="flusso_revoca")
  public FlussoRevocaType getFlussoRevoca() {
    return flussoRevoca;
  }

  public void setFlussoRevoca(FlussoRevocaType flussoRevoca) {
    this.flussoRevoca = flussoRevoca;
  }

  public StatoSingoloPagamentoType flussoRevoca(FlussoRevocaType flussoRevoca) {
    this.flussoRevoca = flussoRevoca;
    return this;
  }

 /**
   * Identificativo Univoco di Versamento (IUV)
   * @return iuv
  **/
  @XmlElement(name="iuv")
  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public StatoSingoloPagamentoType iuv(String iuv) {
    this.iuv = iuv;
    return this;
  }

 /**
   * Definisce i possibili esiti di un pagamento, riferiti ad un singolo iuv 
   * @return statoPagamento
  **/
  @XmlElement(name="stato_pagamento")
  public String getStatoPagamento() {
    return statoPagamento;
  }

  public void setStatoPagamento(String statoPagamento) {
    this.statoPagamento = statoPagamento;
  }

  public StatoSingoloPagamentoType statoPagamento(String statoPagamento) {
    this.statoPagamento = statoPagamento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoSingoloPagamentoType {\n");
    
    sb.append("    codiceAvvisoPagamento: ").append(toIndentedString(codiceAvvisoPagamento)).append("\n");
    sb.append("    dettaglioPagamento: ").append(toIndentedString(dettaglioPagamento)).append("\n");
    sb.append("    dettaglioRevoca: ").append(toIndentedString(dettaglioRevoca)).append("\n");
    sb.append("    flussiPagamento: ").append(toIndentedString(flussiPagamento)).append("\n");
    sb.append("    flussoRevoca: ").append(toIndentedString(flussoRevoca)).append("\n");
    sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
    sb.append("    statoPagamento: ").append(toIndentedString(statoPagamento)).append("\n");
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

