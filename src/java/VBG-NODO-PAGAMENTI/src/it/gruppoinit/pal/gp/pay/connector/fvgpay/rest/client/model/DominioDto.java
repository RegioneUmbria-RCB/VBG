package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class DominioDto  {
  
  
  private Integer attivo = null;

  
  private String codiceServizioEnte = null;

  
  private String codiceTassonomia = null;

  
  private String codiceTassonomiaPagopa = null;

  
  private String descrizioneServizio = null;

  
  private Long idDominio = null;

  
  private Long idEnte = null;

  
  private Long idServizio = null;

  
  private Integer pagamentoTelematico = null;

  
  private String passwordServizio = null;

  
  private String passwordWs = null;

  
  private String tipoServizio = null;

  
  private String userServizio = null;

  
  private String userWs = null;
 /**
   * Get attivo
   * @return attivo
  **/
  @XmlElement(name="attivo")
  public Integer getAttivo() {
    return attivo;
  }

  public void setAttivo(Integer attivo) {
    this.attivo = attivo;
  }

  public DominioDto attivo(Integer attivo) {
    this.attivo = attivo;
    return this;
  }

 /**
   * Get codiceServizioEnte
   * @return codiceServizioEnte
  **/
  @XmlElement(name="codiceServizioEnte")
  public String getCodiceServizioEnte() {
    return codiceServizioEnte;
  }

  public void setCodiceServizioEnte(String codiceServizioEnte) {
    this.codiceServizioEnte = codiceServizioEnte;
  }

  public DominioDto codiceServizioEnte(String codiceServizioEnte) {
    this.codiceServizioEnte = codiceServizioEnte;
    return this;
  }

 /**
   * Get codiceTassonomia
   * @return codiceTassonomia
  **/
  @XmlElement(name="codiceTassonomia")
  public String getCodiceTassonomia() {
    return codiceTassonomia;
  }

  public void setCodiceTassonomia(String codiceTassonomia) {
    this.codiceTassonomia = codiceTassonomia;
  }

  public DominioDto codiceTassonomia(String codiceTassonomia) {
    this.codiceTassonomia = codiceTassonomia;
    return this;
  }

 /**
   * Get codiceTassonomiaPagopa
   * @return codiceTassonomiaPagopa
  **/
  @XmlElement(name="codiceTassonomiaPagopa")
  public String getCodiceTassonomiaPagopa() {
    return codiceTassonomiaPagopa;
  }

  public void setCodiceTassonomiaPagopa(String codiceTassonomiaPagopa) {
    this.codiceTassonomiaPagopa = codiceTassonomiaPagopa;
  }

  public DominioDto codiceTassonomiaPagopa(String codiceTassonomiaPagopa) {
    this.codiceTassonomiaPagopa = codiceTassonomiaPagopa;
    return this;
  }

 /**
   * Get descrizioneServizio
   * @return descrizioneServizio
  **/
  @XmlElement(name="descrizioneServizio")
  public String getDescrizioneServizio() {
    return descrizioneServizio;
  }

  public void setDescrizioneServizio(String descrizioneServizio) {
    this.descrizioneServizio = descrizioneServizio;
  }

  public DominioDto descrizioneServizio(String descrizioneServizio) {
    this.descrizioneServizio = descrizioneServizio;
    return this;
  }

 /**
   * Get idDominio
   * @return idDominio
  **/
  @XmlElement(name="idDominio")
  public Long getIdDominio() {
    return idDominio;
  }

  public void setIdDominio(Long idDominio) {
    this.idDominio = idDominio;
  }

  public DominioDto idDominio(Long idDominio) {
    this.idDominio = idDominio;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="idEnte")
  public Long getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(Long idEnte) {
    this.idEnte = idEnte;
  }

  public DominioDto idEnte(Long idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get idServizio
   * @return idServizio
  **/
  @XmlElement(name="idServizio")
  public Long getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(Long idServizio) {
    this.idServizio = idServizio;
  }

  public DominioDto idServizio(Long idServizio) {
    this.idServizio = idServizio;
    return this;
  }

 /**
   * Get pagamentoTelematico
   * @return pagamentoTelematico
  **/
  @XmlElement(name="pagamentoTelematico")
  public Integer getPagamentoTelematico() {
    return pagamentoTelematico;
  }

  public void setPagamentoTelematico(Integer pagamentoTelematico) {
    this.pagamentoTelematico = pagamentoTelematico;
  }

  public DominioDto pagamentoTelematico(Integer pagamentoTelematico) {
    this.pagamentoTelematico = pagamentoTelematico;
    return this;
  }

 /**
   * Get passwordServizio
   * @return passwordServizio
  **/
  @XmlElement(name="passwordServizio")
  public String getPasswordServizio() {
    return passwordServizio;
  }

  public void setPasswordServizio(String passwordServizio) {
    this.passwordServizio = passwordServizio;
  }

  public DominioDto passwordServizio(String passwordServizio) {
    this.passwordServizio = passwordServizio;
    return this;
  }

 /**
   * Get passwordWs
   * @return passwordWs
  **/
  @XmlElement(name="passwordWs")
  public String getPasswordWs() {
    return passwordWs;
  }

  public void setPasswordWs(String passwordWs) {
    this.passwordWs = passwordWs;
  }

  public DominioDto passwordWs(String passwordWs) {
    this.passwordWs = passwordWs;
    return this;
  }

 /**
   * Get tipoServizio
   * @return tipoServizio
  **/
  @XmlElement(name="tipoServizio")
  public String getTipoServizio() {
    return tipoServizio;
  }

  public void setTipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
  }

  public DominioDto tipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
    return this;
  }

 /**
   * Get userServizio
   * @return userServizio
  **/
  @XmlElement(name="userServizio")
  public String getUserServizio() {
    return userServizio;
  }

  public void setUserServizio(String userServizio) {
    this.userServizio = userServizio;
  }

  public DominioDto userServizio(String userServizio) {
    this.userServizio = userServizio;
    return this;
  }

 /**
   * Get userWs
   * @return userWs
  **/
  @XmlElement(name="userWs")
  public String getUserWs() {
    return userWs;
  }

  public void setUserWs(String userWs) {
    this.userWs = userWs;
  }

  public DominioDto userWs(String userWs) {
    this.userWs = userWs;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DominioDto {\n");
    
    sb.append("    attivo: ").append(toIndentedString(attivo)).append("\n");
    sb.append("    codiceServizioEnte: ").append(toIndentedString(codiceServizioEnte)).append("\n");
    sb.append("    codiceTassonomia: ").append(toIndentedString(codiceTassonomia)).append("\n");
    sb.append("    codiceTassonomiaPagopa: ").append(toIndentedString(codiceTassonomiaPagopa)).append("\n");
    sb.append("    descrizioneServizio: ").append(toIndentedString(descrizioneServizio)).append("\n");
    sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    idServizio: ").append(toIndentedString(idServizio)).append("\n");
    sb.append("    pagamentoTelematico: ").append(toIndentedString(pagamentoTelematico)).append("\n");
    sb.append("    passwordServizio: ").append(toIndentedString(passwordServizio)).append("\n");
    sb.append("    passwordWs: ").append(toIndentedString(passwordWs)).append("\n");
    sb.append("    tipoServizio: ").append(toIndentedString(tipoServizio)).append("\n");
    sb.append("    userServizio: ").append(toIndentedString(userServizio)).append("\n");
    sb.append("    userWs: ").append(toIndentedString(userWs)).append("\n");
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

