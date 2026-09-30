package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
  * Descrive lo stato di pagamento della posizione debitoria Contiene anche il dettaglio dello stato del pagamento in unica soluzione e degli eventuali pagamenti associati alle singole rate 
 **/

public class StatoPagamentoPosizioneDebitoriaType  {
  
  
 /**
   * Definisce i possibili stati di pagamento della posizione debitoria 
   * 
   * <pre>
        - in attesa di pagamento
        - in_attesa_di_richiesta
        - in_attesa_di_esito
        - in_elaborazione
        - parzialmente_pagato
        - pagato
        - pagato_extra_sistema
        - pagato_con_errore
        - annullato
        - chiuso
        - parzialmente_revocato
        - revocato
        - cancellato
        - non_approvato
        - abbandonato
        - errore
    </pre>
  **/
  private String statoPagamento = null;

  
 /**
   * Rateizzazione
  **/
  private List<StatoSingoloPagamentoType> statoPagamentoRate = null;

  
  private StatoSingoloPagamentoType statoPagamentoUnicaSoluzione = null;
 /**
   * Definisce i possibili stati di pagamento della posizione debitoria 
   *    * <pre>
        - in attesa di pagamento
        - in_attesa_di_richiesta
        - in_attesa_di_esito
        - in_elaborazione
        - parzialmente_pagato
        - pagato
        - pagato_extra_sistema
        - pagato_con_errore
        - annullato
        - chiuso
        - parzialmente_revocato
        - revocato
        - cancellato
        - non_approvato
        - abbandonato
        - errore
    </pre>
   * @return statoPagamento
  **/
  @XmlElement(name="stato_pagamento")
  public String getStatoPagamento() {
    return statoPagamento;
  }

  public void setStatoPagamento(String statoPagamento) {
    this.statoPagamento = statoPagamento;
  }

  public StatoPagamentoPosizioneDebitoriaType statoPagamento(String statoPagamento) {
    this.statoPagamento = statoPagamento;
    return this;
  }

 /**
   * Rateizzazione
   * @return statoPagamentoRate
  **/
  @XmlElement(name="stato_pagamento_rate")
  public List<StatoSingoloPagamentoType> getStatoPagamentoRate() {
    return statoPagamentoRate;
  }

  public void setStatoPagamentoRate(List<StatoSingoloPagamentoType> statoPagamentoRate) {
    this.statoPagamentoRate = statoPagamentoRate;
  }

  public StatoPagamentoPosizioneDebitoriaType statoPagamentoRate(List<StatoSingoloPagamentoType> statoPagamentoRate) {
    this.statoPagamentoRate = statoPagamentoRate;
    return this;
  }

  public StatoPagamentoPosizioneDebitoriaType addStatoPagamentoRateItem(StatoSingoloPagamentoType statoPagamentoRateItem) {
    this.statoPagamentoRate.add(statoPagamentoRateItem);
    return this;
  }

 /**
   * Get statoPagamentoUnicaSoluzione
   * @return statoPagamentoUnicaSoluzione
  **/
  @XmlElement(name="stato_pagamento_unica_soluzione")
  public StatoSingoloPagamentoType getStatoPagamentoUnicaSoluzione() {
    return statoPagamentoUnicaSoluzione;
  }

  public void setStatoPagamentoUnicaSoluzione(StatoSingoloPagamentoType statoPagamentoUnicaSoluzione) {
    this.statoPagamentoUnicaSoluzione = statoPagamentoUnicaSoluzione;
  }

  public StatoPagamentoPosizioneDebitoriaType statoPagamentoUnicaSoluzione(StatoSingoloPagamentoType statoPagamentoUnicaSoluzione) {
    this.statoPagamentoUnicaSoluzione = statoPagamentoUnicaSoluzione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatoPagamentoPosizioneDebitoriaType {\n");
    
    sb.append("    statoPagamento: ").append(toIndentedString(statoPagamento)).append("\n");
    sb.append("    statoPagamentoRate: ").append(toIndentedString(statoPagamentoRate)).append("\n");
    sb.append("    statoPagamentoUnicaSoluzione: ").append(toIndentedString(statoPagamentoUnicaSoluzione)).append("\n");
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

