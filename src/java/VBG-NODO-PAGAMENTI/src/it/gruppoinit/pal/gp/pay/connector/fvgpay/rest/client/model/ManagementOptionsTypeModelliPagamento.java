package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
  * Elenco dei modelli di pagamento utilizzabili per pagare - se non specificati viene usata la configurazione specificata   durante l'attivazione servizio 
 **/

public class ManagementOptionsTypeModelliPagamento  {
  
  
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

  public ManagementOptionsTypeModelliPagamento elenco(List<String> elenco) {
    this.elenco = elenco;
    return this;
  }

  public ManagementOptionsTypeModelliPagamento addElencoItem(String elencoItem) {
    this.elenco.add(elencoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManagementOptionsTypeModelliPagamento {\n");
    
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

