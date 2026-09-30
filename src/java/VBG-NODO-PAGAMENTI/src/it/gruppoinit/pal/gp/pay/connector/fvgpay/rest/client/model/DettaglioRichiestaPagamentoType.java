package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
  * Contiene il dettaglio di una richiesta di registrazione di una posizione debitoria nell'Archivio Pagamenti in Attesa Per ogni pagamento e' necessario specificare da 1 a 5 voci di dettaglio, ogni voce puo' descrivere: - il pagamento di un debito nei confronti dell'Ente Creditore - il pagamento di una marca da bollo digitale E' possibile specificare in alternativa importo_totale e descrizione_pagamento o dettaglio_voci_pagamento - nel primo caso viene creata una singola voce di pagamento verso l'Ente Creditore con importo e causale specificati - nel secondo caso l'importo totale viene calcolato sommando gli importi di tutte le voci - se vengono specificati sia una casuale che il dettaglio delle voci,   la causale verra' eventualmente usata come default per le singole voci - se vengono specificati sia un importo_totale che il dettaglio delle voci,    il totale deve essere uguale alla somma dei singoli importi 
 **/

public class DettaglioRichiestaPagamentoType  {
  
  
 /**
   * annualità a cui è riferito il pagamento
  **/
  private Integer annoRiferimento = null;

  
 /**
   * dati da pubblicare su una eventuale lettera accompagnatoria - la lettera viene notificata insieme all'avviso di pagamento analogico - viene formata a partire da un modello configurato al momento dell'attivazione del servizio - i dati sono una stringa Base64 encoded che rappresenta una sorgente di dati strutturati:   potrebbe essere un documento xml o un riferimento qualsiasi ad una sorgente dati   la struttura dei dati ed il meodo di recuperarli devono essere noti al servizio di   composizione documenti che deve comporre la lettera a partire dal modello e dai dati 
  **/
  private byte[] datiAggiuntiviAvviso = null;

  
 /**
   * Descrizione della causale di pagamento: - viene usata nell'avviso di pagamento - può essere usata come default per la causale delle singole voci 
  **/
  private String descrizionePagamento = null;

  
 /**
   * Elenco delle singole voci di pagamento (da 1 a 5)
  **/
  private List<DettaglioRichiestaVocePagamentoType> dettaglioVociPagamento = null;

  
  private ImportoType importoTotale = null;

  
 /**
   * Data fino a cui l'avviso di pagamento rimane valido - usare il formato ISO 8601 (YYYY-MM-DD) - va usata nell'avvisatura digitale - deve essere precedente alla data di scadenza NOTA: se l'avviso digitale non viene notificato mediante il servizio pagoPA, pagoPA-SPC ed i PSP non hanno a disposizione l'informazione, non essendo veicolata con una RPT 
  **/
  private Date scadenzaAvviso = null;

  
 /**
   * Data entro cui effettuare il pagamento - usare il formato ISO 8601 (YYYY-MM-DD) Alla scadenza si dovrebbe generare una notifica di scadenza 
  **/
  private Date scadenzaPagamento = null;
 /**
   * annualità a cui è riferito il pagamento
   * @return annoRiferimento
  **/
  @XmlElement(name="anno_riferimento")
  public Integer getAnnoRiferimento() {
    return annoRiferimento;
  }

  public void setAnnoRiferimento(Integer annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
  }

  public DettaglioRichiestaPagamentoType annoRiferimento(Integer annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
    return this;
  }

 /**
   * dati da pubblicare su una eventuale lettera accompagnatoria - la lettera viene notificata insieme all&#39;avviso di pagamento analogico - viene formata a partire da un modello configurato al momento dell&#39;attivazione del servizio - i dati sono una stringa Base64 encoded che rappresenta una sorgente di dati strutturati:   potrebbe essere un documento xml o un riferimento qualsiasi ad una sorgente dati   la struttura dei dati ed il meodo di recuperarli devono essere noti al servizio di   composizione documenti che deve comporre la lettera a partire dal modello e dai dati 
   * @return datiAggiuntiviAvviso
  **/
  @XmlElement(name="dati_aggiuntivi_avviso")
  public byte[] getDatiAggiuntiviAvviso() {
    return datiAggiuntiviAvviso;
  }

  public void setDatiAggiuntiviAvviso(byte[] datiAggiuntiviAvviso) {
    this.datiAggiuntiviAvviso = datiAggiuntiviAvviso;
  }

  public DettaglioRichiestaPagamentoType datiAggiuntiviAvviso(byte[] datiAggiuntiviAvviso) {
    this.datiAggiuntiviAvviso = datiAggiuntiviAvviso;
    return this;
  }

