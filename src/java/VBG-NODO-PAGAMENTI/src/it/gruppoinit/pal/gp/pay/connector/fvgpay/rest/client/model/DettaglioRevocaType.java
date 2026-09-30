package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DettaglioRevocaType  {
  
  
 /**
   * Elenco delle singole voci di revoca (da 1 a 5) 
  **/
  private List<DettaglioVoceRevocaType> dettaglioVociRevoca = new ArrayList<DettaglioVoceRevocaType>();

  
  private ImportoType importoTotale = null;
 /**
   * Elenco delle singole voci di revoca (da 1 a 5) 
   * @return dettaglioVociRevoca
  **/
  @XmlElement(name="dettaglio_voci_revoca")
  public List<DettaglioVoceRevocaType> getDettaglioVociRevoca() {
    return dettaglioVociRevoca;
  }

  public void setDettaglioVociRevoca(List<DettaglioVoceRevocaType> dettaglioVociRevoca) {
    this.dettaglioVociRevoca = dettaglioVociRevoca;
  }

  public DettaglioRevocaType dettaglioVociRevoca(List<DettaglioVoceRevocaType> dettaglioVociRevoca) {
    this.dettaglioVociRevoca = dettaglioVociRevoca;
    return this;
  }

  public DettaglioRevocaType addDettaglioVociRevocaItem(DettaglioVoceRevocaType dettaglioVociRevocaItem) {
    this.dettaglioVociRevoca.add(dettaglioVociRevocaItem);
    return this;
  }

 /**
   * Get importoTotale
   * @return importoTotale
  **/
  @XmlElement(name="importo_totale")
  public ImportoType getImportoTotale() {
    return importoTotale;
  }

  public void setImportoTotale(ImportoType importoTotale) {
    this.importoTotale = importoTotale;
  }

  public DettaglioRevocaType importoTotale(ImportoType importoTotale) {
    this.importoTotale = importoTotale;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DettaglioRevocaType {\n");
    
    sb.append("    dettaglioVociRevoca: ").append(toIndentedString(dettaglioVociRevoca)).append("\n");
    sb.append("    importoTotale: ").append(toIndentedString(importoTotale)).append("\n");
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

