package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class Ente  {
  
  
  private String codiceEnte = null;

  
  private String codiceIPA = null;

  
  private String denominazioneEnte = null;

  
  private String idEnte = null;

  
  private String tipoEnte = null;
 /**
   * Get codiceEnte
   * @return codiceEnte
  **/
  @XmlElement(name="codiceEnte")
  public String getCodiceEnte() {
    return codiceEnte;
  }

  public void setCodiceEnte(String codiceEnte) {
    this.codiceEnte = codiceEnte;
  }

  public Ente codiceEnte(String codiceEnte) {
    this.codiceEnte = codiceEnte;
    return this;
  }

 /**
   * Get codiceIPA
   * @return codiceIPA
  **/
  @XmlElement(name="codiceIPA")
  public String getCodiceIPA() {
    return codiceIPA;
  }

  public void setCodiceIPA(String codiceIPA) {
    this.codiceIPA = codiceIPA;
  }

  public Ente codiceIPA(String codiceIPA) {
    this.codiceIPA = codiceIPA;
    return this;
  }

 /**
   * Get denominazioneEnte
   * @return denominazioneEnte
  **/
  @XmlElement(name="denominazioneEnte")
  public String getDenominazioneEnte() {
    return denominazioneEnte;
  }

  public void setDenominazioneEnte(String denominazioneEnte) {
    this.denominazioneEnte = denominazioneEnte;
  }

  public Ente denominazioneEnte(String denominazioneEnte) {
    this.denominazioneEnte = denominazioneEnte;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="idEnte")
  public String getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(String idEnte) {
    this.idEnte = idEnte;
  }

  public Ente idEnte(String idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get tipoEnte
   * @return tipoEnte
  **/
  @XmlElement(name="tipoEnte")
  public String getTipoEnte() {
    return tipoEnte;
  }

  public void setTipoEnte(String tipoEnte) {
    this.tipoEnte = tipoEnte;
  }

  public Ente tipoEnte(String tipoEnte) {
    this.tipoEnte = tipoEnte;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ente {\n");
    
    sb.append("    codiceEnte: ").append(toIndentedString(codiceEnte)).append("\n");
    sb.append("    codiceIPA: ").append(toIndentedString(codiceIPA)).append("\n");
    sb.append("    denominazioneEnte: ").append(toIndentedString(denominazioneEnte)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    tipoEnte: ").append(toIndentedString(tipoEnte)).append("\n");
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

