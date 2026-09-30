package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * oggetto standard da usare nelle response con stato 2xx che restituisce un messaggio di conferma che l'operazione richiesta e' andata a buon fine 
 **/

public class OkMessage  {
  
  
 /**
   * messaggio di conferma
  **/
  private String message = null;
 /**
   * messaggio di conferma
   * @return message
  **/
  @XmlElement(name="message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public OkMessage message(String message) {
    this.message = message;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OkMessage {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

