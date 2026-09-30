package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class TipoEnteCreditore  {
  
  
  private Integer attivo = null;

  
  private String descrizione = null;

  
  private String tipoEnteCreditore = null;
 /**
   * Get attivo
   * @return attivo
  **/
  @XmlElement(name="attivo")
  public Integer getAttivo() {
    return attivo;
  }

  public void setAttivo(Integer attivo) {
    this.attivo = attivo;
  }

  public TipoEnteCreditore attivo(Integer attivo) {
    this.attivo = attivo;
    return this;
  }

 /**
   * Get descrizione
   * @return descrizione
  **/
  @XmlElement(name="descrizione")
  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public TipoEnteCreditore descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * Get tipoEnteCreditore
   * @return tipoEnteCreditore
  **/
  @XmlElement(name="tipoEnteCreditore")
  public String getTipoEnteCreditore() {
    return tipoEnteCreditore;
  }

  public void setTipoEnteCreditore(String tipoEnteCreditore) {
    this.tipoEnteCreditore = tipoEnteCreditore;
  }

  public TipoEnteCreditore tipoEnteCreditore(String tipoEnteCreditore) {
    this.tipoEnteCreditore = tipoEnteCreditore;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TipoEnteCreditore {\n");
    
    sb.append("    attivo: ").append(toIndentedString(attivo)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    tipoEnteCreditore: ").append(toIndentedString(tipoEnteCreditore)).append("\n");
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

