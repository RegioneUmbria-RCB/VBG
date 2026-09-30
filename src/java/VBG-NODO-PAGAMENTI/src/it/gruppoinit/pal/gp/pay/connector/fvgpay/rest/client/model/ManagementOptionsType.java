package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class ManagementOptionsType  {
  
  
 /**
   * Data in cui l'avviso di pagamento deve essere reso disponibile L'avviso di pagamento non viene reso disponibile fino alla data eventualmente specificata Se non viene specificata, l'avviso viene reso disponibile immediatamente Quando un avviso viene pubblicato viene reso disponibile sul FrontEnd Utente e viene notificato in forma digitale ed analogica, da quel momento viene quindi reso disponibile per il pagamento - usare il formato ISO 8601 (YYYY-MM-DD) - deve essere precedente alla data di scadenza del pagamento 
  **/
  private Date dataPubblicazioneAvviso = null;

  
  private ManagementOptionsTypeMezziNotifica mezziNotifica = null;

  
  private ManagementOptionsTypeModelliPagamento modelliPagamento = null;

  
  private RichiestaRateizzazioneType richiestaRateizzazione = null;
 /**
   * Data in cui l&#39;avviso di pagamento deve essere reso disponibile L&#39;avviso di pagamento non viene reso disponibile fino alla data eventualmente specificata Se non viene specificata, l&#39;avviso viene reso disponibile immediatamente Quando un avviso viene pubblicato viene reso disponibile sul FrontEnd Utente e viene notificato in forma digitale ed analogica, da quel momento viene quindi reso disponibile per il pagamento - usare il formato ISO 8601 (YYYY-MM-DD) - deve essere precedente alla data di scadenza del pagamento 
   * @return dataPubblicazioneAvviso
  **/
  @XmlElement(name="data_pubblicazione_avviso")
  public Date getDataPubblicazioneAvviso() {
    return dataPubblicazioneAvviso;
  }

  public void setDataPubblicazioneAvviso(Date dataPubblicazioneAvviso) {
    this.dataPubblicazioneAvviso = dataPubblicazioneAvviso;
  }

  public ManagementOptionsType dataPubblicazioneAvviso(Date dataPubblicazioneAvviso) {
    this.dataPubblicazioneAvviso = dataPubblicazioneAvviso;
    return this;
  }

 /**
   * Get mezziNotifica
   * @return mezziNotifica
  **/
  @XmlElement(name="mezzi_notifica")
  public ManagementOptionsTypeMezziNotifica getMezziNotifica() {
    return mezziNotifica;
  }

  public void setMezziNotifica(ManagementOptionsTypeMezziNotifica mezziNotifica) {
    this.mezziNotifica = mezziNotifica;
  }

  public ManagementOptionsType mezziNotifica(ManagementOptionsTypeMezziNotifica mezziNotifica) {
    this.mezziNotifica = mezziNotifica;
    return this;
  }

 /**
   * Get modelliPagamento
   * @return modelliPagamento
  **/
  @XmlElement(name="modelli_pagamento")
  public ManagementOptionsTypeModelliPagamento getModelliPagamento() {
    return modelliPagamento;
  }

  public void setModelliPagamento(ManagementOptionsTypeModelliPagamento modelliPagamento) {
    this.modelliPagamento = modelliPagamento;
  }

  public ManagementOptionsType modelliPagamento(ManagementOptionsTypeModelliPagamento modelliPagamento) {
    this.modelliPagamento = modelliPagamento;
    return this;
  }

 /**
   * Get richiestaRateizzazione
   * @return richiestaRateizzazione
  **/
  @XmlElement(name="richiesta_rateizzazione")
  public RichiestaRateizzazioneType getRichiestaRateizzazione() {
    return richiestaRateizzazione;
  }

  public void setRichiestaRateizzazione(RichiestaRateizzazioneType richiestaRateizzazione) {
    this.richiestaRateizzazione = richiestaRateizzazione;
  }

  public ManagementOptionsType richiestaRateizzazione(RichiestaRateizzazioneType richiestaRateizzazione) {
    this.richiestaRateizzazione = richiestaRateizzazione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManagementOptionsType {\n");
    
    sb.append("    dataPubblicazioneAvviso: ").append(toIndentedString(dataPubblicazioneAvviso)).append("\n");
    sb.append("    mezziNotifica: ").append(toIndentedString(mezziNotifica)).append("\n");
    sb.append("    modelliPagamento: ").append(toIndentedString(modelliPagamento)).append("\n");
    sb.append("    richiestaRateizzazione: ").append(toIndentedString(richiestaRateizzazione)).append("\n");
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

