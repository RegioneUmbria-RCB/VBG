package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ParamProcConfigBackOfficeProfiloUtenteGestioneDto  {
  
  
  private String attivazione = null;

  
  private String codErrore = null;

  
  private String codiceFiscaleUtgest = null;

  
  private String descErrore = null;

  
  private String esito = null;

  
  private String idConfigProfilo = null;

  
  private String idProfilo = null;

  
  private String idRuolo = null;

  
  private String idServizioEnte = null;

  
  private String loginfvgUsernameUtgest = null;
 /**
   * Get attivazione
   * @return attivazione
  **/
  @XmlElement(name="attivazione")
  public String getAttivazione() {
    return attivazione;
  }

  public void setAttivazione(String attivazione) {
    this.attivazione = attivazione;
  }

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto attivazione(String attivazione) {
    this.attivazione = attivazione;
    return this;
  }

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

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto codErrore(String codErrore) {
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

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto codiceFiscaleUtgest(String codiceFiscaleUtgest) {
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

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto descErrore(String descErrore) {
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

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto esito(String esito) {
    this.esito = esito;
    return this;
  }

 /**
   * Get idConfigProfilo
   * @return idConfigProfilo
  **/
  @XmlElement(name="idConfigProfilo")
  public String getIdConfigProfilo() {
    return idConfigProfilo;
  }

  public void setIdConfigProfilo(String idConfigProfilo) {
    this.idConfigProfilo = idConfigProfilo;
  }

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto idConfigProfilo(String idConfigProfilo) {
    this.idConfigProfilo = idConfigProfilo;
    return this;
  }

 /**
   * Get idProfilo
   * @return idProfilo
  **/
  @XmlElement(name="idProfilo")
  public String getIdProfilo() {
    return idProfilo;
  }

  public void setIdProfilo(String idProfilo) {
    this.idProfilo = idProfilo;
  }

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto idProfilo(String idProfilo) {
    this.idProfilo = idProfilo;
    return this;
  }

 /**
   * Get idRuolo
   * @return idRuolo
  **/
  @XmlElement(name="idRuolo")
  public String getIdRuolo() {
    return idRuolo;
  }

  public void setIdRuolo(String idRuolo) {
    this.idRuolo = idRuolo;
  }

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto idRuolo(String idRuolo) {
    this.idRuolo = idRuolo;
    return this;
  }

 /**
   * Get idServizioEnte
   * @return idServizioEnte
  **/
  @XmlElement(name="idServizioEnte")
  public String getIdServizioEnte() {
    return idServizioEnte;
  }

  public void setIdServizioEnte(String idServizioEnte) {
    this.idServizioEnte = idServizioEnte;
  }

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto idServizioEnte(String idServizioEnte) {
    this.idServizioEnte = idServizioEnte;
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

  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto loginfvgUsernameUtgest(String loginfvgUsernameUtgest) {
    this.loginfvgUsernameUtgest = loginfvgUsernameUtgest;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamProcConfigBackOfficeProfiloUtenteGestioneDto {\n");
    
    sb.append("    attivazione: ").append(toIndentedString(attivazione)).append("\n");
    sb.append("    codErrore: ").append(toIndentedString(codErrore)).append("\n");
    sb.append("    codiceFiscaleUtgest: ").append(toIndentedString(codiceFiscaleUtgest)).append("\n");
    sb.append("    descErrore: ").append(toIndentedString(descErrore)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    idConfigProfilo: ").append(toIndentedString(idConfigProfilo)).append("\n");
    sb.append("    idProfilo: ").append(toIndentedString(idProfilo)).append("\n");
    sb.append("    idRuolo: ").append(toIndentedString(idRuolo)).append("\n");
    sb.append("    idServizioEnte: ").append(toIndentedString(idServizioEnte)).append("\n");
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

