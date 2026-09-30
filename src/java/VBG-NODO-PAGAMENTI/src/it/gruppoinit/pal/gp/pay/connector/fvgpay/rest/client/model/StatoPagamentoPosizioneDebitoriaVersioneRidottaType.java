package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Descrive lo stato di pagamento della posizione debitoria Contiene anche il dettaglio dello stato del pagamento in unica soluzione e degli eventuali pagamenti associati alle singole rate 
 **/

public class StatoPagamentoPosizioneDebitoriaVersioneRidottaType  {
  
  
  private StatoPosizioneVersioneRidottaType statoPagamento = null;
 /**
   * Get statoPagamento
   * @return statoPagamento
  **/
  @XmlElement(name="stato_pagamento")
  public StatoPosizioneVersioneRidottaType getStatoPagamento() {
    return statoPagamento;
  }

  public void setStatoPagamento(StatoPosizioneVersioneRidottaType statoPagamento) {
    this.statoPagamento = statoPagamento;
  }

  public StatoPagamentoPosizioneDebitoriaVersioneRidottaType statoPagamento(StatoPosizioneVersioneRidottaType statoPagamento) {
    this.statoPagamento = statoPagamento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoPagamentoPosizioneDebitoriaVersioneRidottaType {\n");
    
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

