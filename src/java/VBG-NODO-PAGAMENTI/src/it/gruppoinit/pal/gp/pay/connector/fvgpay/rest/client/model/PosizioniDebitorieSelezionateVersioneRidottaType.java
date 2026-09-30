package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class PosizioniDebitorieSelezionateVersioneRidottaType  {
  
  
  private Integer limit = null;

  
  private Integer offset = null;

  
  private List<DettaglioPosizioneDebitoriaVersioneRidottaType> posizioniDebitorie = new ArrayList<DettaglioPosizioneDebitoriaVersioneRidottaType>();

  
  private Integer recordTrovati = null;
 /**
   * Get limit
   * @return limit
  **/
  @XmlElement(name="limit")
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public PosizioniDebitorieSelezionateVersioneRidottaType limit(Integer limit) {
    this.limit = limit;
    return this;
  }

 /**
   * Get offset
   * @return offset
  **/
  @XmlElement(name="offset")
  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public PosizioniDebitorieSelezionateVersioneRidottaType offset(Integer offset) {
    this.offset = offset;
    return this;
  }

 /**
   * Get posizioniDebitorie
   * @return posizioniDebitorie
  **/
  @XmlElement(name="posizioni_debitorie")
  public List<DettaglioPosizioneDebitoriaVersioneRidottaType> getPosizioniDebitorie() {
    return posizioniDebitorie;
  }

  public void setPosizioniDebitorie(List<DettaglioPosizioneDebitoriaVersioneRidottaType> posizioniDebitorie) {
    this.posizioniDebitorie = posizioniDebitorie;
  }

  public PosizioniDebitorieSelezionateVersioneRidottaType posizioniDebitorie(List<DettaglioPosizioneDebitoriaVersioneRidottaType> posizioniDebitorie) {
    this.posizioniDebitorie = posizioniDebitorie;
    return this;
  }

  public PosizioniDebitorieSelezionateVersioneRidottaType addPosizioniDebitorieItem(DettaglioPosizioneDebitoriaVersioneRidottaType posizioniDebitorieItem) {
    this.posizioniDebitorie.add(posizioniDebitorieItem);
    return this;
  }

 /**
   * Get recordTrovati
   * @return recordTrovati
  **/
  @XmlElement(name="record_trovati")
  public Integer getRecordTrovati() {
    return recordTrovati;
  }

  public void setRecordTrovati(Integer recordTrovati) {
    this.recordTrovati = recordTrovati;
  }

  public PosizioniDebitorieSelezionateVersioneRidottaType recordTrovati(Integer recordTrovati) {
    this.recordTrovati = recordTrovati;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PosizioniDebitorieSelezionateVersioneRidottaType {\n");
    
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    posizioniDebitorie: ").append(toIndentedString(posizioniDebitorie)).append("\n");
    sb.append("    recordTrovati: ").append(toIndentedString(recordTrovati)).append("\n");
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

