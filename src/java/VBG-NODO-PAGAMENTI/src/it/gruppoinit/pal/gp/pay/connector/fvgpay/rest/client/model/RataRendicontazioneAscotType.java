package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
  * Una rata si definisce con un importo ed una data di scadenza
 **/

public class RataRendicontazioneAscotType  {
  
  
  private Date dataScadenza = null;

  
  private Date dataScadenzaAvviso = null;

  
 /**
   * dati per la contabilita' ascot -  i dati sono una stringa Base64 encoded che rappresenta una sorgente di dati strutturati in un documento xml. 
  **/
  private byte[] datiContabili = null;

  
  private ImportoType importo = null;
 /**
   * Get dataScadenza
   * @return dataScadenza
  **/
  @XmlElement(name="data_scadenza")
  public Date getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(Date dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public RataRendicontazioneAscotType dataScadenza(Date dataScadenza) {
    this.dataScadenza = dataScadenza;
    return this;
  }

 /**
   * Get dataScadenzaAvviso
   * @return dataScadenzaAvviso
  **/
  @XmlElement(name="data_scadenza_avviso")
  public Date getDataScadenzaAvviso() {
    return dataScadenzaAvviso;
  }

  public void setDataScadenzaAvviso(Date dataScadenzaAvviso) {
    this.dataScadenzaAvviso = dataScadenzaAvviso;
  }

  public RataRendicontazioneAscotType dataScadenzaAvviso(Date dataScadenzaAvviso) {
    this.dataScadenzaAvviso = dataScadenzaAvviso;
    return this;
  }

 /**
   * dati per la contabilita&#39; ascot -  i dati sono una stringa Base64 encoded che rappresenta una sorgente di dati strutturati in un documento xml. 
   * @return datiContabili
  **/
  @XmlElement(name="dati_contabili")
  public byte[] getDatiContabili() {
    return datiContabili;
  }

  public void setDatiContabili(byte[] datiContabili) {
    this.datiContabili = datiContabili;
  }

  public RataRendicontazioneAscotType datiContabili(byte[] datiContabili) {
    this.datiContabili = datiContabili;
    return this;
  }

 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public ImportoType getImporto() {
    return importo;
  }

  public void setImporto(ImportoType importo) {
    this.importo = importo;
  }

  public RataRendicontazioneAscotType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RataRendicontazioneAscotType {\n");
    
    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
    sb.append("    dataScadenzaAvviso: ").append(toIndentedString(dataScadenzaAvviso)).append("\n");
    sb.append("    datiContabili: ").append(toIndentedString(datiContabili)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
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

