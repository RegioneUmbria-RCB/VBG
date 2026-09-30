package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ParamProcConfigProfiloUtenteGestioneDto  {
  
  
  private String codErrore = null;

  
  private String codiceFiscaleUtgest = null;

  
  private String descErrore = null;

  
  private String esito = null;

  
  private String idUtenteGestione = null;

  
  private String loginfvgUsernameUtgest = null;
 /**
   * Get codErrore
   * @return codErrore
  **/
  @XmlElement(name="codErrore")
  public String getCodErrore() {
    return codErrore;
  }

  public void setCodErrore(String codErrore) {
    this.codErrore = codErrore;
  }

  public ParamProcConfigProfiloUtenteGestioneDto codErrore(String codErrore) {
    this.codErrore = codErrore;
    return this;
  }

 /**
   * Get codiceFiscaleUtgest
   * @return codiceFiscaleUtgest
  **/
  @XmlElement(name="codiceFiscaleUtgest")
  public String getCodiceFiscaleUtgest() {
    return codiceFiscaleUtgest;
  }

  public void setCodiceFiscaleUtgest(String codiceFiscaleUtgest) {
    this.codiceFiscaleUtgest = codiceFiscaleUtgest;
  }

  public ParamProcConfigProfiloUtenteGestioneDto codiceFiscaleUtgest(String codiceFiscaleUtgest) {
    this.codiceFiscaleUtgest = codiceFiscaleUtgest;
    return this;
  }

 /**
   * Get descErrore
   * @return descErrore
  **/
  @XmlElement(name="descErrore")
  public String getDescErrore() {
    return descErrore;
  }

  public void setDescErrore(String descErrore) {
    this.descErrore = descErrore;
  }

  public ParamProcConfigProfiloUtenteGestioneDto descErrore(String descErrore) {
    this.descErrore = descErrore;
    return this;
  }

 /**
   * Get esito
   * @return esito
  **/
  @XmlElement(name="esito")
  public String getEsito() {
    return esito;
  }

  public void setEsito(String esito) {
    this.esito = esito;
  }

  public ParamProcConfigProfiloUtenteGestioneDto esito(String esito) {
    this.esito = esito;
    return this;
  }

 /**
   * Get idUtenteGestione
   * @return idUtenteGestione
  **/
  @XmlElement(name="idUtenteGestione")
  public String getIdUtenteGestione() {
    return idUtenteGestione;
  }

  public void setIdUtenteGestione(String idUtenteGestione) {
    this.idUtenteGestione = idUtenteGestione;
  }

  public ParamProcConfigProfiloUtenteGestioneDto idUtenteGestione(String idUtenteGestione) {
    this.idUtenteGestione = idUtenteGestione;
    return this;
  }

 /**
   * Get loginfvgUsernameUtgest
   * @return loginfvgUsernameUtgest
  **/
  @XmlElement(name="loginfvgUsernameUtgest")
  public String getLoginfvgUsernameUtgest() {
    return loginfvgUsernameUtgest;
  }

  public void setLoginfvgUsernameUtgest(String loginfvgUsernameUtgest) {
    this.loginfvgUsernameUtgest = loginfvgUsernameUtgest;
  }

  public ParamProcConfigProfiloUtenteGestioneDto loginfvgUsernameUtgest(String loginfvgUsernameUtgest) {
    this.loginfvgUsernameUtgest = loginfvgUsernameUtgest;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamProcConfigProfiloUtenteGestioneDto {\n");
    
    sb.append("    codErrore: ").append(toIndentedString(codErrore)).append("\n");
    sb.append("    codiceFiscaleUtgest: ").append(toIndentedString(codiceFiscaleUtgest)).append("\n");
    sb.append("    descErrore: ").append(toIndentedString(descErrore)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    idUtenteGestione: ").append(toIndentedString(idUtenteGestione)).append("\n");
    sb.append("    loginfvgUsernameUtgest: ").append(toIndentedString(loginfvgUsernameUtgest)).append("\n");
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

