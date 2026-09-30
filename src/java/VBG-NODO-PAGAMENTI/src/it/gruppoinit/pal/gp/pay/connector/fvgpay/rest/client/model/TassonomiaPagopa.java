package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class TassonomiaPagopa  {
  
  
  private String codice = null;

  
  private String codicePagopa = null;

  
  private String codiceTipoServizio = null;

  
  private Date dataFineValidita = null;

  
  private Date dataInizioValidita = null;

  
  private String descrizioneTipoServizio = null;

  
  private Long idMacroArea = null;

  
  private Long idTassonomiaPagopa = null;

  
  private String motivoGiuridicoRiscossione = null;

  
  private Long specializzazioneIdEnte = null;

  
  private String tipoEnteCreditore = null;

  
  private String tipoServizio = null;

  
  private String versioneTassonomia = null;
 /**
   * Get codice
   * @return codice
  **/
  @XmlElement(name="codice")
  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public TassonomiaPagopa codice(String codice) {
    this.codice = codice;
    return this;
  }

 /**
   * Get codicePagopa
   * @return codicePagopa
  **/
  @XmlElement(name="codicePagopa")
  public String getCodicePagopa() {
    return codicePagopa;
  }

  public void setCodicePagopa(String codicePagopa) {
    this.codicePagopa = codicePagopa;
  }

  public TassonomiaPagopa codicePagopa(String codicePagopa) {
    this.codicePagopa = codicePagopa;
    return this;
  }

 /**
   * Get codiceTipoServizio
   * @return codiceTipoServizio
  **/
  @XmlElement(name="codiceTipoServizio")
  public String getCodiceTipoServizio() {
    return codiceTipoServizio;
  }

  public void setCodiceTipoServizio(String codiceTipoServizio) {
    this.codiceTipoServizio = codiceTipoServizio;
  }

  public TassonomiaPagopa codiceTipoServizio(String codiceTipoServizio) {
    this.codiceTipoServizio = codiceTipoServizio;
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

  public TassonomiaPagopa dataFineValidita(Date dataFineValidita) {
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

  public TassonomiaPagopa dataInizioValidita(Date dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
    return this;
  }

 /**
   * Get descrizioneTipoServizio
   * @return descrizioneTipoServizio
  **/
  @XmlElement(name="descrizioneTipoServizio")
  public String getDescrizioneTipoServizio() {
    return descrizioneTipoServizio;
  }

  public void setDescrizioneTipoServizio(String descrizioneTipoServizio) {
    this.descrizioneTipoServizio = descrizioneTipoServizio;
  }

  public TassonomiaPagopa descrizioneTipoServizio(String descrizioneTipoServizio) {
    this.descrizioneTipoServizio = descrizioneTipoServizio;
    return this;
  }

 /**
   * Get idMacroArea
   * @return idMacroArea
  **/
  @XmlElement(name="idMacroArea")
  public Long getIdMacroArea() {
    return idMacroArea;
  }

  public void setIdMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
  }

  public TassonomiaPagopa idMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
    return this;
  }

 /**
   * Get idTassonomiaPagopa
   * @return idTassonomiaPagopa
  **/
  @XmlElement(name="idTassonomiaPagopa")
  public Long getIdTassonomiaPagopa() {
    return idTassonomiaPagopa;
  }

  public void setIdTassonomiaPagopa(Long idTassonomiaPagopa) {
    this.idTassonomiaPagopa = idTassonomiaPagopa;
  }

  public TassonomiaPagopa idTassonomiaPagopa(Long idTassonomiaPagopa) {
    this.idTassonomiaPagopa = idTassonomiaPagopa;
    return this;
  }

 /**
   * Get motivoGiuridicoRiscossione
   * @return motivoGiuridicoRiscossione
  **/
  @XmlElement(name="motivoGiuridicoRiscossione")
  public String getMotivoGiuridicoRiscossione() {
    return motivoGiuridicoRiscossione;
  }

  public void setMotivoGiuridicoRiscossione(String motivoGiuridicoRiscossione) {
    this.motivoGiuridicoRiscossione = motivoGiuridicoRiscossione;
  }

  public TassonomiaPagopa motivoGiuridicoRiscossione(String motivoGiuridicoRiscossione) {
    this.motivoGiuridicoRiscossione = motivoGiuridicoRiscossione;
    return this;
  }

 /**
   * Get specializzazioneIdEnte
   * @return specializzazioneIdEnte
  **/
  @XmlElement(name="specializzazioneIdEnte")
  public Long getSpecializzazioneIdEnte() {
    return specializzazioneIdEnte;
  }

  public void setSpecializzazioneIdEnte(Long specializzazioneIdEnte) {
    this.specializzazioneIdEnte = specializzazioneIdEnte;
  }

  public TassonomiaPagopa specializzazioneIdEnte(Long specializzazioneIdEnte) {
    this.specializzazioneIdEnte = specializzazioneIdEnte;
    return this;
  }

 /**
   * Get tipoEnteCreditore
   * @return tipoEnteCreditore
  **/
  @XmlElement(name="tipoEnteCreditore")
  public String getTipoEnteCreditore() {
    return tipoEnteCreditore;
  }

  public void setTipoEnteCreditore(String tipoEnteCreditore) {
    this.tipoEnteCreditore = tipoEnteCreditore;
  }

  public TassonomiaPagopa tipoEnteCreditore(String tipoEnteCreditore) {
    this.tipoEnteCreditore = tipoEnteCreditore;
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

  public TassonomiaPagopa tipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
    return this;
  }

 /**
   * Get versioneTassonomia
   * @return versioneTassonomia
  **/
  @XmlElement(name="versioneTassonomia")
  public String getVersioneTassonomia() {
    return versioneTassonomia;
  }

  public void setVersioneTassonomia(String versioneTassonomia) {
    this.versioneTassonomia = versioneTassonomia;
  }

  public TassonomiaPagopa versioneTassonomia(String versioneTassonomia) {
    this.versioneTassonomia = versioneTassonomia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TassonomiaPagopa {\n");
    
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    codicePagopa: ").append(toIndentedString(codicePagopa)).append("\n");
    sb.append("    codiceTipoServizio: ").append(toIndentedString(codiceTipoServizio)).append("\n");
    sb.append("    dataFineValidita: ").append(toIndentedString(dataFineValidita)).append("\n");
    sb.append("    dataInizioValidita: ").append(toIndentedString(dataInizioValidita)).append("\n");
    sb.append("    descrizioneTipoServizio: ").append(toIndentedString(descrizioneTipoServizio)).append("\n");
    sb.append("    idMacroArea: ").append(toIndentedString(idMacroArea)).append("\n");
    sb.append("    idTassonomiaPagopa: ").append(toIndentedString(idTassonomiaPagopa)).append("\n");
    sb.append("    motivoGiuridicoRiscossione: ").append(toIndentedString(motivoGiuridicoRiscossione)).append("\n");
    sb.append("    specializzazioneIdEnte: ").append(toIndentedString(specializzazioneIdEnte)).append("\n");
    sb.append("    tipoEnteCreditore: ").append(toIndentedString(tipoEnteCreditore)).append("\n");
    sb.append("    tipoServizio: ").append(toIndentedString(tipoServizio)).append("\n");
    sb.append("    versioneTassonomia: ").append(toIndentedString(versioneTassonomia)).append("\n");
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

