package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Indirizzo  {
  
  
  private String barrato = null;

  
  private String cap = null;

  
  private String frazione = null;

  
  private Georeferenziazione georeferenziazione = null;

  
  private String indirizzo = null;

  
  private String interno = null;

  
  private Luogo luogo = null;

  
  private String numeroCivico = null;

  
  private String piano = null;

  
  private String scala = null;

  
  private String tipoIndirizzo = null;
 /**
   * Get barrato
   * @return barrato
  **/
  @XmlElement(name="barrato")
  public String getBarrato() {
    return barrato;
  }

  public void setBarrato(String barrato) {
    this.barrato = barrato;
  }

  public Indirizzo barrato(String barrato) {
    this.barrato = barrato;
    return this;
  }

 /**
   * Get cap
   * @return cap
  **/
  @XmlElement(name="cap")
  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public Indirizzo cap(String cap) {
    this.cap = cap;
    return this;
  }

 /**
   * Get frazione
   * @return frazione
  **/
  @XmlElement(name="frazione")
  public String getFrazione() {
    return frazione;
  }

  public void setFrazione(String frazione) {
    this.frazione = frazione;
  }

  public Indirizzo frazione(String frazione) {
    this.frazione = frazione;
    return this;
  }

 /**
   * Get georeferenziazione
   * @return georeferenziazione
  **/
  @XmlElement(name="georeferenziazione")
  public Georeferenziazione getGeoreferenziazione() {
    return georeferenziazione;
  }

  public void setGeoreferenziazione(Georeferenziazione georeferenziazione) {
    this.georeferenziazione = georeferenziazione;
  }

  public Indirizzo georeferenziazione(Georeferenziazione georeferenziazione) {
    this.georeferenziazione = georeferenziazione;
    return this;
  }

 /**
   * Get indirizzo
   * @return indirizzo
  **/
  @XmlElement(name="indirizzo")
  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public Indirizzo indirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
    return this;
  }

 /**
   * Get interno
   * @return interno
  **/
  @XmlElement(name="interno")
  public String getInterno() {
    return interno;
  }

  public void setInterno(String interno) {
    this.interno = interno;
  }

  public Indirizzo interno(String interno) {
    this.interno = interno;
    return this;
  }

 /**
   * Get luogo
   * @return luogo
  **/
  @XmlElement(name="luogo")
  public Luogo getLuogo() {
    return luogo;
  }

  public void setLuogo(Luogo luogo) {
    this.luogo = luogo;
  }

  public Indirizzo luogo(Luogo luogo) {
    this.luogo = luogo;
    return this;
  }

 /**
   * Get numeroCivico
   * @return numeroCivico
  **/
  @XmlElement(name="numeroCivico")
  public String getNumeroCivico() {
    return numeroCivico;
  }

  public void setNumeroCivico(String numeroCivico) {
    this.numeroCivico = numeroCivico;
  }

  public Indirizzo numeroCivico(String numeroCivico) {
    this.numeroCivico = numeroCivico;
    return this;
  }

 /**
   * Get piano
   * @return piano
  **/
  @XmlElement(name="piano")
  public String getPiano() {
    return piano;
  }

  public void setPiano(String piano) {
    this.piano = piano;
  }

  public Indirizzo piano(String piano) {
    this.piano = piano;
    return this;
  }

 /**
   * Get scala
   * @return scala
  **/
  @XmlElement(name="scala")
  public String getScala() {
    return scala;
  }

  public void setScala(String scala) {
    this.scala = scala;
  }

  public Indirizzo scala(String scala) {
    this.scala = scala;
    return this;
  }

 /**
   * Get tipoIndirizzo
   * @return tipoIndirizzo
  **/
  @XmlElement(name="tipoIndirizzo")
  public String getTipoIndirizzo() {
    return tipoIndirizzo;
  }

  public void setTipoIndirizzo(String tipoIndirizzo) {
    this.tipoIndirizzo = tipoIndirizzo;
  }

  public Indirizzo tipoIndirizzo(String tipoIndirizzo) {
    this.tipoIndirizzo = tipoIndirizzo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Indirizzo {\n");
    
    sb.append("    barrato: ").append(toIndentedString(barrato)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    frazione: ").append(toIndentedString(frazione)).append("\n");
    sb.append("    georeferenziazione: ").append(toIndentedString(georeferenziazione)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    interno: ").append(toIndentedString(interno)).append("\n");
    sb.append("    luogo: ").append(toIndentedString(luogo)).append("\n");
    sb.append("    numeroCivico: ").append(toIndentedString(numeroCivico)).append("\n");
    sb.append("    piano: ").append(toIndentedString(piano)).append("\n");
    sb.append("    scala: ").append(toIndentedString(scala)).append("\n");
    sb.append("    tipoIndirizzo: ").append(toIndentedString(tipoIndirizzo)).append("\n");
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

