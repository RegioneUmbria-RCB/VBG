package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class TipoClassificazione  {
  
  
  private String codiceTipoClassificazione = null;

  
  private String descrizioneTipoClassificazione = null;

  
  private String idTipoClassificazione = null;
 /**
   * Get codiceTipoClassificazione
   * @return codiceTipoClassificazione
  **/
  @XmlElement(name="codiceTipoClassificazione")
  public String getCodiceTipoClassificazione() {
    return codiceTipoClassificazione;
  }

  public void setCodiceTipoClassificazione(String codiceTipoClassificazione) {
    this.codiceTipoClassificazione = codiceTipoClassificazione;
  }

  public TipoClassificazione codiceTipoClassificazione(String codiceTipoClassificazione) {
    this.codiceTipoClassificazione = codiceTipoClassificazione;
    return this;
  }

 /**
   * Get descrizioneTipoClassificazione
   * @return descrizioneTipoClassificazione
  **/
  @XmlElement(name="descrizioneTipoClassificazione")
  public String getDescrizioneTipoClassificazione() {
    return descrizioneTipoClassificazione;
  }

  public void setDescrizioneTipoClassificazione(String descrizioneTipoClassificazione) {
    this.descrizioneTipoClassificazione = descrizioneTipoClassificazione;
  }

  public TipoClassificazione descrizioneTipoClassificazione(String descrizioneTipoClassificazione) {
    this.descrizioneTipoClassificazione = descrizioneTipoClassificazione;
    return this;
  }

 /**
   * Get idTipoClassificazione
   * @return idTipoClassificazione
  **/
  @XmlElement(name="idTipoClassificazione")
  public String getIdTipoClassificazione() {
    return idTipoClassificazione;
  }

  public void setIdTipoClassificazione(String idTipoClassificazione) {
    this.idTipoClassificazione = idTipoClassificazione;
  }

  public TipoClassificazione idTipoClassificazione(String idTipoClassificazione) {
    this.idTipoClassificazione = idTipoClassificazione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TipoClassificazione {\n");
    
    sb.append("    codiceTipoClassificazione: ").append(toIndentedString(codiceTipoClassificazione)).append("\n");
    sb.append("    descrizioneTipoClassificazione: ").append(toIndentedString(descrizioneTipoClassificazione)).append("\n");
    sb.append("    idTipoClassificazione: ").append(toIndentedString(idTipoClassificazione)).append("\n");
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

