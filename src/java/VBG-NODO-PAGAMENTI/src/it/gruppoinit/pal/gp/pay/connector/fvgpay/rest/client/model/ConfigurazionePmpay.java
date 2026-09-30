package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ConfigurazionePmpay  {
  
  
  private Integer auxDigit = null;

  
  private String codiceAzienda = null;

  
  private String codiceIdentificativo = null;

  
  private String codiceSegregazioneEnte = null;

  
  private String cognomeRiffEnte = null;

  
  private String divisa = null;

  
  private String emailRiffEnte = null;

  
  private String endpointPagoPmpay = null;

  
  private String endpointTicketWeb = null;

  
  private Long id = null;

  
  private String idClient = null;

  
  private Long idDominio = null;

  
  private Long idEnte = null;

  
  private String identificativoEnte = null;

  
  private String nomeRiffEnte = null;

  
  private String pwdClient = null;

  
  private String servizioPagamenti = null;

  
  private String telefonoRiffEnte = null;

  
  private String tipoClient = null;

  
  private String tipoContabilita = null;

  
  private String urlCancel = null;

  
  private String urlKo = null;

  
  private String urlOk = null;

  
  private String urlS2s = null;

  
  private String versione = null;
 /**
   * Get auxDigit
   * @return auxDigit
  **/
  @XmlElement(name="auxDigit")
  public Integer getAuxDigit() {
    return auxDigit;
  }

  public void setAuxDigit(Integer auxDigit) {
    this.auxDigit = auxDigit;
  }

  public ConfigurazionePmpay auxDigit(Integer auxDigit) {
    this.auxDigit = auxDigit;
    return this;
  }

 /**
   * Get codiceAzienda
   * @return codiceAzienda
  **/
  @XmlElement(name="codiceAzienda")
  public String getCodiceAzienda() {
    return codiceAzienda;
  }

  public void setCodiceAzienda(String codiceAzienda) {
    this.codiceAzienda = codiceAzienda;
  }

  public ConfigurazionePmpay codiceAzienda(String codiceAzienda) {
    this.codiceAzienda = codiceAzienda;
    return this;
  }

 /**
   * Get codiceIdentificativo
   * @return codiceIdentificativo
  **/
  @XmlElement(name="codiceIdentificativo")
  public String getCodiceIdentificativo() {
    return codiceIdentificativo;
  }

  public void setCodiceIdentificativo(String codiceIdentificativo) {
    this.codiceIdentificativo = codiceIdentificativo;
  }

  public ConfigurazionePmpay codiceIdentificativo(String codiceIdentificativo) {
    this.codiceIdentificativo = codiceIdentificativo;
    return this;
  }

 /**
   * Get codiceSegregazioneEnte
   * @return codiceSegregazioneEnte
  **/
  @XmlElement(name="codiceSegregazioneEnte")
  public String getCodiceSegregazioneEnte() {
    return codiceSegregazioneEnte;
  }

  public void setCodiceSegregazioneEnte(String codiceSegregazioneEnte) {
    this.codiceSegregazioneEnte = codiceSegregazioneEnte;
  }

  public ConfigurazionePmpay codiceSegregazioneEnte(String codiceSegregazioneEnte) {
    this.codiceSegregazioneEnte = codiceSegregazioneEnte;
    return this;
  }

 /**
   * Get cognomeRiffEnte
   * @return cognomeRiffEnte
  **/
  @XmlElement(name="cognomeRiffEnte")
  public String getCognomeRiffEnte() {
    return cognomeRiffEnte;
  }

  public void setCognomeRiffEnte(String cognomeRiffEnte) {
    this.cognomeRiffEnte = cognomeRiffEnte;
  }

  public ConfigurazionePmpay cognomeRiffEnte(String cognomeRiffEnte) {
    this.cognomeRiffEnte = cognomeRiffEnte;
    return this;
  }

 /**
   * Get divisa
   * @return divisa
  **/
  @XmlElement(name="divisa")
  public String getDivisa() {
    return divisa;
  }

  public void setDivisa(String divisa) {
    this.divisa = divisa;
  }

  public ConfigurazionePmpay divisa(String divisa) {
    this.divisa = divisa;
    return this;
  }

 /**
   * Get emailRiffEnte
   * @return emailRiffEnte
  **/
  @XmlElement(name="emailRiffEnte")
  public String getEmailRiffEnte() {
    return emailRiffEnte;
  }

  public void setEmailRiffEnte(String emailRiffEnte) {
    this.emailRiffEnte = emailRiffEnte;
  }

  public ConfigurazionePmpay emailRiffEnte(String emailRiffEnte) {
    this.emailRiffEnte = emailRiffEnte;
    return this;
  }

 /**
   * Get endpointPagoPmpay
   * @return endpointPagoPmpay
  **/
  @XmlElement(name="endpointPagoPmpay")
  public String getEndpointPagoPmpay() {
    return endpointPagoPmpay;
  }

  public void setEndpointPagoPmpay(String endpointPagoPmpay) {
    this.endpointPagoPmpay = endpointPagoPmpay;
  }

  public ConfigurazionePmpay endpointPagoPmpay(String endpointPagoPmpay) {
    this.endpointPagoPmpay = endpointPagoPmpay;
    return this;
  }

 /**
   * Get endpointTicketWeb
   * @return endpointTicketWeb
  **/
  @XmlElement(name="endpointTicketWeb")
  public String getEndpointTicketWeb() {
    return endpointTicketWeb;
  }

  public void setEndpointTicketWeb(String endpointTicketWeb) {
    this.endpointTicketWeb = endpointTicketWeb;
  }

  public ConfigurazionePmpay endpointTicketWeb(String endpointTicketWeb) {
    this.endpointTicketWeb = endpointTicketWeb;
    return this;
  }

 /**
   * Get id
   * @return id
  **/
  @XmlElement(name="id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ConfigurazionePmpay id(Long id) {
    this.id = id;
    return this;
  }

 /**
   * Get idClient
   * @return idClient
  **/
  @XmlElement(name="idClient")
  public String getIdClient() {
    return idClient;
  }

  public void setIdClient(String idClient) {
    this.idClient = idClient;
  }

  public ConfigurazionePmpay idClient(String idClient) {
    this.idClient = idClient;
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

  public ConfigurazionePmpay idDominio(Long idDominio) {
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

  public ConfigurazionePmpay idEnte(Long idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get identificativoEnte
   * @return identificativoEnte
  **/
  @XmlElement(name="identificativoEnte")
  public String getIdentificativoEnte() {
    return identificativoEnte;
  }

  public void setIdentificativoEnte(String identificativoEnte) {
    this.identificativoEnte = identificativoEnte;
  }

  public ConfigurazionePmpay identificativoEnte(String identificativoEnte) {
    this.identificativoEnte = identificativoEnte;
    return this;
  }

 /**
   * Get nomeRiffEnte
   * @return nomeRiffEnte
  **/
  @XmlElement(name="nomeRiffEnte")
  public String getNomeRiffEnte() {
    return nomeRiffEnte;
  }

  public void setNomeRiffEnte(String nomeRiffEnte) {
    this.nomeRiffEnte = nomeRiffEnte;
  }

  public ConfigurazionePmpay nomeRiffEnte(String nomeRiffEnte) {
    this.nomeRiffEnte = nomeRiffEnte;
    return this;
  }

 /**
   * Get pwdClient
   * @return pwdClient
  **/
  @XmlElement(name="pwdClient")
  public String getPwdClient() {
    return pwdClient;
  }

  public void setPwdClient(String pwdClient) {
    this.pwdClient = pwdClient;
  }

  public ConfigurazionePmpay pwdClient(String pwdClient) {
    this.pwdClient = pwdClient;
    return this;
  }

 /**
   * Get servizioPagamenti
   * @return servizioPagamenti
  **/
  @XmlElement(name="servizioPagamenti")
  public String getServizioPagamenti() {
    return servizioPagamenti;
  }

  public void setServizioPagamenti(String servizioPagamenti) {
    this.servizioPagamenti = servizioPagamenti;
  }

  public ConfigurazionePmpay servizioPagamenti(String servizioPagamenti) {
    this.servizioPagamenti = servizioPagamenti;
    return this;
  }

 /**
   * Get telefonoRiffEnte
   * @return telefonoRiffEnte
  **/
  @XmlElement(name="telefonoRiffEnte")
  public String getTelefonoRiffEnte() {
    return telefonoRiffEnte;
  }

  public void setTelefonoRiffEnte(String telefonoRiffEnte) {
    this.telefonoRiffEnte = telefonoRiffEnte;
  }

  public ConfigurazionePmpay telefonoRiffEnte(String telefonoRiffEnte) {
    this.telefonoRiffEnte = telefonoRiffEnte;
    return this;
  }

 /**
   * Get tipoClient
   * @return tipoClient
  **/
  @XmlElement(name="tipoClient")
  public String getTipoClient() {
    return tipoClient;
  }

  public void setTipoClient(String tipoClient) {
    this.tipoClient = tipoClient;
  }

  public ConfigurazionePmpay tipoClient(String tipoClient) {
    this.tipoClient = tipoClient;
    return this;
  }

 /**
   * Get tipoContabilita
   * @return tipoContabilita
  **/
  @XmlElement(name="tipoContabilita")
  public String getTipoContabilita() {
    return tipoContabilita;
  }

  public void setTipoContabilita(String tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
  }

  public ConfigurazionePmpay tipoContabilita(String tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
    return this;
  }

 /**
   * Get urlCancel
   * @return urlCancel
  **/
  @XmlElement(name="urlCancel")
  public String getUrlCancel() {
    return urlCancel;
  }

  public void setUrlCancel(String urlCancel) {
    this.urlCancel = urlCancel;
  }

  public ConfigurazionePmpay urlCancel(String urlCancel) {
    this.urlCancel = urlCancel;
    return this;
  }

 /**
   * Get urlKo
   * @return urlKo
  **/
  @XmlElement(name="urlKo")
  public String getUrlKo() {
    return urlKo;
  }

  public void setUrlKo(String urlKo) {
    this.urlKo = urlKo;
  }

  public ConfigurazionePmpay urlKo(String urlKo) {
    this.urlKo = urlKo;
    return this;
  }

 /**
   * Get urlOk
   * @return urlOk
  **/
  @XmlElement(name="urlOk")
  public String getUrlOk() {
    return urlOk;
  }

  public void setUrlOk(String urlOk) {
    this.urlOk = urlOk;
  }

  public ConfigurazionePmpay urlOk(String urlOk) {
    this.urlOk = urlOk;
    return this;
  }

 /**
   * Get urlS2s
   * @return urlS2s
  **/
  @XmlElement(name="urlS2s")
  public String getUrlS2s() {
    return urlS2s;
  }

  public void setUrlS2s(String urlS2s) {
    this.urlS2s = urlS2s;
  }

  public ConfigurazionePmpay urlS2s(String urlS2s) {
    this.urlS2s = urlS2s;
    return this;
  }

 /**
   * Get versione
   * @return versione
  **/
  @XmlElement(name="versione")
  public String getVersione() {
    return versione;
  }

  public void setVersione(String versione) {
    this.versione = versione;
  }

  public ConfigurazionePmpay versione(String versione) {
    this.versione = versione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfigurazionePmpay {\n");
    
    sb.append("    auxDigit: ").append(toIndentedString(auxDigit)).append("\n");
    sb.append("    codiceAzienda: ").append(toIndentedString(codiceAzienda)).append("\n");
    sb.append("    codiceIdentificativo: ").append(toIndentedString(codiceIdentificativo)).append("\n");
    sb.append("    codiceSegregazioneEnte: ").append(toIndentedString(codiceSegregazioneEnte)).append("\n");
    sb.append("    cognomeRiffEnte: ").append(toIndentedString(cognomeRiffEnte)).append("\n");
    sb.append("    divisa: ").append(toIndentedString(divisa)).append("\n");
    sb.append("    emailRiffEnte: ").append(toIndentedString(emailRiffEnte)).append("\n");
    sb.append("    endpointPagoPmpay: ").append(toIndentedString(endpointPagoPmpay)).append("\n");
    sb.append("    endpointTicketWeb: ").append(toIndentedString(endpointTicketWeb)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idClient: ").append(toIndentedString(idClient)).append("\n");
    sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    identificativoEnte: ").append(toIndentedString(identificativoEnte)).append("\n");
    sb.append("    nomeRiffEnte: ").append(toIndentedString(nomeRiffEnte)).append("\n");
    sb.append("    pwdClient: ").append(toIndentedString(pwdClient)).append("\n");
    sb.append("    servizioPagamenti: ").append(toIndentedString(servizioPagamenti)).append("\n");
    sb.append("    telefonoRiffEnte: ").append(toIndentedString(telefonoRiffEnte)).append("\n");
    sb.append("    tipoClient: ").append(toIndentedString(tipoClient)).append("\n");
    sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
    sb.append("    urlCancel: ").append(toIndentedString(urlCancel)).append("\n");
    sb.append("    urlKo: ").append(toIndentedString(urlKo)).append("\n");
    sb.append("    urlOk: ").append(toIndentedString(urlOk)).append("\n");
    sb.append("    urlS2s: ").append(toIndentedString(urlS2s)).append("\n");
    sb.append("    versione: ").append(toIndentedString(versione)).append("\n");
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

