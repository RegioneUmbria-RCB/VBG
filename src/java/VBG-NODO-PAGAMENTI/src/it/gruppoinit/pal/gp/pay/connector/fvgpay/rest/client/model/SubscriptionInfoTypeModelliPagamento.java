package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
  * Elenco dei modelli di pagamento utilizzabili per pagare - usato come default per la registrazione di una posizione debitoria - il supporto al modello 2 richiede il supporto al modello 1 - il supporto al modello 3 richiede sia registrato il codice interbancario - il supporto al modello 4 richiede il supporto del modello 3 
 **/

public class SubscriptionInfoTypeModelliPagamento  {
  
  
  private List<String> elenco = new ArrayList<String>();
 /**
   * Get elenco
   * @return elenco
  **/
  @XmlElement(name="elenco")
  public List<String> getElenco() {
    return elenco;
  }

  public void setElenco(List<String> elenco) {
    this.elenco = elenco;
  }

  public SubscriptionInfoTypeModelliPagamento elenco(List<String> elenco) {
    this.elenco = elenco;
    return this;
  }

  public SubscriptionInfoTypeModelliPagamento addElencoItem(String elencoItem) {
    this.elenco.add(elencoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionInfoTypeModelliPagamento {\n");
    
    sb.append("    elenco: ").append(toIndentedString(elenco)).append("\n");
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