 /**
   * Descrizione della causale di pagamento: - viene usata nell&#39;avviso di pagamento - può essere usata come default per la causale delle singole voci 
   * @return descrizionePagamento
  **/
  @XmlElement(name="descrizione_pagamento")
  public String getDescrizionePagamento() {
    return descrizionePagamento;
  }

  public void setDescrizionePagamento(String descrizionePagamento) {
    this.descrizionePagamento = descrizionePagamento;
  }

  public DettaglioRichiestaPagamentoType descrizionePagamento(String descrizionePagamento) {
    this.descrizionePagamento = descrizionePagamento;
    return this;
  }

 /**
   * Elenco delle singole voci di pagamento (da 1 a 5)
   * @return dettaglioVociPagamento
  **/
  @XmlElement(name="dettaglio_voci_pagamento")
  public List<DettaglioRichiestaVocePagamentoType> getDettaglioVociPagamento() {
    return dettaglioVociPagamento;
  }

  public void setDettaglioVociPagamento(List<DettaglioRichiestaVocePagamentoType> dettaglioVociPagamento) {
    this.dettaglioVociPagamento = dettaglioVociPagamento;
  }

  public DettaglioRichiestaPagamentoType dettaglioVociPagamento(List<DettaglioRichiestaVocePagamentoType> dettaglioVociPagamento) {
    this.dettaglioVociPagamento = dettaglioVociPagamento;
    return this;
  }

  public DettaglioRichiestaPagamentoType addDettaglioVociPagamentoItem(DettaglioRichiestaVocePagamentoType dettaglioVociPagamentoItem) {
    this.dettaglioVociPagamento.add(dettaglioVociPagamentoItem);
    return this;
  }

 /**
   * Get importoTotale
   * @return importoTotale
  **/
  @XmlElement(name="importo_totale")
  public ImportoType getImportoTotale() {
    return importoTotale;
  }

  public void setImportoTotale(ImportoType importoTotale) {
    this.importoTotale = importoTotale;
  }

  public DettaglioRichiestaPagamentoType importoTotale(ImportoType importoTotale) {
    this.importoTotale = importoTotale;
    return this;
  }

 /**
   * Data fino a cui l&#39;avviso di pagamento rimane valido - usare il formato ISO 8601 (YYYY-MM-DD) - va usata nell&#39;avvisatura digitale - deve essere precedente alla data di scadenza NOTA: se l&#39;avviso digitale non viene notificato mediante il servizio pagoPA, pagoPA-SPC ed i PSP non hanno a disposizione l&#39;informazione, non essendo veicolata con una RPT 
   * @return scadenzaAvviso
  **/
  @XmlElement(name="scadenza_avviso")
  public Date getScadenzaAvviso() {
    return scadenzaAvviso;
  }

  public void setScadenzaAvviso(Date scadenzaAvviso) {
    this.scadenzaAvviso = scadenzaAvviso;
  }

  public DettaglioRichiestaPagamentoType scadenzaAvviso(Date scadenzaAvviso) {
    this.scadenzaAvviso = scadenzaAvviso;
    return this;
  }

 /**
   * Data entro cui effettuare il pagamento - usare il formato ISO 8601 (YYYY-MM-DD) Alla scadenza si dovrebbe generare una notifica di scadenza 
   * @return scadenzaPagamento
  **/
  @XmlElement(name="scadenza_pagamento")
  public Date getScadenzaPagamento() {
    return scadenzaPagamento;
  }

  public void setScadenzaPagamento(Date scadenzaPagamento) {
    this.scadenzaPagamento = scadenzaPagamento;
  }

  public DettaglioRichiestaPagamentoType scadenzaPagamento(Date scadenzaPagamento) {
    this.scadenzaPagamento = scadenzaPagamento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DettaglioRichiestaPagamentoType {\n");
    
    sb.append("    annoRiferimento: ").append(toIndentedString(annoRiferimento)).append("\n");
    sb.append("    datiAggiuntiviAvviso: ").append(toIndentedString(datiAggiuntiviAvviso)).append("\n");
    sb.append("    descrizionePagamento: ").append(toIndentedString(descrizionePagamento)).append("\n");
    sb.append("    dettaglioVociPagamento: ").append(toIndentedString(dettaglioVociPagamento)).append("\n");
    sb.append("    importoTotale: ").append(toIndentedString(importoTotale)).append("\n");
    sb.append("    scadenzaAvviso: ").append(toIndentedString(scadenzaAvviso)).append("\n");
    sb.append("    scadenzaPagamento: ").append(toIndentedString(scadenzaPagamento)).append("\n");
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

