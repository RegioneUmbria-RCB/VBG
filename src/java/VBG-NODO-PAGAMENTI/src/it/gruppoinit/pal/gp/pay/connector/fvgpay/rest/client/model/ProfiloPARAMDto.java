package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ProfiloPARAMDto  {
  
  
  private String codiceFiscaleUtenteGestione = null;

  
  private String codiceStrutturaMasterdata = null;

  
  private String idEnte = null;

  
  private String idProfilo = null;

  
  private String idServizioEnte = null;

  
  private String idUtenteGestione = null;

  
  private String loginFvgUsername = null;

  
  private List<String> ltCodStrutturaMdCodRuoloMd = null;
 /**
   * Get codiceFiscaleUtenteGestione
   * @return codiceFiscaleUtenteGestione
  **/
  @XmlElement(name="codiceFiscaleUtenteGestione")
  public String getCodiceFiscaleUtenteGestione() {
    return codiceFiscaleUtenteGestione;
  }

  public void setCodiceFiscaleUtenteGestione(String codiceFiscaleUtenteGestione) {
    this.codiceFiscaleUtenteGestione = codiceFiscaleUtenteGestione;
  }

  public ProfiloPARAMDto codiceFiscaleUtenteGestione(String codiceFiscaleUtenteGestione) {
    this.codiceFiscaleUtenteGestione = codiceFiscaleUtenteGestione;
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

  public ProfiloPARAMDto codiceStrutturaMasterdata(String codiceStrutturaMasterdata) {
    this.codiceStrutturaMasterdata = codiceStrutturaMasterdata;
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

  public ProfiloPARAMDto idEnte(String idEnte) {
    this.idEnte = idEnte;
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

  public ProfiloPARAMDto idProfilo(String idProfilo) {
    this.idProfilo = idProfilo;
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

  public ProfiloPARAMDto idServizioEnte(String idServizioEnte) {
    this.idServizioEnte = idServizioEnte;
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

  public ProfiloPARAMDto idUtenteGestione(String idUtenteGestione) {
    this.idUtenteGestione = idUtenteGestione;
    return this;
  }

 /**
   * Get loginFvgUsername
   * @return loginFvgUsername
  **/
  @XmlElement(name="loginFvgUsername")
  public String getLoginFvgUsername() {
    return loginFvgUsername;
  }

  public void setLoginFvgUsername(String loginFvgUsername) {
    this.loginFvgUsername = loginFvgUsername;
  }

  public ProfiloPARAMDto loginFvgUsername(String loginFvgUsername) {
    this.loginFvgUsername = loginFvgUsername;
    return this;
  }

 /**
   * Get ltCodStrutturaMdCodRuoloMd
   * @return ltCodStrutturaMdCodRuoloMd
  **/
  @XmlElement(name="ltCodStrutturaMdCodRuoloMd")
  public List<String> getLtCodStrutturaMdCodRuoloMd() {
    return ltCodStrutturaMdCodRuoloMd;
  }

  public void setLtCodStrutturaMdCodRuoloMd(List<String> ltCodStrutturaMdCodRuoloMd) {
    this.ltCodStrutturaMdCodRuoloMd = ltCodStrutturaMdCodRuoloMd;
  }

  public ProfiloPARAMDto ltCodStrutturaMdCodRuoloMd(List<String> ltCodStrutturaMdCodRuoloMd) {
    this.ltCodStrutturaMdCodRuoloMd = ltCodStrutturaMdCodRuoloMd;
    return this;
  }

  public ProfiloPARAMDto addLtCodStrutturaMdCodRuoloMdItem(String ltCodStrutturaMdCodRuoloMdItem) {
    this.ltCodStrutturaMdCodRuoloMd.add(ltCodStrutturaMdCodRuoloMdItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProfiloPARAMDto {\n");
    
    sb.append("    codiceFiscaleUtenteGestione: ").append(toIndentedString(codiceFiscaleUtenteGestione)).append("\n");
    sb.append("    codiceStrutturaMasterdata: ").append(toIndentedString(codiceStrutturaMasterdata)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    idProfilo: ").append(toIndentedString(idProfilo)).append("\n");
    sb.append("    idServizioEnte: ").append(toIndentedString(idServizioEnte)).append("\n");
    sb.append("    idUtenteGestione: ").append(toIndentedString(idUtenteGestione)).append("\n");
    sb.append("    loginFvgUsername: ").append(toIndentedString(loginFvgUsername)).append("\n");
    sb.append("    ltCodStrutturaMdCodRuoloMd: ").append(toIndentedString(ltCodStrutturaMdCodRuoloMd)).append("\n");
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

