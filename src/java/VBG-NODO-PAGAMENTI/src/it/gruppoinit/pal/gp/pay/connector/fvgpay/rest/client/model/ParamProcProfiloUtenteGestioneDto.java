package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ParamProcProfiloUtenteGestioneDto  {
  
  
  private String codErrore = null;

  
  private String codiceFiscaleUtgest = null;

  
  private String codiceLegameMasterdata = null;

  
  private String codiceStrutturaMasterdata = null;

  
  private String cognomeUtgest = null;

  
  private String descErrore = null;

  
  private String emailUtgest = null;

  
  private String esito = null;

  
  private String loginfvgUsernameUtgest = null;

  
  private String nint = null;

  
  private String nomeUtgest = null;
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

  public ParamProcProfiloUtenteGestioneDto codErrore(String codErrore) {
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

  public ParamProcProfiloUtenteGestioneDto codiceFiscaleUtgest(String codiceFiscaleUtgest) {
    this.codiceFiscaleUtgest = codiceFiscaleUtgest;
    return this;
  }

 /**
   * Get codiceLegameMasterdata
   * @return codiceLegameMasterdata
  **/
  @XmlElement(name="codiceLegameMasterdata")
  public String getCodiceLegameMasterdata() {
    return codiceLegameMasterdata;
  }

  public void setCodiceLegameMasterdata(String codiceLegameMasterdata) {
    this.codiceLegameMasterdata = codiceLegameMasterdata;
  }

  public ParamProcProfiloUtenteGestioneDto codiceLegameMasterdata(String codiceLegameMasterdata) {
    this.codiceLegameMasterdata = codiceLegameMasterdata;
    return this;
  }

 /**
   * Get codiceStrutturaMasterdata
   * @return codiceStrutturaMasterdata
  **/
  @XmlElement(name="codiceStrutturaMasterdata")
  public String getCodiceStrutturaMasterdata() {
    return codiceStrutturaMasterdata;
  }

  public void setCodiceStrutturaMasterdata(String codiceStrutturaMasterdata) {
    this.codiceStrutturaMasterdata = codiceStrutturaMasterdata;
  }

  public ParamProcProfiloUtenteGestioneDto codiceStrutturaMasterdata(String codiceStrutturaMasterdata) {
    this.codiceStrutturaMasterdata = codiceStrutturaMasterdata;
    return this;
  }

 /**
   * Get cognomeUtgest
   * @return cognomeUtgest
  **/
  @XmlElement(name="cognomeUtgest")
  public String getCognomeUtgest() {
    return cognomeUtgest;
  }

  public void setCognomeUtgest(String cognomeUtgest) {
    this.cognomeUtgest = cognomeUtgest;
  }

  public ParamProcProfiloUtenteGestioneDto cognomeUtgest(String cognomeUtgest) {
    this.cognomeUtgest = cognomeUtgest;
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

  public ParamProcProfiloUtenteGestioneDto descErrore(String descErrore) {
    this.descErrore = descErrore;
    return this;
  }

 /**
   * Get emailUtgest
   * @return emailUtgest
  **/
  @XmlElement(name="emailUtgest")
  public String getEmailUtgest() {
    return emailUtgest;
  }

  public void setEmailUtgest(String emailUtgest) {
    this.emailUtgest = emailUtgest;
  }

  public ParamProcProfiloUtenteGestioneDto emailUtgest(String emailUtgest) {
    this.emailUtgest = emailUtgest;
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

  public ParamProcProfiloUtenteGestioneDto esito(String esito) {
    this.esito = esito;
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

  public ParamProcProfiloUtenteGestioneDto loginfvgUsernameUtgest(String loginfvgUsernameUtgest) {
    this.loginfvgUsernameUtgest = loginfvgUsernameUtgest;
    return this;
  }

 /**
   * Get nint
   * @return nint
  **/
  @XmlElement(name="nint")
  public String getNint() {
    return nint;
  }

  public void setNint(String nint) {
    this.nint = nint;
  }

  public ParamProcProfiloUtenteGestioneDto nint(String nint) {
    this.nint = nint;
    return this;
  }

 /**
   * Get nomeUtgest
   * @return nomeUtgest
  **/
  @XmlElement(name="nomeUtgest")
  public String getNomeUtgest() {
    return nomeUtgest;
  }

  public void setNomeUtgest(String nomeUtgest) {
    this.nomeUtgest = nomeUtgest;
  }

  public ParamProcProfiloUtenteGestioneDto nomeUtgest(String nomeUtgest) {
    this.nomeUtgest = nomeUtgest;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamProcProfiloUtenteGestioneDto {\n");
    
    sb.append("    codErrore: ").append(toIndentedString(codErrore)).append("\n");
    sb.append("    codiceFiscaleUtgest: ").append(toIndentedString(codiceFiscaleUtgest)).append("\n");
    sb.append("    codiceLegameMasterdata: ").append(toIndentedString(codiceLegameMasterdata)).append("\n");
    sb.append("    codiceStrutturaMasterdata: ").append(toIndentedString(codiceStrutturaMasterdata)).append("\n");
    sb.append("    cognomeUtgest: ").append(toIndentedString(cognomeUtgest)).append("\n");
    sb.append("    descErrore: ").append(toIndentedString(descErrore)).append("\n");
    sb.append("    emailUtgest: ").append(toIndentedString(emailUtgest)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    loginfvgUsernameUtgest: ").append(toIndentedString(loginfvgUsernameUtgest)).append("\n");
    sb.append("    nint: ").append(toIndentedString(nint)).append("\n");
    sb.append("    nomeUtgest: ").append(toIndentedString(nomeUtgest)).append("\n");
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

