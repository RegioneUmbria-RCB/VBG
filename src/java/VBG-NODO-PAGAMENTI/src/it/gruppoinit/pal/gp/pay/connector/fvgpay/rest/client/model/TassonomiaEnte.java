package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class TassonomiaEnte  {
  
  
  private Long attivo = null;

  
  private String codiceTassonomiaEnte = null;

  
  private String codiceTassonomiaPagopa = null;

  
  private Date dataCreazione = null;

  
  private String descrTassonomiaEnte = null;

  
  private Long idEnte = null;

  
  private Long idTassonomiaEnte = null;
 /**
   * Get attivo
   * @return attivo
  **/
  @XmlElement(name="attivo")
  public Long getAttivo() {
    return attivo;
  }

  public void setAttivo(Long attivo) {
    this.attivo = attivo;
  }

  public TassonomiaEnte attivo(Long attivo) {
    this.attivo = attivo;
    return this;
  }

 /**
   * Get codiceTassonomiaEnte
   * @return codiceTassonomiaEnte
  **/
  @XmlElement(name="codiceTassonomiaEnte")
  public String getCodiceTassonomiaEnte() {
    return codiceTassonomiaEnte;
  }

  public void setCodiceTassonomiaEnte(String codiceTassonomiaEnte) {
    this.codiceTassonomiaEnte = codiceTassonomiaEnte;
  }

  public TassonomiaEnte codiceTassonomiaEnte(String codiceTassonomiaEnte) {
    this.codiceTassonomiaEnte = codiceTassonomiaEnte;
    return this;
  }

 /**
   * Get codiceTassonomiaPagopa
   * @return codiceTassonomiaPagopa
  **/
  @XmlElement(name="codiceTassonomiaPagopa")
  public String getCodiceTassonomiaPagopa() {
    return codiceTassonomiaPagopa;
  }

  public void setCodiceTassonomiaPagopa(String codiceTassonomiaPagopa) {
    this.codiceTassonomiaPagopa = codiceTassonomiaPagopa;
  }

  public TassonomiaEnte codiceTassonomiaPagopa(String codiceTassonomiaPagopa) {
    this.codiceTassonomiaPagopa = codiceTassonomiaPagopa;
    return this;
  }

 /**
   * Get dataCreazione
   * @return dataCreazione
  **/
  @XmlElement(name="dataCreazione")
  public Date getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public TassonomiaEnte dataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
    return this;
  }

 /**
   * Get descrTassonomiaEnte
   * @return descrTassonomiaEnte
  **/
  @XmlElement(name="descrTassonomiaEnte")
  public String getDescrTassonomiaEnte() {
    return descrTassonomiaEnte;
  }

  public void setDescrTassonomiaEnte(String descrTassonomiaEnte) {
    this.descrTassonomiaEnte = descrTassonomiaEnte;
  }

  public TassonomiaEnte descrTassonomiaEnte(String descrTassonomiaEnte) {
    this.descrTassonomiaEnte = descrTassonomiaEnte;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="idEnte")
  public Long getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(Long idEnte) {
    this.idEnte = idEnte;
  }

  public TassonomiaEnte idEnte(Long idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get idTassonomiaEnte
   * @return idTassonomiaEnte
  **/
  @XmlElement(name="idTassonomiaEnte")
  public Long getIdTassonomiaEnte() {
    return idTassonomiaEnte;
  }

  public void setIdTassonomiaEnte(Long idTassonomiaEnte) {
    this.idTassonomiaEnte = idTassonomiaEnte;
  }

  public TassonomiaEnte idTassonomiaEnte(Long idTassonomiaEnte) {
    this.idTassonomiaEnte = idTassonomiaEnte;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TassonomiaEnte {\n");
    
    sb.append("    attivo: ").append(toIndentedString(attivo)).append("\n");
    sb.append("    codiceTassonomiaEnte: ").append(toIndentedString(codiceTassonomiaEnte)).append("\n");
    sb.append("    codiceTassonomiaPagopa: ").append(toIndentedString(codiceTassonomiaPagopa)).append("\n");
    sb.append("    dataCreazione: ").append(toIndentedString(dataCreazione)).append("\n");
    sb.append("    descrTassonomiaEnte: ").append(toIndentedString(descrTassonomiaEnte)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    idTassonomiaEnte: ").append(toIndentedString(idTassonomiaEnte)).append("\n");
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

