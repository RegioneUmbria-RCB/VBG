package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Descrive una richiesta di storno da parte di un Ente Creditore\\nLa richiesta puo' avere varie motivazioni, fra cui il fatto che il pagamento e' gia' stato effettuato fuori pagoPA. In ogni caso a seguito della richiesta lo stato pagamento potra' eventualmente transitare in stato Annullato o Revocato (anche solo parzialmente) a seconda che il pagamento era gia' stato eseguito (con pagoPA) o era ancora in attesa 
 **/

public class RichiestaStornoType  {
  
  
 /**
   * Motivo della revoca Verra' usato come default per la causale delle singole voci di revoca, qualora non specificate esplicitamente 
  **/
  private String causale = null;

  
  private DettaglioRevocaType dettaglioRevoca = null;

  
  private IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria = null;

  
 /**
   * Identificativo Univoco di Versamento (IUV)
  **/
  private String iuv = null;

  
 /**
   * Definisce i possibili tipi di revoca   Richiesta proveniente da PSP   - 0: Tipo non codificato   - 1: Annullo Tecnico   - 2: Procedura di Charge Back   Richiesta proveniente da Ente Creditore   - 3: Storno I primi tre valori sono quelli usati nella richiesta di revoca pagoPA 
  **/
  private String tipoRevoca = null;
 /**
   * Motivo della revoca Verra&#39; usato come default per la causale delle singole voci di revoca, qualora non specificate esplicitamente 
   * @return causale
  **/
  @XmlElement(name="causale")
  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public RichiestaStornoType causale(String causale) {
    this.causale = causale;
    return this;
  }

 /**
   * Get dettaglioRevoca
   * @return dettaglioRevoca
  **/
  @XmlElement(name="dettaglio_revoca")
  public DettaglioRevocaType getDettaglioRevoca() {
    return dettaglioRevoca;
  }

  public void setDettaglioRevoca(DettaglioRevocaType dettaglioRevoca) {
    this.dettaglioRevoca = dettaglioRevoca;
  }

  public RichiestaStornoType dettaglioRevoca(DettaglioRevocaType dettaglioRevoca) {
    this.dettaglioRevoca = dettaglioRevoca;
    return this;
  }

 /**
   * Get identificativoPosizioneDebitoria
   * @return identificativoPosizioneDebitoria
  **/
  @XmlElement(name="identificativo_posizione_debitoria")
  public IdentificativoPosizioneDebitoriaType getIdentificativoPosizioneDebitoria() {
    return identificativoPosizioneDebitoria;
  }

  public void setIdentificativoPosizioneDebitoria(IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria) {
    this.identificativoPosizioneDebitoria = identificativoPosizioneDebitoria;
  }

  public RichiestaStornoType identificativoPosizioneDebitoria(IdentificativoPosizioneDebitoriaType identificativoPosizioneDebitoria) {
    this.identificativoPosizioneDebitoria = identificativoPosizioneDebitoria;
    return this;
  }

 /**
   * Identificativo Univoco di Versamento (IUV)
   * @return iuv
  **/
  @XmlElement(name="iuv")
  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public RichiestaStornoType iuv(String iuv) {
    this.iuv = iuv;
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

  public RichiestaStornoType tipoRevoca(String tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaStornoType {\n");
    
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    dettaglioRevoca: ").append(toIndentedString(dettaglioRevoca)).append("\n");
    sb.append("    identificativoPosizioneDebitoria: ").append(toIndentedString(identificativoPosizioneDebitoria)).append("\n");
    sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
    sb.append("    tipoRevoca: ").append(toIndentedString(tipoRevoca)).append("\n");
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

