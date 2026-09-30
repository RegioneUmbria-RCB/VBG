package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Classificazione  {
  
  
  private String codiceClassificazione = null;

  
  private String descrizioneClassificazione = null;

  
  private String idClassificazione = null;

  
  private PercorsoRadiceNodo percorsoRadiceNodo = null;

  
  private TipoClassificazione tipoClassificazione = null;
 /**
   * Get codiceClassificazione
   * @return codiceClassificazione
  **/
  @XmlElement(name="codiceClassificazione")
  public String getCodiceClassificazione() {
    return codiceClassificazione;
  }

  public void setCodiceClassificazione(String codiceClassificazione) {
    this.codiceClassificazione = codiceClassificazione;
  }

  public Classificazione codiceClassificazione(String codiceClassificazione) {
    this.codiceClassificazione = codiceClassificazione;
    return this;
  }

 /**
   * Get descrizioneClassificazione
   * @return descrizioneClassificazione
  **/
  @XmlElement(name="descrizioneClassificazione")
  public String getDescrizioneClassificazione() {
    return descrizioneClassificazione;
  }

  public void setDescrizioneClassificazione(String descrizioneClassificazione) {
    this.descrizioneClassificazione = descrizioneClassificazione;
  }

  public Classificazione descrizioneClassificazione(String descrizioneClassificazione) {
    this.descrizioneClassificazione = descrizioneClassificazione;
    return this;
  }

 /**
   * Get idClassificazione
   * @return idClassificazione
  **/
  @XmlElement(name="idClassificazione")
  public String getIdClassificazione() {
    return idClassificazione;
  }

  public void setIdClassificazione(String idClassificazione) {
    this.idClassificazione = idClassificazione;
  }

  public Classificazione idClassificazione(String idClassificazione) {
    this.idClassificazione = idClassificazione;
    return this;
  }

 /**
   * Get percorsoRadiceNodo
   * @return percorsoRadiceNodo
  **/
  @XmlElement(name="percorsoRadiceNodo")
  public PercorsoRadiceNodo getPercorsoRadiceNodo() {
    return percorsoRadiceNodo;
  }

  public void setPercorsoRadiceNodo(PercorsoRadiceNodo percorsoRadiceNodo) {
    this.percorsoRadiceNodo = percorsoRadiceNodo;
  }

  public Classificazione percorsoRadiceNodo(PercorsoRadiceNodo percorsoRadiceNodo) {
    this.percorsoRadiceNodo = percorsoRadiceNodo;
    return this;
  }

 /**
   * Get tipoClassificazione
   * @return tipoClassificazione
  **/
  @XmlElement(name="tipoClassificazione")
  public TipoClassificazione getTipoClassificazione() {
    return tipoClassificazione;
  }

  public void setTipoClassificazione(TipoClassificazione tipoClassificazione) {
    this.tipoClassificazione = tipoClassificazione;
  }

  public Classificazione tipoClassificazione(TipoClassificazione tipoClassificazione) {
    this.tipoClassificazione = tipoClassificazione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Classificazione {\n");
    
    sb.append("    codiceClassificazione: ").append(toIndentedString(codiceClassificazione)).append("\n");
    sb.append("    descrizioneClassificazione: ").append(toIndentedString(descrizioneClassificazione)).append("\n");
    sb.append("    idClassificazione: ").append(toIndentedString(idClassificazione)).append("\n");
    sb.append("    percorsoRadiceNodo: ").append(toIndentedString(percorsoRadiceNodo)).append("\n");
    sb.append("    tipoClassificazione: ").append(toIndentedString(tipoClassificazione)).append("\n");
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

