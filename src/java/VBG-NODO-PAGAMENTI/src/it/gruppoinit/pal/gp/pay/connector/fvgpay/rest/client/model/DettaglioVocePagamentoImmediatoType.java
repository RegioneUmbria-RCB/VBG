package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Contiene il dettaglio di ogni voce di pagamento associata ad un pagamento identificato da uno specifico IUV Sono dati forniti da un verticale quando registra una posizione debitoria o recuperati dalla configurazione dei servizi di pagamento Devono essere obbligatoriamente specificati per ogni voce importo, causale e dati_specifici_riscossione I dati contabili di riscossione, se non specificati nella registrazione della posizione debitoria, vengono compilati dal Gateway usando quanto specificato durante l'attivazione di un servizio di pagamento per un Ente Creditore; l'eventuale importo della commissione di cui si fa carico l'Ente Creditore, se presente, deve essere diverso da 0.00 
 **/

public class DettaglioVocePagamentoImmediatoType  {
  
  
 /**
   * Descrizione della causale per la singola voce di pagamento (massimo 140 caratteri)
  **/
  private String causale = null;

  
  private ImportoType commissioneCaricoPa = null;

  
  private DatiSpecificiRiscossioneRendicontazioneAscotType datiSpecificiRiscossione = null;

  
  private ImportoType importo = null;

  
  private RichiestaBolloPagamentoImmediatoType richiestaBollo = null;
 /**
   * Descrizione della causale per la singola voce di pagamento (massimo 140 caratteri)
   * @return causale
  **/
  @XmlElement(name="causale")
  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public DettaglioVocePagamentoImmediatoType causale(String causale) {
    this.causale = causale;
    return this;
  }

 /**
   * Get commissioneCaricoPa
   * @return commissioneCaricoPa
  **/
  @XmlElement(name="commissione_carico_pa")
  public ImportoType getCommissioneCaricoPa() {
    return commissioneCaricoPa;
  }

  public void setCommissioneCaricoPa(ImportoType commissioneCaricoPa) {
    this.commissioneCaricoPa = commissioneCaricoPa;
  }

  public DettaglioVocePagamentoImmediatoType commissioneCaricoPa(ImportoType commissioneCaricoPa) {
    this.commissioneCaricoPa = commissioneCaricoPa;
    return this;
  }

 /**
   * Get datiSpecificiRiscossione
   * @return datiSpecificiRiscossione
  **/
  @XmlElement(name="dati_specifici_riscossione")
  public DatiSpecificiRiscossioneRendicontazioneAscotType getDatiSpecificiRiscossione() {
    return datiSpecificiRiscossione;
  }

  public void setDatiSpecificiRiscossione(DatiSpecificiRiscossioneRendicontazioneAscotType datiSpecificiRiscossione) {
    this.datiSpecificiRiscossione = datiSpecificiRiscossione;
  }

  public DettaglioVocePagamentoImmediatoType datiSpecificiRiscossione(DatiSpecificiRiscossioneRendicontazioneAscotType datiSpecificiRiscossione) {
    this.datiSpecificiRiscossione = datiSpecificiRiscossione;
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

  public DettaglioVocePagamentoImmediatoType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }

 /**
   * Get richiestaBollo
   * @return richiestaBollo
  **/
  @XmlElement(name="richiesta_bollo")
  public RichiestaBolloPagamentoImmediatoType getRichiestaBollo() {
    return richiestaBollo;
  }

  public void setRichiestaBollo(RichiestaBolloPagamentoImmediatoType richiestaBollo) {
    this.richiestaBollo = richiestaBollo;
  }

  public DettaglioVocePagamentoImmediatoType richiestaBollo(RichiestaBolloPagamentoImmediatoType richiestaBollo) {
    this.richiestaBollo = richiestaBollo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DettaglioVocePagamentoImmediatoType {\n");
    
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    commissioneCaricoPa: ").append(toIndentedString(commissioneCaricoPa)).append("\n");
    sb.append("    datiSpecificiRiscossione: ").append(toIndentedString(datiSpecificiRiscossione)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    richiestaBollo: ").append(toIndentedString(richiestaBollo)).append("\n");
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

