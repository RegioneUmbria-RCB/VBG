package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
  * Una rata si definisce con un importo ed una data di scadenza
 **/

public class RataType  {
  
  
  private Date dataScadenza = null;

  
  private Date dataScadenzaAvviso = null;

  
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

  public RataType dataScadenza(Date dataScadenza) {
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

  public RataType dataScadenzaAvviso(Date dataScadenzaAvviso) {
    this.dataScadenzaAvviso = dataScadenzaAvviso;
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

  public RataType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RataType {\n");
    
    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
    sb.append("    dataScadenzaAvviso: ").append(toIndentedString(dataScadenzaAvviso)).append("\n");
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

