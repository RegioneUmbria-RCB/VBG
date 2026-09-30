package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Comune  {
  
  
  private String codiceCatastaleComune = null;

  
  private String codiceIstatComune = null;

  
  private String denominazioneComune = null;

  
  private String id = null;

  
  private Provincia provincia = null;
 /**
   * Get codiceCatastaleComune
   * @return codiceCatastaleComune
  **/
  @XmlElement(name="codiceCatastaleComune")
  public String getCodiceCatastaleComune() {
    return codiceCatastaleComune;
  }

  public void setCodiceCatastaleComune(String codiceCatastaleComune) {
    this.codiceCatastaleComune = codiceCatastaleComune;
  }

  public Comune codiceCatastaleComune(String codiceCatastaleComune) {
    this.codiceCatastaleComune = codiceCatastaleComune;
    return this;
  }

 /**
   * Get codiceIstatComune
   * @return codiceIstatComune
  **/
  @XmlElement(name="codiceIstatComune")
  public String getCodiceIstatComune() {
    return codiceIstatComune;
  }

  public void setCodiceIstatComune(String codiceIstatComune) {
    this.codiceIstatComune = codiceIstatComune;
  }

  public Comune codiceIstatComune(String codiceIstatComune) {
    this.codiceIstatComune = codiceIstatComune;
    return this;
  }

 /**
   * Get denominazioneComune
   * @return denominazioneComune
  **/
  @XmlElement(name="denominazioneComune")
  public String getDenominazioneComune() {
    return denominazioneComune;
  }

  public void setDenominazioneComune(String denominazioneComune) {
    this.denominazioneComune = denominazioneComune;
  }

  public Comune denominazioneComune(String denominazioneComune) {
    this.denominazioneComune = denominazioneComune;
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

  public Comune id(String id) {
    this.id = id;
    return this;
  }

 /**
   * Get provincia
   * @return provincia
  **/
  @XmlElement(name="provincia")
  public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public Comune provincia(Provincia provincia) {
    this.provincia = provincia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comune {\n");
    
    sb.append("    codiceCatastaleComune: ").append(toIndentedString(codiceCatastaleComune)).append("\n");
    sb.append("    codiceIstatComune: ").append(toIndentedString(codiceIstatComune)).append("\n");
    sb.append("    denominazioneComune: ").append(toIndentedString(denominazioneComune)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
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

