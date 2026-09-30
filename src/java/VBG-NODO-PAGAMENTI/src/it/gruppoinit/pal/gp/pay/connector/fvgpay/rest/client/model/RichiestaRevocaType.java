package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
  * Contiene se richiesto il messaggio tecnico pagoPA di richiesta Revoca da cui viene estratta la data della richiesta Il tipo_revoca determina se la richiesta e' stata determinata dall'Ente Creditore o dal PSP e se si tratta di Annullo Tecnico 
 **/

public class RichiestaRevocaType  {
  
  
 /**
   * data della richiesta di revoca
  **/
  private Date dataRichiestaRevoca = null;

  
 /**
   * Definisce i possibili tipi di revoca   Richiesta proveniente da PSP   - 0: Tipo non codificato   - 1: Annullo Tecnico   - 2: Procedura di Charge Back   Richiesta proveniente da Ente Creditore   - 3: Storno I primi tre valori sono quelli usati nella richiesta di revoca pagoPA 
  **/
  private String tipoRevoca = null;

  
  private byte[] xmlRr = null;
 /**
   * data della richiesta di revoca
   * @return dataRichiestaRevoca
  **/
  @XmlElement(name="data_richiesta_revoca")
  public Date getDataRichiestaRevoca() {
    return dataRichiestaRevoca;
  }

  public void setDataRichiestaRevoca(Date dataRichiestaRevoca) {
    this.dataRichiestaRevoca = dataRichiestaRevoca;
  }

  public RichiestaRevocaType dataRichiestaRevoca(Date dataRichiestaRevoca) {
    this.dataRichiestaRevoca = dataRichiestaRevoca;
    return this;
  }

 /**
   * Definisce i possibili tipi di revoca   Richiesta proveniente da PSP   - 0: Tipo non codificato   - 1: Annullo Tecnico   - 2: Procedura di Charge Back   Richiesta proveniente da Ente Creditore   - 3: Storno I primi tre valori sono quelli usati nella richiesta di revoca pagoPA 
   * @return tipoRevoca
  **/
  @XmlElement(name="tipo_revoca")
  public String getTipoRevoca() {
    return tipoRevoca;
  }

  public void setTipoRevoca(String tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
  }

  public RichiestaRevocaType tipoRevoca(String tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
    return this;
  }

 /**
   * Get xmlRr
   * @return xmlRr
  **/
  @XmlElement(name="xml_rr")
  public byte[] getXmlRr() {
    return xmlRr;
  }

  public void setXmlRr(byte[] xmlRr) {
    this.xmlRr = xmlRr;
  }

  public RichiestaRevocaType xmlRr(byte[] xmlRr) {
    this.xmlRr = xmlRr;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaRevocaType {\n");
    
    sb.append("    dataRichiestaRevoca: ").append(toIndentedString(dataRichiestaRevoca)).append("\n");
    sb.append("    tipoRevoca: ").append(toIndentedString(tipoRevoca)).append("\n");
    sb.append("    xmlRr: ").append(toIndentedString(xmlRr)).append("\n");
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

