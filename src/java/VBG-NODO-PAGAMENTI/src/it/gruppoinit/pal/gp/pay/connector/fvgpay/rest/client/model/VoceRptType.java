package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Contiene i dati, estratti dalla RPT, che possono variare da una RPT all'altra per lo stesso pagamento, in quanto dipendenti  dal soggetto versante (eventualmente diverso dal debitore) e dalle sue scelte (PSP e modalita' di pagamento) 
 **/

public class VoceRptType  {
  
  
 /**
   * Bank Identifier Code di accredito, definito secondo lo standard ISO 9362 
  **/
  private String bicAccredito = null;

  
 /**
   * Bank Identifier Code dell'iban di appoggio, definito secondo lo standard ISO 9362 
  **/
  private String bicAppoggio = null;

  
 /**
   * International Bank Account Number del conto bancario o postale da accreditare, definito secondo lo standard ISO 13616 obbligatorio se e solo se la voce non � riferita al pagamento di una marca da bollo 
  **/
  private String ibanAccredito = null;

  
 /**
   * International Bank Account Number del conto, definito secondo lo standard ISO 13616, da accreditare presso un PSP che provveder� a trasferire i fondi incassati sul conto indicato nell�elemento ibanAccredito 
  **/
  private String ibanAppoggio = null;
 /**
   * Bank Identifier Code di accredito, definito secondo lo standard ISO 9362 
   * @return bicAccredito
  **/
  @XmlElement(name="bic_accredito")
  public String getBicAccredito() {
    return bicAccredito;
  }

  public void setBicAccredito(String bicAccredito) {
    this.bicAccredito = bicAccredito;
  }

  public VoceRptType bicAccredito(String bicAccredito) {
    this.bicAccredito = bicAccredito;
    return this;
  }

 /**
   * Bank Identifier Code dell&#39;iban di appoggio, definito secondo lo standard ISO 9362 
   * @return bicAppoggio
  **/
  @XmlElement(name="bic_appoggio")
  public String getBicAppoggio() {
    return bicAppoggio;
  }

  public void setBicAppoggio(String bicAppoggio) {
    this.bicAppoggio = bicAppoggio;
  }

  public VoceRptType bicAppoggio(String bicAppoggio) {
    this.bicAppoggio = bicAppoggio;
    return this;
  }

 /**
   * International Bank Account Number del conto bancario o postale da accreditare, definito secondo lo standard ISO 13616 obbligatorio se e solo se la voce non � riferita al pagamento di una marca da bollo 
   * @return ibanAccredito
  **/
  @XmlElement(name="iban_accredito")
  public String getIbanAccredito() {
    return ibanAccredito;
  }

  public void setIbanAccredito(String ibanAccredito) {
    this.ibanAccredito = ibanAccredito;
  }

  public VoceRptType ibanAccredito(String ibanAccredito) {
    this.ibanAccredito = ibanAccredito;
    return this;
  }

 /**
   * International Bank Account Number del conto, definito secondo lo standard ISO 13616, da accreditare presso un PSP che provveder� a trasferire i fondi incassati sul conto indicato nell�elemento ibanAccredito 
   * @return ibanAppoggio
  **/
  @XmlElement(name="iban_appoggio")
  public String getIbanAppoggio() {
    return ibanAppoggio;
  }

  public void setIbanAppoggio(String ibanAppoggio) {
    this.ibanAppoggio = ibanAppoggio;
  }

  public VoceRptType ibanAppoggio(String ibanAppoggio) {
    this.ibanAppoggio = ibanAppoggio;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VoceRptType {\n");
    
    sb.append("    bicAccredito: ").append(toIndentedString(bicAccredito)).append("\n");
    sb.append("    bicAppoggio: ").append(toIndentedString(bicAppoggio)).append("\n");
    sb.append("    ibanAccredito: ").append(toIndentedString(ibanAccredito)).append("\n");
    sb.append("    ibanAppoggio: ").append(toIndentedString(ibanAppoggio)).append("\n");
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

