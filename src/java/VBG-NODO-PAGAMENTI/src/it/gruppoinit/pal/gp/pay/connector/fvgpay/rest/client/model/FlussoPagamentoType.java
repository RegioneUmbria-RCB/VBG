package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Rappresenta una transazione base del pagamento pagoPA, basato sull'invio di una RPT e la ricezione di una RT; dal punto di vista logico la ricevuta dovrebbe essere disponibile in un momento diverso, di fatto l'implementazione attuale comporta che la disponibilita' della RPT si concretizza al momento della ricezione della RT 
 **/

public class FlussoPagamentoType  {
  
  
  private RicevutaPagamentoType ricevutaPagamento = null;

  
  private RichiestaPagamentoType richiestaPagamento = null;
 /**
   * Get ricevutaPagamento
   * @return ricevutaPagamento
  **/
  @XmlElement(name="ricevuta_pagamento")
  public RicevutaPagamentoType getRicevutaPagamento() {
    return ricevutaPagamento;
  }

  public void setRicevutaPagamento(RicevutaPagamentoType ricevutaPagamento) {
    this.ricevutaPagamento = ricevutaPagamento;
  }

  public FlussoPagamentoType ricevutaPagamento(RicevutaPagamentoType ricevutaPagamento) {
    this.ricevutaPagamento = ricevutaPagamento;
    return this;
  }

 /**
   * Get richiestaPagamento
   * @return richiestaPagamento
  **/
  @XmlElement(name="richiesta_pagamento")
  public RichiestaPagamentoType getRichiestaPagamento() {
    return richiestaPagamento;
  }

  public void setRichiestaPagamento(RichiestaPagamentoType richiestaPagamento) {
    this.richiestaPagamento = richiestaPagamento;
  }

  public FlussoPagamentoType richiestaPagamento(RichiestaPagamentoType richiestaPagamento) {
    this.richiestaPagamento = richiestaPagamento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlussoPagamentoType {\n");
    
    sb.append("    ricevutaPagamento: ").append(toIndentedString(ricevutaPagamento)).append("\n");
    sb.append("    richiestaPagamento: ").append(toIndentedString(richiestaPagamento)).append("\n");
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

