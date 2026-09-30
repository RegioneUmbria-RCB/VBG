package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Recapito postale del Pagatore a cui notificare l'avviso di pagamento 
 **/

public class RecapitoPostaleType  {
  
  
 /**
   * Codice di Avviamento Postale del recapito postale del Pagatore. Deve essere di 5 cifre 
  **/
  private String cap = null;

  
 /**
   * Numero civico del recapito postale del Pagatore 
  **/
  private String civico = null;

  
 /**
   * Indirizzo (con indicazione di via, piazza o altro toponimo) del recapito postale del Pagatore 
  **/
  private String indirizzo = null;

  
 /**
   * Nome della localita', comune del recapito postale del Pagatore 
  **/
  private String localita = null;

  
 /**
   * Codice nazione del recapito postale del Pagatore secondo lo standard ISO 3166 
  **/
  private String nazione = null;

  
 /**
   * Nome della provincia del recapito postale del Pagatore 
  **/
  private String provincia = null;
 /**
   * Codice di Avviamento Postale del recapito postale del Pagatore. Deve essere di 5 cifre 
   * @return cap
  **/
  @XmlElement(name="cap")
  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public RecapitoPostaleType cap(String cap) {
    this.cap = cap;
    return this;
  }

 /**
   * Numero civico del recapito postale del Pagatore 
   * @return civico
  **/
  @XmlElement(name="civico")
  public String getCivico() {
    return civico;
  }

  public void setCivico(String civico) {
    this.civico = civico;
  }

  public RecapitoPostaleType civico(String civico) {
    this.civico = civico;
    return this;
  }

 /**
   * Indirizzo (con indicazione di via, piazza o altro toponimo) del recapito postale del Pagatore 
   * @return indirizzo
  **/
  @XmlElement(name="indirizzo")
  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public RecapitoPostaleType indirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
    return this;
  }

 /**
   * Nome della localita&#39;, comune del recapito postale del Pagatore 
   * @return localita
  **/
  @XmlElement(name="localita")
  public String getLocalita() {
    return localita;
  }

  public void setLocalita(String localita) {
    this.localita = localita;
  }

  public RecapitoPostaleType localita(String localita) {
    this.localita = localita;
    return this;
  }

 /**
   * Codice nazione del recapito postale del Pagatore secondo lo standard ISO 3166 
   * @return nazione
  **/
  @XmlElement(name="nazione")
  public String getNazione() {
    return nazione;
  }

  public void setNazione(String nazione) {
    this.nazione = nazione;
  }

  public RecapitoPostaleType nazione(String nazione) {
    this.nazione = nazione;
    return this;
  }

 /**
   * Nome della provincia del recapito postale del Pagatore 
   * @return provincia
  **/
  @XmlElement(name="provincia")
  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public RecapitoPostaleType provincia(String provincia) {
    this.provincia = provincia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecapitoPostaleType {\n");
    
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    civico: ").append(toIndentedString(civico)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    localita: ").append(toIndentedString(localita)).append("\n");
    sb.append("    nazione: ").append(toIndentedString(nazione)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
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

