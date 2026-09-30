package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
  * Per ogni voce di pagamento verranno indicati la data effettiva di pagamento e l'importo effettivamente pagato; se importo_pagato = 0.00 varra' obbligatoriamente indicata anche la motivazione del mancato pagamento; se la voce e' associata ad una richiesta di pagamento marca da bollo, dovra' essere valorizzato anche xml_bollo I dati vengono estratti dalla Ricevuta Telematica 
 **/

public class VoceRtType  {
  
  
 /**
   * Eventuale descrizione esito in formato nativo del PSP assente se richiesta_bollo non e' valorizzato 
  **/
  private String allegato = null;

  
  private ImportoType commissioniApplicatePsp = null;

  
 /**
   * Data in cui e' stato effettuato il pagamento
  **/
  private Date dataPagamento = null;

  
 /**
   * descrizione obbligatoria in caso di esito_pagamento negativo 
  **/
  private String descrizioneEsitoKo = null;

  
 /**
   * Riferimento univoco assegnato al pagamento dal PSP, di solito:   CRO o TRN nel caso di Bonifico Bancario   CODELINE nel caso di Bollettino Postale eventualmente uguale per tutte le voci di pagamento 
  **/
  private String identificativoUnivocoRiscossione = null;

  
  private ImportoType importo = null;

  
  private byte[] xmlBollo = null;
 /**
   * Eventuale descrizione esito in formato nativo del PSP assente se richiesta_bollo non e&#39; valorizzato 
   * @return allegato
  **/
  @XmlElement(name="allegato")
  public String getAllegato() {
    return allegato;
  }

  public void setAllegato(String allegato) {
    this.allegato = allegato;
  }

  public VoceRtType allegato(String allegato) {
    this.allegato = allegato;
    return this;
  }

 /**
   * Get commissioniApplicatePsp
   * @return commissioniApplicatePsp
  **/
  @XmlElement(name="commissioni_applicate_psp")
  public ImportoType getCommissioniApplicatePsp() {
    return commissioniApplicatePsp;
  }

  public void setCommissioniApplicatePsp(ImportoType commissioniApplicatePsp) {
    this.commissioniApplicatePsp = commissioniApplicatePsp;
  }

  public VoceRtType commissioniApplicatePsp(ImportoType commissioniApplicatePsp) {
    this.commissioniApplicatePsp = commissioniApplicatePsp;
    return this;
  }

 /**
   * Data in cui e&#39; stato effettuato il pagamento
   * @return dataPagamento
  **/
  @XmlElement(name="data_pagamento")
  public Date getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public VoceRtType dataPagamento(Date dataPagamento) {
    this.dataPagamento = dataPagamento;
    return this;
  }

 /**
   * descrizione obbligatoria in caso di esito_pagamento negativo 
   * @return descrizioneEsitoKo
  **/
  @XmlElement(name="descrizione_esito_ko")
  public String getDescrizioneEsitoKo() {
    return descrizioneEsitoKo;
  }

  public void setDescrizioneEsitoKo(String descrizioneEsitoKo) {
    this.descrizioneEsitoKo = descrizioneEsitoKo;
  }

  public VoceRtType descrizioneEsitoKo(String descrizioneEsitoKo) {
    this.descrizioneEsitoKo = descrizioneEsitoKo;
    return this;
  }

 /**
   * Riferimento univoco assegnato al pagamento dal PSP, di solito:   CRO o TRN nel caso di Bonifico Bancario   CODELINE nel caso di Bollettino Postale eventualmente uguale per tutte le voci di pagamento 
   * @return identificativoUnivocoRiscossione
  **/
  @XmlElement(name="identificativo_univoco_riscossione")
  public String getIdentificativoUnivocoRiscossione() {
    return identificativoUnivocoRiscossione;
  }

  public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
    this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
  }

  public VoceRtType identificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
    this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
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

  public VoceRtType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }

 /**
   * Get xmlBollo
   * @return xmlBollo
  **/
  @XmlElement(name="xml_bollo")
  public byte[] getXmlBollo() {
    return xmlBollo;
  }

  public void setXmlBollo(byte[] xmlBollo) {
    this.xmlBollo = xmlBollo;
  }

  public VoceRtType xmlBollo(byte[] xmlBollo) {
    this.xmlBollo = xmlBollo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VoceRtType {\n");
    
    sb.append("    allegato: ").append(toIndentedString(allegato)).append("\n");
    sb.append("    commissioniApplicatePsp: ").append(toIndentedString(commissioniApplicatePsp)).append("\n");
    sb.append("    dataPagamento: ").append(toIndentedString(dataPagamento)).append("\n");
    sb.append("    descrizioneEsitoKo: ").append(toIndentedString(descrizioneEsitoKo)).append("\n");
    sb.append("    identificativoUnivocoRiscossione: ").append(toIndentedString(identificativoUnivocoRiscossione)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    xmlBollo: ").append(toIndentedString(xmlBollo)).append("\n");
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

