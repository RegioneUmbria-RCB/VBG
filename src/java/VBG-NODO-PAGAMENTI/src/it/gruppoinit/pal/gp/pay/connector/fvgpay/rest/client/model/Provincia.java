package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class Provincia  {
  
  
  private String codiceIstatProvincia = null;

  
  private String codiceNutsProvincia = null;

  
  private String denominazioneProvincia = null;

  
  private String id = null;

  
  private String siglaProvincia = null;
 /**
   * Get codiceIstatProvincia
   * @return codiceIstatProvincia
  **/
  @XmlElement(name="codiceIstatProvincia")
  public String getCodiceIstatProvincia() {
    return codiceIstatProvincia;
  }

  public void setCodiceIstatProvincia(String codiceIstatProvincia) {
    this.codiceIstatProvincia = codiceIstatProvincia;
  }

  public Provincia codiceIstatProvincia(String codiceIstatProvincia) {
    this.codiceIstatProvincia = codiceIstatProvincia;
    return this;
  }

 /**
   * Get codiceNutsProvincia
   * @return codiceNutsProvincia
  **/
  @XmlElement(name="codiceNutsProvincia")
  public String getCodiceNutsProvincia() {
    return codiceNutsProvincia;
  }

  public void setCodiceNutsProvincia(String codiceNutsProvincia) {
    this.codiceNutsProvincia = codiceNutsProvincia;
  }

  public Provincia codiceNutsProvincia(String codiceNutsProvincia) {
    this.codiceNutsProvincia = codiceNutsProvincia;
    return this;
  }

 /**
   * Get denominazioneProvincia
   * @return denominazioneProvincia
  **/
  @XmlElement(name="denominazioneProvincia")
  public String getDenominazioneProvincia() {
    return denominazioneProvincia;
  }

  public void setDenominazioneProvincia(String denominazioneProvincia) {
    this.denominazioneProvincia = denominazioneProvincia;
  }

  public Provincia denominazioneProvincia(String denominazioneProvincia) {
    this.denominazioneProvincia = denominazioneProvincia;
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

  public Provincia id(String id) {
    this.id = id;
    return this;
  }

 /**
   * Get siglaProvincia
   * @return siglaProvincia
  **/
  @XmlElement(name="siglaProvincia")
  public String getSiglaProvincia() {
    return siglaProvincia;
  }

  public void setSiglaProvincia(String siglaProvincia) {
    this.siglaProvincia = siglaProvincia;
  }

  public Provincia siglaProvincia(String siglaProvincia) {
    this.siglaProvincia = siglaProvincia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Provincia {\n");
    
    sb.append("    codiceIstatProvincia: ").append(toIndentedString(codiceIstatProvincia)).append("\n");
    sb.append("    codiceNutsProvincia: ").append(toIndentedString(codiceNutsProvincia)).append("\n");
    sb.append("    denominazioneProvincia: ").append(toIndentedString(denominazioneProvincia)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    siglaProvincia: ").append(toIndentedString(siglaProvincia)).append("\n");
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

