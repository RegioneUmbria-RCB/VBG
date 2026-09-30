package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * se il servizio supporta la gestione delle rate specifica le impostazioni di rateazione per un ente creditore - se non viene specificato un importo minimo la rateazione � possibile per qualsiasi Importo - se viene specificata una rateazione automatica gli importi vanno espressi come % dell'importo totale per cui la loro somma deve essere 100, l'ultimo importo percentuale va ignorato e l'importo va calcolato per differenza dal totale - l'elenco rate deve avere non oltre max_rate elementi 
 **/

public class SupportoRateType  {
  
  
  private ImportoType importoMinimo = null;

  
 /**
   * specifica il numero massimo di rate consentito, comunque inferiore a 12 
  **/
  private Integer maxRate = null;

  
 /**
   * specifica se la rateazione � obbligatoria 
  **/
  private Boolean rateObbligatorie = null;

  
  private RichiestaRateizzazioneType rateizzazione = null;
 /**
   * Get importoMinimo
   * @return importoMinimo
  **/
  @XmlElement(name="importo_minimo")
  public ImportoType getImportoMinimo() {
    return importoMinimo;
  }

  public void setImportoMinimo(ImportoType importoMinimo) {
    this.importoMinimo = importoMinimo;
  }

  public SupportoRateType importoMinimo(ImportoType importoMinimo) {
    this.importoMinimo = importoMinimo;
    return this;
  }

 /**
   * specifica il numero massimo di rate consentito, comunque inferiore a 12 
   * @return maxRate
  **/
  @XmlElement(name="max_rate")
  public Integer getMaxRate() {
    return maxRate;
  }

  public void setMaxRate(Integer maxRate) {
    this.maxRate = maxRate;
  }

  public SupportoRateType maxRate(Integer maxRate) {
    this.maxRate = maxRate;
    return this;
  }

 /**
   * specifica se la rateazione � obbligatoria 
   * @return rateObbligatorie
  **/
  @XmlElement(name="rate_obbligatorie")
  public Boolean isRateObbligatorie() {
    return rateObbligatorie;
  }

  public void setRateObbligatorie(Boolean rateObbligatorie) {
    this.rateObbligatorie = rateObbligatorie;
  }

  public SupportoRateType rateObbligatorie(Boolean rateObbligatorie) {
    this.rateObbligatorie = rateObbligatorie;
    return this;
  }

 /**
   * Get rateizzazione
   * @return rateizzazione
  **/
  @XmlElement(name="rateizzazione")
  public RichiestaRateizzazioneType getRateizzazione() {
    return rateizzazione;
  }

  public void setRateizzazione(RichiestaRateizzazioneType rateizzazione) {
    this.rateizzazione = rateizzazione;
  }

  public SupportoRateType rateizzazione(RichiestaRateizzazioneType rateizzazione) {
    this.rateizzazione = rateizzazione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SupportoRateType {\n");
    
    sb.append("    importoMinimo: ").append(toIndentedString(importoMinimo)).append("\n");
    sb.append("    maxRate: ").append(toIndentedString(maxRate)).append("\n");
    sb.append("    rateObbligatorie: ").append(toIndentedString(rateObbligatorie)).append("\n");
    sb.append("    rateizzazione: ").append(toIndentedString(rateizzazione)).append("\n");
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

