package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
  * Eventuale suddivisione in rate (da 2 a 12) del versamento richiesta per la posizione debitoria Se non vengono specificate nella singola posizione debitoria deve essere configurata a livello di attivazione del servizio di pagamento la modalita' di calcolo delle rate 
 **/

public class RichiestaRateizzazioneTypeRate  {
  
  
  private List<RataType> elenco = new ArrayList<RataType>();
 /**
   * Get elenco
   * @return elenco
  **/
  @XmlElement(name="elenco")
  public List<RataType> getElenco() {
    return elenco;
  }

  public void setElenco(List<RataType> elenco) {
    this.elenco = elenco;
  }

  public RichiestaRateizzazioneTypeRate elenco(List<RataType> elenco) {
    this.elenco = elenco;
    return this;
  }

  public RichiestaRateizzazioneTypeRate addElencoItem(RataType elencoItem) {
    this.elenco.add(elencoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaRateizzazioneTypeRate {\n");
    
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

