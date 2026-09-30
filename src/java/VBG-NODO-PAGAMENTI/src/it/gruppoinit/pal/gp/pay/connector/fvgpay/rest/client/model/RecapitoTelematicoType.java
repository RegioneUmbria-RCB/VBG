package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Recapito telematico del Pagatore a cui notificare l'avviso di pagamento 
 **/

public class RecapitoTelematicoType  {
  
  
 /**
   * Definizione del recapito telematico del Pagatore 
  **/
  private String recapito = null;

  
 /**
   * Definisce i possibili tipi di recapito telematico ammessi per le notifiche digitali. Se mail deve essere un indirizzo mail valido 
  **/
  private String tipo = null;
 /**
   * Definizione del recapito telematico del Pagatore 
   * @return recapito
  **/
  @XmlElement(name="recapito")
  public String getRecapito() {
    return recapito;
  }

  public void setRecapito(String recapito) {
    this.recapito = recapito;
  }

  public RecapitoTelematicoType recapito(String recapito) {
    this.recapito = recapito;
    return this;
  }

 /**
   * Definisce i possibili tipi di recapito telematico ammessi per le notifiche digitali. Se mail deve essere un indirizzo mail valido 
   * @return tipo
  **/
  @XmlElement(name="tipo")
  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public RecapitoTelematicoType tipo(String tipo) {
    this.tipo = tipo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecapitoTelematicoType {\n");
    
    sb.append("    recapito: ").append(toIndentedString(recapito)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
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

