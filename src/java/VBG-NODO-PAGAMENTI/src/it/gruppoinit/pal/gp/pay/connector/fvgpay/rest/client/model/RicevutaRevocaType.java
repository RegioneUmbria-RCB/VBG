package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
  * Contiene se richiesto il messaggio tecnico pagoPA di Esito della Richiesta di Revoca da cui viene estratta la data 
 **/

public class RicevutaRevocaType  {
  
  
 /**
   * data in cui viene fornito l'esito della richiesta di revoca 
  **/
  private Date dataRevoca = null;

  
  private byte[] xmlEr = null;
 /**
   * data in cui viene fornito l&#39;esito della richiesta di revoca 
   * @return dataRevoca
  **/
  @XmlElement(name="data_revoca")
  public Date getDataRevoca() {
    return dataRevoca;
  }

  public void setDataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
  }

  public RicevutaRevocaType dataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
    return this;
  }

 /**
   * Get xmlEr
   * @return xmlEr
  **/
  @XmlElement(name="xml_er")
  public byte[] getXmlEr() {
    return xmlEr;
  }

  public void setXmlEr(byte[] xmlEr) {
    this.xmlEr = xmlEr;
  }

  public RicevutaRevocaType xmlEr(byte[] xmlEr) {
    this.xmlEr = xmlEr;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RicevutaRevocaType {\n");
    
    sb.append("    dataRevoca: ").append(toIndentedString(dataRevoca)).append("\n");
    sb.append("    xmlEr: ").append(toIndentedString(xmlEr)).append("\n");
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

