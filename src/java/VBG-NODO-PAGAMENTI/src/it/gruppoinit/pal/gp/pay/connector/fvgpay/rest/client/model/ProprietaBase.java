package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ProprietaBase  {
  
  
  private String codice = null;

  
  private String descrizione = null;

  
  private String id = null;
 /**
   * Get codice
   * @return codice
  **/
  @XmlElement(name="codice")
  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public ProprietaBase codice(String codice) {
    this.codice = codice;
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

  public ProprietaBase descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * Get id
   * @return id
  **/
  @XmlElement(name="id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ProprietaBase id(String id) {
    this.id = id;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProprietaBase {\n");
    
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

