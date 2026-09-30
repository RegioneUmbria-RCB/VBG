package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class StatoPagamentoRateVersioneRidottaType  {
  
  
  private ImportoType importo = null;

  
 /**
   * Identificativo Univoco di Versamento (IUV)
  **/
  private String iuv = null;

  
 /**
   * Definisce i possibili esiti di un pagamento, riferiti ad un singolo iuv 
  **/
  private String statoPagamento = null;
 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public ImportoType getImporto() {
    return importo;
  }

  public void setImporto(ImportoType importo) {
    this.importo = importo;
  }

  public StatoPagamentoRateVersioneRidottaType importo(ImportoType importo) {
    this.importo = importo;
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

  public StatoPagamentoRateVersioneRidottaType iuv(String iuv) {
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

  public StatoPagamentoRateVersioneRidottaType statoPagamento(String statoPagamento) {
    this.statoPagamento = statoPagamento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoPagamentoRateVersioneRidottaType {\n");
    
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
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

