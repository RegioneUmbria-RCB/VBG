package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class PercorsoRadiceNodo  {
  
  
  private List<ProprietaBase> passoPercorso = null;
 /**
   * Get passoPercorso
   * @return passoPercorso
  **/
  @XmlElement(name="passoPercorso")
  public List<ProprietaBase> getPassoPercorso() {
    return passoPercorso;
  }

  public void setPassoPercorso(List<ProprietaBase> passoPercorso) {
    this.passoPercorso = passoPercorso;
  }

  public PercorsoRadiceNodo passoPercorso(List<ProprietaBase> passoPercorso) {
    this.passoPercorso = passoPercorso;
    return this;
  }

  public PercorsoRadiceNodo addPassoPercorsoItem(ProprietaBase passoPercorsoItem) {
    this.passoPercorso.add(passoPercorsoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PercorsoRadiceNodo {\n");
    
    sb.append("    passoPercorso: ").append(toIndentedString(passoPercorso)).append("\n");
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

