package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class RecapitiTelematiciType  {
  
  
  private List<RecapitoTelematicoType> elenco = null;
 /**
   * Get elenco
   * @return elenco
  **/
  @XmlElement(name="elenco")
  public List<RecapitoTelematicoType> getElenco() {
    return elenco;
  }

  public void setElenco(List<RecapitoTelematicoType> elenco) {
    this.elenco = elenco;
  }

  public RecapitiTelematiciType elenco(List<RecapitoTelematicoType> elenco) {
    this.elenco = elenco;
    return this;
  }

  public RecapitiTelematiciType addElencoItem(RecapitoTelematicoType elencoItem) {
    this.elenco.add(elencoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecapitiTelematiciType {\n");
    
    sb.append("    elenco: ").append(toIndentedString(elenco)).append("\n");
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

