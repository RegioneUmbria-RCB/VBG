package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ListaClassificazioni  {
  
  
  private List<Classificazione> classificazione = null;
 /**
   * Get classificazione
   * @return classificazione
  **/
  @XmlElement(name="classificazione")
  public List<Classificazione> getClassificazione() {
    return classificazione;
  }

  public void setClassificazione(List<Classificazione> classificazione) {
    this.classificazione = classificazione;
  }

  public ListaClassificazioni classificazione(List<Classificazione> classificazione) {
    this.classificazione = classificazione;
    return this;
  }

  public ListaClassificazioni addClassificazioneItem(Classificazione classificazioneItem) {
    this.classificazione.add(classificazioneItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaClassificazioni {\n");
    
    sb.append("    classificazione: ").append(toIndentedString(classificazione)).append("\n");
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

