package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ClasseStruttura  {
  
  
  private String codiceClasseStruttura = null;

  
  private String descrizioneClasseStruttura = null;

  
  private String idClasseStruttura = null;
 /**
   * Get codiceClasseStruttura
   * @return codiceClasseStruttura
  **/
  @XmlElement(name="codiceClasseStruttura")
  public String getCodiceClasseStruttura() {
    return codiceClasseStruttura;
  }

  public void setCodiceClasseStruttura(String codiceClasseStruttura) {
    this.codiceClasseStruttura = codiceClasseStruttura;
  }

  public ClasseStruttura codiceClasseStruttura(String codiceClasseStruttura) {
    this.codiceClasseStruttura = codiceClasseStruttura;
    return this;
  }

 /**
   * Get descrizioneClasseStruttura
   * @return descrizioneClasseStruttura
  **/
  @XmlElement(name="descrizioneClasseStruttura")
  public String getDescrizioneClasseStruttura() {
    return descrizioneClasseStruttura;
  }

  public void setDescrizioneClasseStruttura(String descrizioneClasseStruttura) {
    this.descrizioneClasseStruttura = descrizioneClasseStruttura;
  }

  public ClasseStruttura descrizioneClasseStruttura(String descrizioneClasseStruttura) {
    this.descrizioneClasseStruttura = descrizioneClasseStruttura;
    return this;
  }

 /**
   * Get idClasseStruttura
   * @return idClasseStruttura
  **/
  @XmlElement(name="idClasseStruttura")
  public String getIdClasseStruttura() {
    return idClasseStruttura;
  }

  public void setIdClasseStruttura(String idClasseStruttura) {
    this.idClasseStruttura = idClasseStruttura;
  }

  public ClasseStruttura idClasseStruttura(String idClasseStruttura) {
    this.idClasseStruttura = idClasseStruttura;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClasseStruttura {\n");
    
    sb.append("    codiceClasseStruttura: ").append(toIndentedString(codiceClasseStruttura)).append("\n");
    sb.append("    descrizioneClasseStruttura: ").append(toIndentedString(descrizioneClasseStruttura)).append("\n");
    sb.append("    idClasseStruttura: ").append(toIndentedString(idClasseStruttura)).append("\n");
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

