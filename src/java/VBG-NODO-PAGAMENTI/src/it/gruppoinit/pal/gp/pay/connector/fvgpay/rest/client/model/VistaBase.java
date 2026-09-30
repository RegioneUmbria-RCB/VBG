package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class VistaBase  {
  
  
  private XMLGregorianCalendar dataFine = null;

  
  private XMLGregorianCalendar dataInizio = null;

  
  private String descrizioneVista = null;

  
  private String idVista = null;

  
  private Modello modello = null;

  
  private String nomeLogicoVista = null;

  
  private String tipoVista = null;
 /**
   * Get dataFine
   * @return dataFine
  **/
  @XmlElement(name="dataFine")
  public XMLGregorianCalendar getDataFine() {
    return dataFine;
  }

  public void setDataFine(XMLGregorianCalendar dataFine) {
    this.dataFine = dataFine;
  }

  public VistaBase dataFine(XMLGregorianCalendar dataFine) {
    this.dataFine = dataFine;
    return this;
  }

 /**
   * Get dataInizio
   * @return dataInizio
  **/
  @XmlElement(name="dataInizio")
  public XMLGregorianCalendar getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(XMLGregorianCalendar dataInizio) {
    this.dataInizio = dataInizio;
  }

  public VistaBase dataInizio(XMLGregorianCalendar dataInizio) {
    this.dataInizio = dataInizio;
    return this;
  }

 /**
   * Get descrizioneVista
   * @return descrizioneVista
  **/
  @XmlElement(name="descrizioneVista")
  public String getDescrizioneVista() {
    return descrizioneVista;
  }

  public void setDescrizioneVista(String descrizioneVista) {
    this.descrizioneVista = descrizioneVista;
  }

  public VistaBase descrizioneVista(String descrizioneVista) {
    this.descrizioneVista = descrizioneVista;
    return this;
  }

 /**
   * Get idVista
   * @return idVista
  **/
  @XmlElement(name="idVista")
  public String getIdVista() {
    return idVista;
  }

  public void setIdVista(String idVista) {
    this.idVista = idVista;
  }

  public VistaBase idVista(String idVista) {
    this.idVista = idVista;
    return this;
  }

 /**
   * Get modello
   * @return modello
  **/
  @XmlElement(name="modello")
  public Modello getModello() {
    return modello;
  }

  public void setModello(Modello modello) {
    this.modello = modello;
  }

  public VistaBase modello(Modello modello) {
    this.modello = modello;
    return this;
  }

 /**
   * Get nomeLogicoVista
   * @return nomeLogicoVista
  **/
  @XmlElement(name="nomeLogicoVista")
  public String getNomeLogicoVista() {
    return nomeLogicoVista;
  }

  public void setNomeLogicoVista(String nomeLogicoVista) {
    this.nomeLogicoVista = nomeLogicoVista;
  }

  public VistaBase nomeLogicoVista(String nomeLogicoVista) {
    this.nomeLogicoVista = nomeLogicoVista;
    return this;
  }

 /**
   * Get tipoVista
   * @return tipoVista
  **/
  @XmlElement(name="tipoVista")
  public String getTipoVista() {
    return tipoVista;
  }

  public void setTipoVista(String tipoVista) {
    this.tipoVista = tipoVista;
  }

  public VistaBase tipoVista(String tipoVista) {
    this.tipoVista = tipoVista;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VistaBase {\n");
    
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
    sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
    sb.append("    descrizioneVista: ").append(toIndentedString(descrizioneVista)).append("\n");
    sb.append("    idVista: ").append(toIndentedString(idVista)).append("\n");
    sb.append("    modello: ").append(toIndentedString(modello)).append("\n");
    sb.append("    nomeLogicoVista: ").append(toIndentedString(nomeLogicoVista)).append("\n");
    sb.append("    tipoVista: ").append(toIndentedString(tipoVista)).append("\n");
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

