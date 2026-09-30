package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class PosizioniDebitorieSelezionateType  {
  
  
  private List<DettaglioPosizioneDebitoriaType> posizioniDebitorie = new ArrayList<DettaglioPosizioneDebitoriaType>();
 /**
   * Get posizioniDebitorie
   * @return posizioniDebitorie
  **/
  @XmlElement(name="posizioni_debitorie")
  public List<DettaglioPosizioneDebitoriaType> getPosizioniDebitorie() {
    return posizioniDebitorie;
  }

  public void setPosizioniDebitorie(List<DettaglioPosizioneDebitoriaType> posizioniDebitorie) {
    this.posizioniDebitorie = posizioniDebitorie;
  }

  public PosizioniDebitorieSelezionateType posizioniDebitorie(List<DettaglioPosizioneDebitoriaType> posizioniDebitorie) {
    this.posizioniDebitorie = posizioniDebitorie;
    return this;
  }

  public PosizioniDebitorieSelezionateType addPosizioniDebitorieItem(DettaglioPosizioneDebitoriaType posizioniDebitorieItem) {
    this.posizioniDebitorie.add(posizioniDebitorieItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PosizioniDebitorieSelezionateType {\n");
    
    sb.append("    posizioniDebitorie: ").append(toIndentedString(posizioniDebitorie)).append("\n");
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

