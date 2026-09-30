package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class EsitoGetPosizioneDebitoriaResponse  {
  
  
  private AnagraficaCreditoreDto creditore = null;

  
  private AnagraficaDebitoreDto debitore = null;

  
  private DatiPendenzaDto posizione = null;
 /**
   * Get creditore
   * @return creditore
  **/
  @XmlElement(name="creditore")
  public AnagraficaCreditoreDto getCreditore() {
    return creditore;
  }

  public void setCreditore(AnagraficaCreditoreDto creditore) {
    this.creditore = creditore;
  }

  public EsitoGetPosizioneDebitoriaResponse creditore(AnagraficaCreditoreDto creditore) {
    this.creditore = creditore;
    return this;
  }

 /**
   * Get debitore
   * @return debitore
  **/
  @XmlElement(name="debitore")
  public AnagraficaDebitoreDto getDebitore() {
    return debitore;
  }

  public void setDebitore(AnagraficaDebitoreDto debitore) {
    this.debitore = debitore;
  }

  public EsitoGetPosizioneDebitoriaResponse debitore(AnagraficaDebitoreDto debitore) {
    this.debitore = debitore;
    return this;
  }

 /**
   * Get posizione
   * @return posizione
  **/
  @XmlElement(name="posizione")
  public DatiPendenzaDto getPosizione() {
    return posizione;
  }

  public void setPosizione(DatiPendenzaDto posizione) {
    this.posizione = posizione;
  }

  public EsitoGetPosizioneDebitoriaResponse posizione(DatiPendenzaDto posizione) {
    this.posizione = posizione;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoGetPosizioneDebitoriaResponse {\n");
    
    sb.append("    creditore: ").append(toIndentedString(creditore)).append("\n");
    sb.append("    debitore: ").append(toIndentedString(debitore)).append("\n");
    sb.append("    posizione: ").append(toIndentedString(posizione)).append("\n");
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

