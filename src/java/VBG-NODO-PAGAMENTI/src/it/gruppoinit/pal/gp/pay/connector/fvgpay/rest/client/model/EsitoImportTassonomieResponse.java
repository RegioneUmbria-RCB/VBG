package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class EsitoImportTassonomieResponse  {
  
  
  private String errore = null;

  
  private Boolean esito = null;

  
  private Integer tassonomieImportate = null;

  
  private String versione = null;
 /**
   * Get errore
   * @return errore
  **/
  @XmlElement(name="errore")
  public String getErrore() {
    return errore;
  }

  public void setErrore(String errore) {
    this.errore = errore;
  }

  public EsitoImportTassonomieResponse errore(String errore) {
    this.errore = errore;
    return this;
  }

 /**
   * Get esito
   * @return esito
  **/
  @XmlElement(name="esito")
  public Boolean isEsito() {
    return esito;
  }

  public void setEsito(Boolean esito) {
    this.esito = esito;
  }

  public EsitoImportTassonomieResponse esito(Boolean esito) {
    this.esito = esito;
    return this;
  }

 /**
   * Get tassonomieImportate
   * @return tassonomieImportate
  **/
  @XmlElement(name="tassonomieImportate")
  public Integer getTassonomieImportate() {
    return tassonomieImportate;
  }

  public void setTassonomieImportate(Integer tassonomieImportate) {
    this.tassonomieImportate = tassonomieImportate;
  }

  public EsitoImportTassonomieResponse tassonomieImportate(Integer tassonomieImportate) {
    this.tassonomieImportate = tassonomieImportate;
    return this;
  }

 /**
   * Get versione
   * @return versione
  **/
  @XmlElement(name="versione")
  public String getVersione() {
    return versione;
  }

  public void setVersione(String versione) {
    this.versione = versione;
  }

  public EsitoImportTassonomieResponse versione(String versione) {
    this.versione = versione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoImportTassonomieResponse {\n");
    
    sb.append("    errore: ").append(toIndentedString(errore)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    tassonomieImportate: ").append(toIndentedString(tassonomieImportate)).append("\n");
    sb.append("    versione: ").append(toIndentedString(versione)).append("\n");
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

