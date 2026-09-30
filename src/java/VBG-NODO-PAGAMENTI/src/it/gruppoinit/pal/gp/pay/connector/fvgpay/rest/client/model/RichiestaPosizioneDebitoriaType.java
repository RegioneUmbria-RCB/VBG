package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class RichiestaPosizioneDebitoriaType  {
  
  
  private DettaglioRichiestaPagamentoType dettaglioRichiestaPagamento = null;

  
  private IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria = null;

  
  private ManagementOptionsType managementOptions = null;

  
  private SoggettoPagatoreType soggettoPagatore = null;
 /**
   * Get dettaglioRichiestaPagamento
   * @return dettaglioRichiestaPagamento
  **/
  @XmlElement(name="dettaglio_richiesta_pagamento")
  public DettaglioRichiestaPagamentoType getDettaglioRichiestaPagamento() {
    return dettaglioRichiestaPagamento;
  }

  public void setDettaglioRichiestaPagamento(DettaglioRichiestaPagamentoType dettaglioRichiestaPagamento) {
    this.dettaglioRichiestaPagamento = dettaglioRichiestaPagamento;
  }

  public RichiestaPosizioneDebitoriaType dettaglioRichiestaPagamento(DettaglioRichiestaPagamentoType dettaglioRichiestaPagamento) {
    this.dettaglioRichiestaPagamento = dettaglioRichiestaPagamento;
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

  public RichiestaPosizioneDebitoriaType identificativoPosizioneDebitoria(IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria) {
    this.identificativoPosizioneDebitoria = identificativoPosizioneDebitoria;
    return this;
  }

 /**
   * Get managementOptions
   * @return managementOptions
  **/
  @XmlElement(name="management_options")
  public ManagementOptionsType getManagementOptions() {
    return managementOptions;
  }

  public void setManagementOptions(ManagementOptionsType managementOptions) {
    this.managementOptions = managementOptions;
  }

  public RichiestaPosizioneDebitoriaType managementOptions(ManagementOptionsType managementOptions) {
    this.managementOptions = managementOptions;
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

  public RichiestaPosizioneDebitoriaType soggettoPagatore(SoggettoPagatoreType soggettoPagatore) {
    this.soggettoPagatore = soggettoPagatore;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaPosizioneDebitoriaType {\n");
    
    sb.append("    dettaglioRichiestaPagamento: ").append(toIndentedString(dettaglioRichiestaPagamento)).append("\n");
    sb.append("    identificativoPosizioneDebitoria: ").append(toIndentedString(identificativoPosizioneDebitoria)).append("\n");
    sb.append("    managementOptions: ").append(toIndentedString(managementOptions)).append("\n");
    sb.append("    soggettoPagatore: ").append(toIndentedString(soggettoPagatore)).append("\n");
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

