package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Rappresenta il flusso di gestione di una richiesta di revoca pagoPA,  basato sull'invio di una RR e la ricezione di una ER; L'esito risulta disponibile in un momento diverso rispetto alla  richiesta 
 **/

public class FlussoRevocaType  {
  
  
  private RicevutaRevocaType ricevutaRevoca = null;

  
  private RichiestaRevocaType richiestaRevoca = null;
 /**
   * Get ricevutaRevoca
   * @return ricevutaRevoca
  **/
  @XmlElement(name="ricevuta_revoca")
  public RicevutaRevocaType getRicevutaRevoca() {
    return ricevutaRevoca;
  }

  public void setRicevutaRevoca(RicevutaRevocaType ricevutaRevoca) {
    this.ricevutaRevoca = ricevutaRevoca;
  }

  public FlussoRevocaType ricevutaRevoca(RicevutaRevocaType ricevutaRevoca) {
    this.ricevutaRevoca = ricevutaRevoca;
    return this;
  }

 /**
   * Get richiestaRevoca
   * @return richiestaRevoca
  **/
  @XmlElement(name="richiesta_revoca")
  public RichiestaRevocaType getRichiestaRevoca() {
    return richiestaRevoca;
  }

  public void setRichiestaRevoca(RichiestaRevocaType richiestaRevoca) {
    this.richiestaRevoca = richiestaRevoca;
  }

  public FlussoRevocaType richiestaRevoca(RichiestaRevocaType richiestaRevoca) {
    this.richiestaRevoca = richiestaRevoca;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FlussoRevocaType {\n");
    
    sb.append("    ricevutaRevoca: ").append(toIndentedString(ricevutaRevoca)).append("\n");
    sb.append("    richiestaRevoca: ").append(toIndentedString(richiestaRevoca)).append("\n");
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

