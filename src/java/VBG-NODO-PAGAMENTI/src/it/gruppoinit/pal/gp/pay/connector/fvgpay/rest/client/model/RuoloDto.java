package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class RuoloDto  {
  
  
  private String codiceRuolo = null;

  
  private String descrizioneRuolo = null;

  
  private String etichettaRuolo = null;

  
  private String idRuolo = null;

  
  private String nomeRuolo = null;
 /**
   * Get codiceRuolo
   * @return codiceRuolo
  **/
  @XmlElement(name="codiceRuolo")
  public String getCodiceRuolo() {
    return codiceRuolo;
  }

  public void setCodiceRuolo(String codiceRuolo) {
    this.codiceRuolo = codiceRuolo;
  }

  public RuoloDto codiceRuolo(String codiceRuolo) {
    this.codiceRuolo = codiceRuolo;
    return this;
  }

 /**
   * Get descrizioneRuolo
   * @return descrizioneRuolo
  **/
  @XmlElement(name="descrizioneRuolo")
  public String getDescrizioneRuolo() {
    return descrizioneRuolo;
  }

  public void setDescrizioneRuolo(String descrizioneRuolo) {
    this.descrizioneRuolo = descrizioneRuolo;
  }

  public RuoloDto descrizioneRuolo(String descrizioneRuolo) {
    this.descrizioneRuolo = descrizioneRuolo;
    return this;
  }

 /**
   * Get etichettaRuolo
   * @return etichettaRuolo
  **/
  @XmlElement(name="etichettaRuolo")
  public String getEtichettaRuolo() {
    return etichettaRuolo;
  }

  public void setEtichettaRuolo(String etichettaRuolo) {
    this.etichettaRuolo = etichettaRuolo;
  }

  public RuoloDto etichettaRuolo(String etichettaRuolo) {
    this.etichettaRuolo = etichettaRuolo;
    return this;
  }

 /**
   * Get idRuolo
   * @return idRuolo
  **/
  @XmlElement(name="idRuolo")
  public String getIdRuolo() {
    return idRuolo;
  }

  public void setIdRuolo(String idRuolo) {
    this.idRuolo = idRuolo;
  }

  public RuoloDto idRuolo(String idRuolo) {
    this.idRuolo = idRuolo;
    return this;
  }

 /**
   * Get nomeRuolo
   * @return nomeRuolo
  **/
  @XmlElement(name="nomeRuolo")
  public String getNomeRuolo() {
    return nomeRuolo;
  }

  public void setNomeRuolo(String nomeRuolo) {
    this.nomeRuolo = nomeRuolo;
  }

  public RuoloDto nomeRuolo(String nomeRuolo) {
    this.nomeRuolo = nomeRuolo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuoloDto {\n");
    
    sb.append("    codiceRuolo: ").append(toIndentedString(codiceRuolo)).append("\n");
    sb.append("    descrizioneRuolo: ").append(toIndentedString(descrizioneRuolo)).append("\n");
    sb.append("    etichettaRuolo: ").append(toIndentedString(etichettaRuolo)).append("\n");
    sb.append("    idRuolo: ").append(toIndentedString(idRuolo)).append("\n");
    sb.append("    nomeRuolo: ").append(toIndentedString(nomeRuolo)).append("\n");
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

