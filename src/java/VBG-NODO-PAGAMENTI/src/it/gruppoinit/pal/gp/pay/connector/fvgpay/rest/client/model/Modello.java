package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class Modello  {
  
  
  private String codiceModello = null;

  
  private String descrizioneModello = null;

  
  private String idModello = null;
 /**
   * Get codiceModello
   * @return codiceModello
  **/
  @XmlElement(name="codiceModello")
  public String getCodiceModello() {
    return codiceModello;
  }

  public void setCodiceModello(String codiceModello) {
    this.codiceModello = codiceModello;
  }

  public Modello codiceModello(String codiceModello) {
    this.codiceModello = codiceModello;
    return this;
  }

 /**
   * Get descrizioneModello
   * @return descrizioneModello
  **/
  @XmlElement(name="descrizioneModello")
  public String getDescrizioneModello() {
    return descrizioneModello;
  }

  public void setDescrizioneModello(String descrizioneModello) {
    this.descrizioneModello = descrizioneModello;
  }

  public Modello descrizioneModello(String descrizioneModello) {
    this.descrizioneModello = descrizioneModello;
    return this;
  }

 /**
   * Get idModello
   * @return idModello
  **/
  @XmlElement(name="idModello")
  public String getIdModello() {
    return idModello;
  }

  public void setIdModello(String idModello) {
    this.idModello = idModello;
  }

  public Modello idModello(String idModello) {
    this.idModello = idModello;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Modello {\n");
    
    sb.append("    codiceModello: ").append(toIndentedString(codiceModello)).append("\n");
    sb.append("    descrizioneModello: ").append(toIndentedString(descrizioneModello)).append("\n");
    sb.append("    idModello: ").append(toIndentedString(idModello)).append("\n");
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

