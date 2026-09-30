package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  * informazioni di registrazione per l'attivazione di un servizio di pagamento per un ente creditore - obbligatorio specificare il supporto_rate se il servizio le supporta 
 **/

public class SubscriptionInfoType  {
  
  
  private String callbackUrlEsitoRevoca = null;

  
  private String callbackUrlRegistrazione = null;

  
  private String callbackUrlRicezioneRT = null;

  
  private ImportoType commissioneCaricoPa = null;

  
  private SubscriptionInfoTypeModelliPagamento modelliPagamento = null;

  
  private SupportoRateType supportoRate = null;


@XmlType(name="TipoContabilitaEnum")
@XmlEnum(String.class)
public enum TipoContabilitaEnum {

@XmlEnumValue("0") _0(String.valueOf("0")), @XmlEnumValue("1") _1(String.valueOf("1")), @XmlEnumValue("2") _2(String.valueOf("2")), @XmlEnumValue("9") _9(String.valueOf("9"));


    private String value;

    TipoContabilitaEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static TipoContabilitaEnum fromValue(String v) {
        for (TipoContabilitaEnum b : TipoContabilitaEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  
 /**
   * tipo di classificazione contabile usato (dovrebbe essere unico per l'ente e non dipendere dal servizio) - usato come default per la registrazione di una posizione debitoria - se non definito viene usato quello dell'ente - 0: Capitolo e articolo di Entrata Bilancio dello Stato - 1: Numero della contabilita' speciale - 2: Codice SIOPE - 9: Altro codice Ente Creditore 
  **/
  private TipoContabilitaEnum tipoContabilita = null;
 /**
   * Get callbackUrlEsitoRevoca
   * @return callbackUrlEsitoRevoca
  **/
  @XmlElement(name="callbackUrlEsitoRevoca")
  public String getCallbackUrlEsitoRevoca() {
    return callbackUrlEsitoRevoca;
  }

  public void setCallbackUrlEsitoRevoca(String callbackUrlEsitoRevoca) {
    this.callbackUrlEsitoRevoca = callbackUrlEsitoRevoca;
  }

  public SubscriptionInfoType callbackUrlEsitoRevoca(String callbackUrlEsitoRevoca) {
    this.callbackUrlEsitoRevoca = callbackUrlEsitoRevoca;
    return this;
  }

 /**
   * Get callbackUrlRegistrazione
   * @return callbackUrlRegistrazione
  **/
  @XmlElement(name="callbackUrlRegistrazione")
  public String getCallbackUrlRegistrazione() {
    return callbackUrlRegistrazione;
  }

  public void setCallbackUrlRegistrazione(String callbackUrlRegistrazione) {
    this.callbackUrlRegistrazione = callbackUrlRegistrazione;
  }

  public SubscriptionInfoType callbackUrlRegistrazione(String callbackUrlRegistrazione) {
    this.callbackUrlRegistrazione = callbackUrlRegistrazione;
    return this;
  }

 /**
   * Get callbackUrlRicezioneRT
   * @return callbackUrlRicezioneRT
  **/
  @XmlElement(name="callbackUrlRicezioneRT")
  public String getCallbackUrlRicezioneRT() {
    return callbackUrlRicezioneRT;
  }

  public void setCallbackUrlRicezioneRT(String callbackUrlRicezioneRT) {
    this.callbackUrlRicezioneRT = callbackUrlRicezioneRT;
  }

  public SubscriptionInfoType callbackUrlRicezioneRT(String callbackUrlRicezioneRT) {
    this.callbackUrlRicezioneRT = callbackUrlRicezioneRT;
    return this;
  }

 /**
   * Get commissioneCaricoPa
   * @return commissioneCaricoPa
  **/
  @XmlElement(name="commissione_carico_pa")
  public ImportoType getCommissioneCaricoPa() {
    return commissioneCaricoPa;
  }

  public void setCommissioneCaricoPa(ImportoType commissioneCaricoPa) {
    this.commissioneCaricoPa = commissioneCaricoPa;
  }

  public SubscriptionInfoType commissioneCaricoPa(ImportoType commissioneCaricoPa) {
    this.commissioneCaricoPa = commissioneCaricoPa;
    return this;
  }

 /**
   * Get modelliPagamento
   * @return modelliPagamento
  **/
  @XmlElement(name="modelli_pagamento")
  public SubscriptionInfoTypeModelliPagamento getModelliPagamento() {
    return modelliPagamento;
  }

  public void setModelliPagamento(SubscriptionInfoTypeModelliPagamento modelliPagamento) {
    this.modelliPagamento = modelliPagamento;
  }

  public SubscriptionInfoType modelliPagamento(SubscriptionInfoTypeModelliPagamento modelliPagamento) {
    this.modelliPagamento = modelliPagamento;
    return this;
  }

 /**
   * Get supportoRate
   * @return supportoRate
  **/
  @XmlElement(name="supporto_rate")
  public SupportoRateType getSupportoRate() {
    return supportoRate;
  }

  public void setSupportoRate(SupportoRateType supportoRate) {
    this.supportoRate = supportoRate;
  }

  public SubscriptionInfoType supportoRate(SupportoRateType supportoRate) {
    this.supportoRate = supportoRate;
    return this;
  }

 /**
   * tipo di classificazione contabile usato (dovrebbe essere unico per l&#39;ente e non dipendere dal servizio) - usato come default per la registrazione di una posizione debitoria - se non definito viene usato quello dell&#39;ente - 0: Capitolo e articolo di Entrata Bilancio dello Stato - 1: Numero della contabilita&#39; speciale - 2: Codice SIOPE - 9: Altro codice Ente Creditore 
   * @return tipoContabilita
  **/
  @XmlElement(name="tipo_contabilita")
  public String getTipoContabilita() {
    if (tipoContabilita == null) {
      return null;
    }
    return tipoContabilita.value();
  }

  public void setTipoContabilita(TipoContabilitaEnum tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
  }

  public SubscriptionInfoType tipoContabilita(TipoContabilitaEnum tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionInfoType {\n");
    
    sb.append("    callbackUrlEsitoRevoca: ").append(toIndentedString(callbackUrlEsitoRevoca)).append("\n");
    sb.append("    callbackUrlRegistrazione: ").append(toIndentedString(callbackUrlRegistrazione)).append("\n");
    sb.append("    callbackUrlRicezioneRT: ").append(toIndentedString(callbackUrlRicezioneRT)).append("\n");
    sb.append("    commissioneCaricoPa: ").append(toIndentedString(commissioneCaricoPa)).append("\n");
    sb.append("    modelliPagamento: ").append(toIndentedString(modelliPagamento)).append("\n");
    sb.append("    supportoRate: ").append(toIndentedString(supportoRate)).append("\n");
    sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
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

