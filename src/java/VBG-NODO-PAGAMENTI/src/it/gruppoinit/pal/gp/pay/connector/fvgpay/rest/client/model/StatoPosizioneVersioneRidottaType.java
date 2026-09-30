package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Ad ogni pagamento viene attribuito un identificativo univoco pagoPA e si trova in ogni momento in uno stato che riassume quanto e' gia' successo: dettagliano la storia del pagamento ed i loro effetti vengono riportati nel dettaglio_pagamento 
 **/

public class StatoPosizioneVersioneRidottaType  {
  
  
  private String codiceAvvisoPagamento = null;

  
  private DettaglioPagamentoVersioneRidottaType dettaglioPagamento = null;

  
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

  public StatoPosizioneVersioneRidottaType codiceAvvisoPagamento(String codiceAvvisoPagamento) {
    this.codiceAvvisoPagamento = codiceAvvisoPagamento;
    return this;
  }

 /**
   * Get dettaglioPagamento
   * @return dettaglioPagamento
  **/
  @XmlElement(name="dettaglio_pagamento")
  public DettaglioPagamentoVersioneRidottaType getDettaglioPagamento() {
    return dettaglioPagamento;
  }

  public void setDettaglioPagamento(DettaglioPagamentoVersioneRidottaType dettaglioPagamento) {
    this.dettaglioPagamento = dettaglioPagamento;
  }

  public StatoPosizioneVersioneRidottaType dettaglioPagamento(DettaglioPagamentoVersioneRidottaType dettaglioPagamento) {
    this.dettaglioPagamento = dettaglioPagamento;
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

  public StatoPosizioneVersioneRidottaType iuv(String iuv) {
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

  public StatoPosizioneVersioneRidottaType statoPagamento(String statoPagamento) {
    this.statoPagamento = statoPagamento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoPosizioneVersioneRidottaType {\n");
    
    sb.append("    codiceAvvisoPagamento: ").append(toIndentedString(codiceAvvisoPagamento)).append("\n");
    sb.append("    dettaglioPagamento: ").append(toIndentedString(dettaglioPagamento)).append("\n");
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

