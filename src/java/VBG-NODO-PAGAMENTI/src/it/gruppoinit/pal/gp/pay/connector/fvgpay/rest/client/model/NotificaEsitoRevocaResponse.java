package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Descrive l'ER inviato al nodo    
 **/

public class NotificaEsitoRevocaResponse  {
  
  
  private RicevutaRevocaType esitoRevocaPagopa = null;
 /**
   * Get esitoRevocaPagopa
   * @return esitoRevocaPagopa
  **/
  @XmlElement(name="esito_revoca_pagopa")
  public RicevutaRevocaType getEsitoRevocaPagopa() {
    return esitoRevocaPagopa;
  }

  public void setEsitoRevocaPagopa(RicevutaRevocaType esitoRevocaPagopa) {
    this.esitoRevocaPagopa = esitoRevocaPagopa;
  }

  public NotificaEsitoRevocaResponse esitoRevocaPagopa(RicevutaRevocaType esitoRevocaPagopa) {
    this.esitoRevocaPagopa = esitoRevocaPagopa;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificaEsitoRevocaResponse {\n");
    
    sb.append("    esitoRevocaPagopa: ").append(toIndentedString(esitoRevocaPagopa)).append("\n");
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

