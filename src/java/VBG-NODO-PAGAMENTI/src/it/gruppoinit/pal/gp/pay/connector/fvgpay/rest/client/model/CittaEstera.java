package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class CittaEstera  {
  
  
  private String denominazioneCittaEstera = null;

  
  private Stato stato = null;
 /**
   * Get denominazioneCittaEstera
   * @return denominazioneCittaEstera
  **/
  @XmlElement(name="denominazioneCittaEstera")
  public String getDenominazioneCittaEstera() {
    return denominazioneCittaEstera;
  }

  public void setDenominazioneCittaEstera(String denominazioneCittaEstera) {
    this.denominazioneCittaEstera = denominazioneCittaEstera;
  }

  public CittaEstera denominazioneCittaEstera(String denominazioneCittaEstera) {
    this.denominazioneCittaEstera = denominazioneCittaEstera;
    return this;
  }

 /**
   * Get stato
   * @return stato
  **/
  @XmlElement(name="stato")
  public Stato getStato() {
    return stato;
  }

  public void setStato(Stato stato) {
    this.stato = stato;
  }

  public CittaEstera stato(Stato stato) {
    this.stato = stato;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CittaEstera {\n");
    
    sb.append("    denominazioneCittaEstera: ").append(toIndentedString(denominazioneCittaEstera)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
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

