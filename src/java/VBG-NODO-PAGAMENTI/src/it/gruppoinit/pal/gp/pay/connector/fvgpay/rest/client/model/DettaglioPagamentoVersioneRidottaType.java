package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
  * Contiene il dettaglio di un singolo pagamento presente nell'Archivio Pagamenti in Attesa: per ogni pagamento e' necessario specificare da 1 a 5 voci di dettaglio, ognuna della quali puo' descrivere - il pagamento di un debito nei confronti dell'Ente Creditore - il pagamento di una marca da bollo digitale Sono i dati con cui vengono generate le RPT, che per? possono contenere anche dati specifici della singola RPT raccolti a run-time in funzione delle scelte operate dal versante 
 **/

public class DettaglioPagamentoVersioneRidottaType  {
  
  
  private ImportoType importo = null;

  
 /**
   * Data entro cui effettuare il pagamento - secondo il formato ISO 8601 (YYYY-MM-DD) 
  **/
  private Date scadenza = null;

  
 /**
   * Data di scadenza avviso - secondo il formato ISO 8601 (YYYY-MM-DD)    
  **/
  private Date scadenzaAvviso = null;
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

  public DettaglioPagamentoVersioneRidottaType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }

 /**
   * Data entro cui effettuare il pagamento - secondo il formato ISO 8601 (YYYY-MM-DD) 
   * @return scadenza
  **/
  @XmlElement(name="scadenza")
  public Date getScadenza() {
    return scadenza;
  }

  public void setScadenza(Date scadenza) {
    this.scadenza = scadenza;
  }

  public DettaglioPagamentoVersioneRidottaType scadenza(Date scadenza) {
    this.scadenza = scadenza;
    return this;
  }

 /**
   * Data di scadenza avviso - secondo il formato ISO 8601 (YYYY-MM-DD)    
   * @return scadenzaAvviso
  **/
  @XmlElement(name="scadenza_avviso")
  public Date getScadenzaAvviso() {
    return scadenzaAvviso;
  }

  public void setScadenzaAvviso(Date scadenzaAvviso) {
    this.scadenzaAvviso = scadenzaAvviso;
  }

  public DettaglioPagamentoVersioneRidottaType scadenzaAvviso(Date scadenzaAvviso) {
    this.scadenzaAvviso = scadenzaAvviso;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DettaglioPagamentoVersioneRidottaType {\n");
    
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    scadenza: ").append(toIndentedString(scadenza)).append("\n");
    sb.append("    scadenzaAvviso: ").append(toIndentedString(scadenzaAvviso)).append("\n");
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

