package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class RegioniDto  {
  
  
  private String codiceRegione = null;

  
  private String descrizione = null;
 /**
   * Get codiceRegione
   * @return codiceRegione
  **/
  @XmlElement(name="codiceRegione")
  public String getCodiceRegione() {
    return codiceRegione;
  }

  public void setCodiceRegione(String codiceRegione) {
    this.codiceRegione = codiceRegione;
  }

  public RegioniDto codiceRegione(String codiceRegione) {
    this.codiceRegione = codiceRegione;
    return this;
  }

 /**
   * Get descrizione
   * @return descrizione
  **/
  @XmlElement(name="descrizione")
  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public RegioniDto descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegioniDto {\n");
    
    sb.append("    codiceRegione: ").append(toIndentedString(codiceRegione)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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

