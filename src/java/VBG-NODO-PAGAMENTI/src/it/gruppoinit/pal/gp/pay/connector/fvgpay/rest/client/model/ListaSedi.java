package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ListaSedi  {
  
  
  private List<Sede> sede = null;
 /**
   * Get sede
   * @return sede
  **/
  @XmlElement(name="sede")
  public List<Sede> getSede() {
    return sede;
  }

  public void setSede(List<Sede> sede) {
    this.sede = sede;
  }

  public ListaSedi sede(List<Sede> sede) {
    this.sede = sede;
    return this;
  }

  public ListaSedi addSedeItem(Sede sedeItem) {
    this.sede.add(sedeItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaSedi {\n");
    
    sb.append("    sede: ").append(toIndentedString(sede)).append("\n");
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

