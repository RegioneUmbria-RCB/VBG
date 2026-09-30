package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class AvvisiDiPagamentoPerIlServizioStampeDto  {
  
  
  private Integer annoRiferimento = null;

  
  private String cap = null;

  
  private Long cuspi = null;

  
  private Long cuspiA = null;

  
  private Long cuspiDa = null;

  
  private Date dataCreazioneA = null;

  
  private Date dataCreazioneDa = null;

  
  private Date dataFineValidita = null;

  
  private Date dataInizioValidita = null;

  
  private Date dataScadenzaAvvisoA = null;

  
  private Date dataScadenzaAvvisoDa = null;

  
  private Date dataScadenzaPagamentoA = null;

  
  private Date dataScadenzaPagamentoDa = null;

  
  private Long idDominio = null;

  
  private String idRichiesta = null;

  
  private byte[] pdf = null;

  
  private Integer tipoStampa = null;
 /**
   * Get annoRiferimento
   * @return annoRiferimento
  **/
  @XmlElement(name="annoRiferimento")
  public Integer getAnnoRiferimento() {
    return annoRiferimento;
  }

  public void setAnnoRiferimento(Integer annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto annoRiferimento(Integer annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
    return this;
  }

 /**
   * Get cap
   * @return cap
  **/
  @XmlElement(name="cap")
  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto cap(String cap) {
    this.cap = cap;
    return this;
  }

 /**
   * Get cuspi
   * @return cuspi
  **/
  @XmlElement(name="cuspi")
  public Long getCuspi() {
    return cuspi;
  }

  public void setCuspi(Long cuspi) {
    this.cuspi = cuspi;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto cuspi(Long cuspi) {
    this.cuspi = cuspi;
    return this;
  }

 /**
   * Get cuspiA
   * @return cuspiA
  **/
  @XmlElement(name="cuspiA")
  public Long getCuspiA() {
    return cuspiA;
  }

  public void setCuspiA(Long cuspiA) {
    this.cuspiA = cuspiA;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto cuspiA(Long cuspiA) {
    this.cuspiA = cuspiA;
    return this;
  }

 /**
   * Get cuspiDa
   * @return cuspiDa
  **/
  @XmlElement(name="cuspiDa")
  public Long getCuspiDa() {
    return cuspiDa;
  }

  public void setCuspiDa(Long cuspiDa) {
    this.cuspiDa = cuspiDa;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto cuspiDa(Long cuspiDa) {
    this.cuspiDa = cuspiDa;
    return this;
  }

 /**
   * Get dataCreazioneA
   * @return dataCreazioneA
  **/
  @XmlElement(name="dataCreazioneA")
  public Date getDataCreazioneA() {
    return dataCreazioneA;
  }

  public void setDataCreazioneA(Date dataCreazioneA) {
    this.dataCreazioneA = dataCreazioneA;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataCreazioneA(Date dataCreazioneA) {
    this.dataCreazioneA = dataCreazioneA;
    return this;
  }

 /**
   * Get dataCreazioneDa
   * @return dataCreazioneDa
  **/
  @XmlElement(name="dataCreazioneDa")
  public Date getDataCreazioneDa() {
    return dataCreazioneDa;
  }

  public void setDataCreazioneDa(Date dataCreazioneDa) {
    this.dataCreazioneDa = dataCreazioneDa;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataCreazioneDa(Date dataCreazioneDa) {
    this.dataCreazioneDa = dataCreazioneDa;
    return this;
  }

 /**
   * Get dataFineValidita
   * @return dataFineValidita
  **/
  @XmlElement(name="dataFineValidita")
  public Date getDataFineValidita() {
    return dataFineValidita;
  }

  public void setDataFineValidita(Date dataFineValidita) {
    this.dataFineValidita = dataFineValidita;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataFineValidita(Date dataFineValidita) {
    this.dataFineValidita = dataFineValidita;
    return this;
  }

 /**
   * Get dataInizioValidita
   * @return dataInizioValidita
  **/
  @XmlElement(name="dataInizioValidita")
  public Date getDataInizioValidita() {
    return dataInizioValidita;
  }

  public void setDataInizioValidita(Date dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataInizioValidita(Date dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
    return this;
  }

 /**
   * Get dataScadenzaAvvisoA
   * @return dataScadenzaAvvisoA
  **/
  @XmlElement(name="dataScadenzaAvvisoA")
  public Date getDataScadenzaAvvisoA() {
    return dataScadenzaAvvisoA;
  }

  public void setDataScadenzaAvvisoA(Date dataScadenzaAvvisoA) {
    this.dataScadenzaAvvisoA = dataScadenzaAvvisoA;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataScadenzaAvvisoA(Date dataScadenzaAvvisoA) {
    this.dataScadenzaAvvisoA = dataScadenzaAvvisoA;
    return this;
  }

 /**
   * Get dataScadenzaAvvisoDa
   * @return dataScadenzaAvvisoDa
  **/
  @XmlElement(name="dataScadenzaAvvisoDa")
  public Date getDataScadenzaAvvisoDa() {
    return dataScadenzaAvvisoDa;
  }

  public void setDataScadenzaAvvisoDa(Date dataScadenzaAvvisoDa) {
    this.dataScadenzaAvvisoDa = dataScadenzaAvvisoDa;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataScadenzaAvvisoDa(Date dataScadenzaAvvisoDa) {
    this.dataScadenzaAvvisoDa = dataScadenzaAvvisoDa;
    return this;
  }

 /**
   * Get dataScadenzaPagamentoA
   * @return dataScadenzaPagamentoA
  **/
  @XmlElement(name="dataScadenzaPagamentoA")
  public Date getDataScadenzaPagamentoA() {
    return dataScadenzaPagamentoA;
  }

  public void setDataScadenzaPagamentoA(Date dataScadenzaPagamentoA) {
    this.dataScadenzaPagamentoA = dataScadenzaPagamentoA;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataScadenzaPagamentoA(Date dataScadenzaPagamentoA) {
    this.dataScadenzaPagamentoA = dataScadenzaPagamentoA;
    return this;
  }

 /**
   * Get dataScadenzaPagamentoDa
   * @return dataScadenzaPagamentoDa
  **/
  @XmlElement(name="dataScadenzaPagamentoDa")
  public Date getDataScadenzaPagamentoDa() {
    return dataScadenzaPagamentoDa;
  }

  public void setDataScadenzaPagamentoDa(Date dataScadenzaPagamentoDa) {
    this.dataScadenzaPagamentoDa = dataScadenzaPagamentoDa;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto dataScadenzaPagamentoDa(Date dataScadenzaPagamentoDa) {
    this.dataScadenzaPagamentoDa = dataScadenzaPagamentoDa;
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

  public AvvisiDiPagamentoPerIlServizioStampeDto idDominio(Long idDominio) {
    this.idDominio = idDominio;
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

  public AvvisiDiPagamentoPerIlServizioStampeDto idRichiesta(String idRichiesta) {
    this.idRichiesta = idRichiesta;
    return this;
  }

 /**
   * Get pdf
   * @return pdf
  **/
  @XmlElement(name="pdf")
  public byte[] getPdf() {
    return pdf;
  }

  public void setPdf(byte[] pdf) {
    this.pdf = pdf;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto pdf(byte[] pdf) {
    this.pdf = pdf;
    return this;
  }

 /**
   * Get tipoStampa
   * @return tipoStampa
  **/
  @XmlElement(name="tipoStampa")
  public Integer getTipoStampa() {
    return tipoStampa;
  }

  public void setTipoStampa(Integer tipoStampa) {
    this.tipoStampa = tipoStampa;
  }

  public AvvisiDiPagamentoPerIlServizioStampeDto tipoStampa(Integer tipoStampa) {
    this.tipoStampa = tipoStampa;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvvisiDiPagamentoPerIlServizioStampeDto {\n");
    
    sb.append("    annoRiferimento: ").append(toIndentedString(annoRiferimento)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    cuspi: ").append(toIndentedString(cuspi)).append("\n");
    sb.append("    cuspiA: ").append(toIndentedString(cuspiA)).append("\n");
    sb.append("    cuspiDa: ").append(toIndentedString(cuspiDa)).append("\n");
    sb.append("    dataCreazioneA: ").append(toIndentedString(dataCreazioneA)).append("\n");
    sb.append("    dataCreazioneDa: ").append(toIndentedString(dataCreazioneDa)).append("\n");
    sb.append("    dataFineValidita: ").append(toIndentedString(dataFineValidita)).append("\n");
    sb.append("    dataInizioValidita: ").append(toIndentedString(dataInizioValidita)).append("\n");
    sb.append("    dataScadenzaAvvisoA: ").append(toIndentedString(dataScadenzaAvvisoA)).append("\n");
    sb.append("    dataScadenzaAvvisoDa: ").append(toIndentedString(dataScadenzaAvvisoDa)).append("\n");
    sb.append("    dataScadenzaPagamentoA: ").append(toIndentedString(dataScadenzaPagamentoA)).append("\n");
    sb.append("    dataScadenzaPagamentoDa: ").append(toIndentedString(dataScadenzaPagamentoDa)).append("\n");
    sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
    sb.append("    idRichiesta: ").append(toIndentedString(idRichiesta)).append("\n");
    sb.append("    pdf: ").append(toIndentedString(pdf)).append("\n");
    sb.append("    tipoStampa: ").append(toIndentedString(tipoStampa)).append("\n");
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

