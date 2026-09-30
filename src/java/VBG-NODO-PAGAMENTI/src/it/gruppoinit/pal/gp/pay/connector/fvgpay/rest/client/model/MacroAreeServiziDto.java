package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class MacroAreeServiziDto  {
  
  
  private String denominazione = null;

  
  private Long idMacroArea = null;
 /**
   * Get denominazione
   * @return denominazione
  **/
  @XmlElement(name="denominazione")
  public String getDenominazione() {
    return denominazione;
  }

  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  public MacroAreeServiziDto denominazione(String denominazione) {
    this.denominazione = denominazione;
    return this;
  }

 /**
   * Get idMacroArea
   * @return idMacroArea
  **/
  @XmlElement(name="idMacroArea")
  public Long getIdMacroArea() {
    return idMacroArea;
  }

  public void setIdMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
  }

  public MacroAreeServiziDto idMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MacroAreeServiziDto {\n");
    
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    idMacroArea: ").append(toIndentedString(idMacroArea)).append("\n");
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

