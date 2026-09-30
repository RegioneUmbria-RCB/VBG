package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class RichiestaRevoca  {
  
  
  private Date dataCreazione = null;

  
  private Date dataRevoca = null;

  
  private Integer errorCodeProcedura = null;

  
  private String errorTextProcedura = null;

  
  private Long idDominio = null;

  
  private Long idPadreRateizzazione = null;

  
  private String idRichiesta = null;

  
  private String idRichiestaPadre = null;

  
  private Double importo = null;

  
  private String iuv = null;

  
  private Long padreRateizzazione = null;

  
  private String provenienza = null;

  
  private Integer statoNotifica = null;

  
  private Integer tipoRevoca = null;

  
  private byte[] xml = null;

  
  private byte[] xmlNonNspace = null;
 /**
   * Get dataCreazione
   * @return dataCreazione
  **/
  @XmlElement(name="dataCreazione")
  public Date getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public RichiestaRevoca dataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
    return this;
  }

 /**
   * Get dataRevoca
   * @return dataRevoca
  **/
  @XmlElement(name="dataRevoca")
  public Date getDataRevoca() {
    return dataRevoca;
  }

  public void setDataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
  }

  public RichiestaRevoca dataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
    return this;
  }

 /**
   * Get errorCodeProcedura
   * @return errorCodeProcedura
  **/
  @XmlElement(name="errorCodeProcedura")
  public Integer getErrorCodeProcedura() {
    return errorCodeProcedura;
  }

  public void setErrorCodeProcedura(Integer errorCodeProcedura) {
    this.errorCodeProcedura = errorCodeProcedura;
  }

  public RichiestaRevoca errorCodeProcedura(Integer errorCodeProcedura) {
    this.errorCodeProcedura = errorCodeProcedura;
    return this;
  }

 /**
   * Get errorTextProcedura
   * @return errorTextProcedura
  **/
  @XmlElement(name="errorTextProcedura")
  public String getErrorTextProcedura() {
    return errorTextProcedura;
  }

  public void setErrorTextProcedura(String errorTextProcedura) {
    this.errorTextProcedura = errorTextProcedura;
  }

  public RichiestaRevoca errorTextProcedura(String errorTextProcedura) {
    this.errorTextProcedura = errorTextProcedura;
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

  public RichiestaRevoca idDominio(Long idDominio) {
    this.idDominio = idDominio;
    return this;
  }

 /**
   * Get idPadreRateizzazione
   * @return idPadreRateizzazione
  **/
  @XmlElement(name="idPadreRateizzazione")
  public Long getIdPadreRateizzazione() {
    return idPadreRateizzazione;
  }

  public void setIdPadreRateizzazione(Long idPadreRateizzazione) {
    this.idPadreRateizzazione = idPadreRateizzazione;
  }

  public RichiestaRevoca idPadreRateizzazione(Long idPadreRateizzazione) {
    this.idPadreRateizzazione = idPadreRateizzazione;
    return this;
  }

 /**
   * Get idRichiesta
   * @return idRichiesta
  **/
  @XmlElement(name="idRichiesta")
  public String getIdRichiesta() {
    return idRichiesta;
  }

  public void setIdRichiesta(String idRichiesta) {
    this.idRichiesta = idRichiesta;
  }

  public RichiestaRevoca idRichiesta(String idRichiesta) {
    this.idRichiesta = idRichiesta;
    return this;
  }

 /**
   * Get idRichiestaPadre
   * @return idRichiestaPadre
  **/
  @XmlElement(name="idRichiestaPadre")
  public String getIdRichiestaPadre() {
    return idRichiestaPadre;
  }

  public void setIdRichiestaPadre(String idRichiestaPadre) {
    this.idRichiestaPadre = idRichiestaPadre;
  }

  public RichiestaRevoca idRichiestaPadre(String idRichiestaPadre) {
    this.idRichiestaPadre = idRichiestaPadre;
    return this;
  }

 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double importo) {
    this.importo = importo;
  }

  public RichiestaRevoca importo(Double importo) {
    this.importo = importo;
    return this;
  }

 /**
   * Get iuv
   * @return iuv
  **/
  @XmlElement(name="iuv")
  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public RichiestaRevoca iuv(String iuv) {
    this.iuv = iuv;
    return this;
  }

 /**
   * Get padreRateizzazione
   * @return padreRateizzazione
  **/
  @XmlElement(name="padreRateizzazione")
  public Long getPadreRateizzazione() {
    return padreRateizzazione;
  }

  public void setPadreRateizzazione(Long padreRateizzazione) {
    this.padreRateizzazione = padreRateizzazione;
  }

  public RichiestaRevoca padreRateizzazione(Long padreRateizzazione) {
    this.padreRateizzazione = padreRateizzazione;
    return this;
  }

 /**
   * Get provenienza
   * @return provenienza
  **/
  @XmlElement(name="provenienza")
  public String getProvenienza() {
    return provenienza;
  }

  public void setProvenienza(String provenienza) {
    this.provenienza = provenienza;
  }

  public RichiestaRevoca provenienza(String provenienza) {
    this.provenienza = provenienza;
    return this;
  }

 /**
   * Get statoNotifica
   * @return statoNotifica
  **/
  @XmlElement(name="statoNotifica")
  public Integer getStatoNotifica() {
    return statoNotifica;
  }

  public void setStatoNotifica(Integer statoNotifica) {
    this.statoNotifica = statoNotifica;
  }

  public RichiestaRevoca statoNotifica(Integer statoNotifica) {
    this.statoNotifica = statoNotifica;
    return this;
  }

 /**
   * Get tipoRevoca
   * @return tipoRevoca
  **/
  @XmlElement(name="tipoRevoca")
  public Integer getTipoRevoca() {
    return tipoRevoca;
  }

  public void setTipoRevoca(Integer tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
  }

  public RichiestaRevoca tipoRevoca(Integer tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
    return this;
  }

 /**
   * Get xml
   * @return xml
  **/
  @XmlElement(name="xml")
  public byte[] getXml() {
    return xml;
  }

  public void setXml(byte[] xml) {
    this.xml = xml;
  }

  public RichiestaRevoca xml(byte[] xml) {
    this.xml = xml;
    return this;
  }

 /**
   * Get xmlNonNspace
   * @return xmlNonNspace
  **/
  @XmlElement(name="xmlNonNspace")
  public byte[] getXmlNonNspace() {
    return xmlNonNspace;
  }

  public void setXmlNonNspace(byte[] xmlNonNspace) {
    this.xmlNonNspace = xmlNonNspace;
  }

  public RichiestaRevoca xmlNonNspace(byte[] xmlNonNspace) {
    this.xmlNonNspace = xmlNonNspace;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaRevoca {\n");
    
    sb.append("    dataCreazione: ").append(toIndentedString(dataCreazione)).append("\n");
    sb.append("    dataRevoca: ").append(toIndentedString(dataRevoca)).append("\n");
    sb.append("    errorCodeProcedura: ").append(toIndentedString(errorCodeProcedura)).append("\n");
    sb.append("    errorTextProcedura: ").append(toIndentedString(errorTextProcedura)).append("\n");
    sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
    sb.append("    idPadreRateizzazione: ").append(toIndentedString(idPadreRateizzazione)).append("\n");
    sb.append("    idRichiesta: ").append(toIndentedString(idRichiesta)).append("\n");
    sb.append("    idRichiestaPadre: ").append(toIndentedString(idRichiestaPadre)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
    sb.append("    padreRateizzazione: ").append(toIndentedString(padreRateizzazione)).append("\n");
    sb.append("    provenienza: ").append(toIndentedString(provenienza)).append("\n");
    sb.append("    statoNotifica: ").append(toIndentedString(statoNotifica)).append("\n");
    sb.append("    tipoRevoca: ").append(toIndentedString(tipoRevoca)).append("\n");
    sb.append("    xml: ").append(toIndentedString(xml)).append("\n");
    sb.append("    xmlNonNspace: ").append(toIndentedString(xmlNonNspace)).append("\n");
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

