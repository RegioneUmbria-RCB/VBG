package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class DatiUpdatePosizioneDebitoriaType  {
  
  
  private String annoRiferimento = null;

  
 /**
   * La causale della singola posizione debitoria          
  **/
  private String causale = null;

  
  private Date dataPubblicazioneAvviso = null;

  
  private Date dataScadenzaAvviso = null;

  
  private Date dataScadenzaPagamento = null;

  
  private ImportoType importo = null;
 /**
   * Get annoRiferimento
   * @return annoRiferimento
  **/
  @XmlElement(name="anno_riferimento")
  public String getAnnoRiferimento() {
    return annoRiferimento;
  }

  public void setAnnoRiferimento(String annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
  }

  public DatiUpdatePosizioneDebitoriaType annoRiferimento(String annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
    return this;
  }

 /**
   * La causale della singola posizione debitoria          
   * @return causale
  **/
  @XmlElement(name="causale")
  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public DatiUpdatePosizioneDebitoriaType causale(String causale) {
    this.causale = causale;
    return this;
  }

 /**
   * Get dataPubblicazioneAvviso
   * @return dataPubblicazioneAvviso
  **/
  @XmlElement(name="data_pubblicazione_avviso")
  public Date getDataPubblicazioneAvviso() {
    return dataPubblicazioneAvviso;
  }

  public void setDataPubblicazioneAvviso(Date dataPubblicazioneAvviso) {
    this.dataPubblicazioneAvviso = dataPubblicazioneAvviso;
  }

  public DatiUpdatePosizioneDebitoriaType dataPubblicazioneAvviso(Date dataPubblicazioneAvviso) {
    this.dataPubblicazioneAvviso = dataPubblicazioneAvviso;
    return this;
  }

 /**
   * Get dataScadenzaAvviso
   * @return dataScadenzaAvviso
  **/
  @XmlElement(name="data_scadenza_avviso")
  public Date getDataScadenzaAvviso() {
    return dataScadenzaAvviso;
  }

  public void setDataScadenzaAvviso(Date dataScadenzaAvviso) {
    this.dataScadenzaAvviso = dataScadenzaAvviso;
  }

  public DatiUpdatePosizioneDebitoriaType dataScadenzaAvviso(Date dataScadenzaAvviso) {
    this.dataScadenzaAvviso = dataScadenzaAvviso;
    return this;
  }

 /**
   * Get dataScadenzaPagamento
   * @return dataScadenzaPagamento
  **/
  @XmlElement(name="data_scadenza_pagamento")
  public Date getDataScadenzaPagamento() {
    return dataScadenzaPagamento;
  }

  public void setDataScadenzaPagamento(Date dataScadenzaPagamento) {
    this.dataScadenzaPagamento = dataScadenzaPagamento;
  }

  public DatiUpdatePosizioneDebitoriaType dataScadenzaPagamento(Date dataScadenzaPagamento) {
    this.dataScadenzaPagamento = dataScadenzaPagamento;
    return this;
  }

 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public ImportoType getImporto() {
    return importo;
  }

  public void setImporto(ImportoType importo) {
    this.importo = importo;
  }

  public DatiUpdatePosizioneDebitoriaType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiUpdatePosizioneDebitoriaType {\n");
    
    sb.append("    annoRiferimento: ").append(toIndentedString(annoRiferimento)).append("\n");
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    dataPubblicazioneAvviso: ").append(toIndentedString(dataPubblicazioneAvviso)).append("\n");
    sb.append("    dataScadenzaAvviso: ").append(toIndentedString(dataScadenzaAvviso)).append("\n");
    sb.append("    dataScadenzaPagamento: ").append(toIndentedString(dataScadenzaPagamento)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
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

