package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ListaOrariApertura  {
  
  
  private List<OrarioApertura> orarioApertura = null;
 /**
   * Get orarioApertura
   * @return orarioApertura
  **/
  @XmlElement(name="orarioApertura")
  public List<OrarioApertura> getOrarioApertura() {
    return orarioApertura;
  }

  public void setOrarioApertura(List<OrarioApertura> orarioApertura) {
    this.orarioApertura = orarioApertura;
  }

  public ListaOrariApertura orarioApertura(List<OrarioApertura> orarioApertura) {
    this.orarioApertura = orarioApertura;
    return this;
  }

  public ListaOrariApertura addOrarioAperturaItem(OrarioApertura orarioAperturaItem) {
    this.orarioApertura.add(orarioAperturaItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaOrariApertura {\n");
    
    sb.append("    orarioApertura: ").append(toIndentedString(orarioApertura)).append("\n");
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

