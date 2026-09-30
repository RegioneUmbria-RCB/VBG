package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Raccoglie gli elementi che caratterizzano una posizione debitoria - gli identificativi dell'Ente Creditore e del Servizio di pagamento - i dati del debitore - lo stato di pagamento della posizione debitoria 
 **/

public class DettaglioPosizioneDebitoriaType  {
  
  
  private byte[] avvisoDiPagamento = null;

  
 /**
   * Denominazione dell'Ente Creditore
  **/
  private String denominazioneBeneficiario = null;

  
 /**
   * Denominazione del servizio di pagamento
  **/
  private String denominazioneServizio = null;

  
  private String descrizionePagamento = null;

  
  private IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria = null;

  
  private SoggettoPagatoreType soggettoPagatore = null;

  
  private StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria = null;
 /**
   * Get avvisoDiPagamento
   * @return avvisoDiPagamento
  **/
  @XmlElement(name="avviso_di_pagamento")
  public byte[] getAvvisoDiPagamento() {
    return avvisoDiPagamento;
  }

  public void setAvvisoDiPagamento(byte[] avvisoDiPagamento) {
    this.avvisoDiPagamento = avvisoDiPagamento;
  }

  public DettaglioPosizioneDebitoriaType avvisoDiPagamento(byte[] avvisoDiPagamento) {
    this.avvisoDiPagamento = avvisoDiPagamento;
    return this;
  }

 /**
   * Denominazione dell&#39;Ente Creditore
   * @return denominazioneBeneficiario
  **/
  @XmlElement(name="denominazione_beneficiario")
  public String getDenominazioneBeneficiario() {
    return denominazioneBeneficiario;
  }

  public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
    this.denominazioneBeneficiario = denominazioneBeneficiario;
  }

  public DettaglioPosizioneDebitoriaType denominazioneBeneficiario(String denominazioneBeneficiario) {
    this.denominazioneBeneficiario = denominazioneBeneficiario;
    return this;
  }

 /**
   * Denominazione del servizio di pagamento
   * @return denominazioneServizio
  **/
  @XmlElement(name="denominazione_servizio")
  public String getDenominazioneServizio() {
    return denominazioneServizio;
  }

  public void setDenominazioneServizio(String denominazioneServizio) {
    this.denominazioneServizio = denominazioneServizio;
  }

  public DettaglioPosizioneDebitoriaType denominazioneServizio(String denominazioneServizio) {
    this.denominazioneServizio = denominazioneServizio;
    return this;
  }

 /**
   * Get descrizionePagamento
   * @return descrizionePagamento
  **/
  @XmlElement(name="descrizione_pagamento")
  public String getDescrizionePagamento() {
    return descrizionePagamento;
  }

  public void setDescrizionePagamento(String descrizionePagamento) {
    this.descrizionePagamento = descrizionePagamento;
  }

  public DettaglioPosizioneDebitoriaType descrizionePagamento(String descrizionePagamento) {
    this.descrizionePagamento = descrizionePagamento;
    return this;
  }

 /**
   * Get identificativoPosizioneDebitoria
   * @return identificativoPosizioneDebitoria
  **/
  @XmlElement(name="identificativo_posizione_debitoria")
  public IdentificativoPosizioneDebitoriaType getIdentificativoPosizioneDebitoria() {
    return identificativoPosizioneDebitoria;
  }

  public void setIdentificativoPosizioneDebitoria(IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria) {
    this.identificativoPosizioneDebitoria = identificativoPosizioneDebitoria;
  }

  public DettaglioPosizioneDebitoriaType identificativoPosizioneDebitoria(IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria) {
    this.identificativoPosizioneDebitoria = identificativoPosizioneDebitoria;
    return this;
  }

 /**
   * Get soggettoPagatore
   * @return soggettoPagatore
  **/
  @XmlElement(name="soggetto_pagatore")
  public SoggettoPagatoreType getSoggettoPagatore() {
    return soggettoPagatore;
  }

  public void setSoggettoPagatore(SoggettoPagatoreType soggettoPagatore) {
    this.soggettoPagatore = soggettoPagatore;
  }

  public DettaglioPosizioneDebitoriaType soggettoPagatore(SoggettoPagatoreType soggettoPagatore) {
    this.soggettoPagatore = soggettoPagatore;
    return this;
  }

 /**
   * Get statoPagamentoPosizioneDebitoria
   * @return statoPagamentoPosizioneDebitoria
  **/
  @XmlElement(name="stato_pagamento_posizione_debitoria")
  public StatoPagamentoPosizioneDebitoriaType getStatoPagamentoPosizioneDebitoria() {
    return statoPagamentoPosizioneDebitoria;
  }

  public void setStatoPagamentoPosizioneDebitoria(StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria) {
    this.statoPagamentoPosizioneDebitoria = statoPagamentoPosizioneDebitoria;
  }

  public DettaglioPosizioneDebitoriaType statoPagamentoPosizioneDebitoria(StatoPagamentoPosizioneDebitoriaType statoPagamentoPosizioneDebitoria) {
    this.statoPagamentoPosizioneDebitoria = statoPagamentoPosizioneDebitoria;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DettaglioPosizioneDebitoriaType {\n");
    
    sb.append("    avvisoDiPagamento: ").append(toIndentedString(avvisoDiPagamento)).append("\n");
    sb.append("    denominazioneBeneficiario: ").append(toIndentedString(denominazioneBeneficiario)).append("\n");
    sb.append("    denominazioneServizio: ").append(toIndentedString(denominazioneServizio)).append("\n");
    sb.append("    descrizionePagamento: ").append(toIndentedString(descrizionePagamento)).append("\n");
    sb.append("    identificativoPosizioneDebitoria: ").append(toIndentedString(identificativoPosizioneDebitoria)).append("\n");
    sb.append("    soggettoPagatore: ").append(toIndentedString(soggettoPagatore)).append("\n");
    sb.append("    statoPagamentoPosizioneDebitoria: ").append(toIndentedString(statoPagamentoPosizioneDebitoria)).append("\n");
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

