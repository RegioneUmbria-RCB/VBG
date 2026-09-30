package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Indica se per questo pagamento e' richiesta la rateizzazione: se il pagamento verra' effettivamente rateizzato le rate effettive vengono restituite nella response 
 **/

public class RichiestaRateizzazioneType  {
  
  
  private RichiestaRateizzazioneTypeRate rate = null;
 /**
   * Get rate
   * @return rate
  **/
  @XmlElement(name="rate")
  public RichiestaRateizzazioneTypeRate getRate() {
    return rate;
  }

  public void setRate(RichiestaRateizzazioneTypeRate rate) {
    this.rate = rate;
  }

  public RichiestaRateizzazioneType rate(RichiestaRateizzazioneTypeRate rate) {
    this.rate = rate;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaRateizzazioneType {\n");
    
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
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

