package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class TassonomieImportDto  {
  
  
  private String codiceTipoEnteCreditore = null;

  
  private String codiceTipologiaServizio = null;

  
  private Date dataFineValidita = null;

  
  private Date dataInizioValidita = null;

  
  private String datiSpecificiIncasso = null;

  
  private String descrizioneMacroArea = null;

  
  private String descrizioneTipoServizio = null;

  
  private String motivoGiuridicoRiscossione = null;

  
  private String nomeMacroArea = null;

  
  private String progressivoMacroAreaEnteCreditore = null;

  
  private String tipoEnteCreditore = null;

  
  private String tipoServizio = null;

  
  private String versioneTassonomia = null;
 /**
   * Get codiceTipoEnteCreditore
   * @return codiceTipoEnteCreditore
  **/
  @XmlElement(name="codiceTipoEnteCreditore")
  public String getCodiceTipoEnteCreditore() {
    return codiceTipoEnteCreditore;
  }

  public void setCodiceTipoEnteCreditore(String codiceTipoEnteCreditore) {
    this.codiceTipoEnteCreditore = codiceTipoEnteCreditore;
  }

  public TassonomieImportDto codiceTipoEnteCreditore(String codiceTipoEnteCreditore) {
    this.codiceTipoEnteCreditore = codiceTipoEnteCreditore;
    return this;
  }

 /**
   * Get codiceTipologiaServizio
   * @return codiceTipologiaServizio
  **/
  @XmlElement(name="codiceTipologiaServizio")
  public String getCodiceTipologiaServizio() {
    return codiceTipologiaServizio;
  }

  public void setCodiceTipologiaServizio(String codiceTipologiaServizio) {
    this.codiceTipologiaServizio = codiceTipologiaServizio;
  }

  public TassonomieImportDto codiceTipologiaServizio(String codiceTipologiaServizio) {
    this.codiceTipologiaServizio = codiceTipologiaServizio;
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

  public TassonomieImportDto dataFineValidita(Date dataFineValidita) {
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

  public TassonomieImportDto dataInizioValidita(Date dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
    return this;
  }

 /**
   * Get datiSpecificiIncasso
   * @return datiSpecificiIncasso
  **/
  @XmlElement(name="datiSpecificiIncasso")
  public String getDatiSpecificiIncasso() {
    return datiSpecificiIncasso;
  }

  public void setDatiSpecificiIncasso(String datiSpecificiIncasso) {
    this.datiSpecificiIncasso = datiSpecificiIncasso;
  }

  public TassonomieImportDto datiSpecificiIncasso(String datiSpecificiIncasso) {
    this.datiSpecificiIncasso = datiSpecificiIncasso;
    return this;
  }

 /**
   * Get descrizioneMacroArea
   * @return descrizioneMacroArea
  **/
  @XmlElement(name="descrizioneMacroArea")
  public String getDescrizioneMacroArea() {
    return descrizioneMacroArea;
  }

  public void setDescrizioneMacroArea(String descrizioneMacroArea) {
    this.descrizioneMacroArea = descrizioneMacroArea;
  }

  public TassonomieImportDto descrizioneMacroArea(String descrizioneMacroArea) {
    this.descrizioneMacroArea = descrizioneMacroArea;
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

  public TassonomieImportDto descrizioneTipoServizio(String descrizioneTipoServizio) {
    this.descrizioneTipoServizio = descrizioneTipoServizio;
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

  public TassonomieImportDto motivoGiuridicoRiscossione(String motivoGiuridicoRiscossione) {
    this.motivoGiuridicoRiscossione = motivoGiuridicoRiscossione;
    return this;
  }

 /**
   * Get nomeMacroArea
   * @return nomeMacroArea
  **/
  @XmlElement(name="nomeMacroArea")
  public String getNomeMacroArea() {
    return nomeMacroArea;
  }

  public void setNomeMacroArea(String nomeMacroArea) {
    this.nomeMacroArea = nomeMacroArea;
  }

  public TassonomieImportDto nomeMacroArea(String nomeMacroArea) {
    this.nomeMacroArea = nomeMacroArea;
    return this;
  }

 /**
   * Get progressivoMacroAreaEnteCreditore
   * @return progressivoMacroAreaEnteCreditore
  **/
  @XmlElement(name="progressivoMacroAreaEnteCreditore")
  public String getProgressivoMacroAreaEnteCreditore() {
    return progressivoMacroAreaEnteCreditore;
  }

  public void setProgressivoMacroAreaEnteCreditore(String progressivoMacroAreaEnteCreditore) {
    this.progressivoMacroAreaEnteCreditore = progressivoMacroAreaEnteCreditore;
  }

  public TassonomieImportDto progressivoMacroAreaEnteCreditore(String progressivoMacroAreaEnteCreditore) {
    this.progressivoMacroAreaEnteCreditore = progressivoMacroAreaEnteCreditore;
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

  public TassonomieImportDto tipoEnteCreditore(String tipoEnteCreditore) {
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

  public TassonomieImportDto tipoServizio(String tipoServizio) {
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

  public TassonomieImportDto versioneTassonomia(String versioneTassonomia) {
    this.versioneTassonomia = versioneTassonomia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TassonomieImportDto {\n");
    
    sb.append("    codiceTipoEnteCreditore: ").append(toIndentedString(codiceTipoEnteCreditore)).append("\n");
    sb.append("    codiceTipologiaServizio: ").append(toIndentedString(codiceTipologiaServizio)).append("\n");
    sb.append("    dataFineValidita: ").append(toIndentedString(dataFineValidita)).append("\n");
    sb.append("    dataInizioValidita: ").append(toIndentedString(dataInizioValidita)).append("\n");
    sb.append("    datiSpecificiIncasso: ").append(toIndentedString(datiSpecificiIncasso)).append("\n");
    sb.append("    descrizioneMacroArea: ").append(toIndentedString(descrizioneMacroArea)).append("\n");
    sb.append("    descrizioneTipoServizio: ").append(toIndentedString(descrizioneTipoServizio)).append("\n");
    sb.append("    motivoGiuridicoRiscossione: ").append(toIndentedString(motivoGiuridicoRiscossione)).append("\n");
    sb.append("    nomeMacroArea: ").append(toIndentedString(nomeMacroArea)).append("\n");
    sb.append("    progressivoMacroAreaEnteCreditore: ").append(toIndentedString(progressivoMacroAreaEnteCreditore)).append("\n");
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

