package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class RichiestaGiornaleDiCassaType  {
  
  
 /**
   * Codice IPA dell'ente
  **/
  private String codiceIpa = null;

  
 /**
   * Codice moon dell'applicazione
  **/
  private String codiceMoon = null;

  
 /**
   * endpoint per la restituzione del giornale arricchito
  **/
  private String endpoint = null;

  
 /**
   * giornale di cassa
  **/
  private byte[] giornaleDiCassa = null;

  
 /**
   * identificativo del flusso del giornale
  **/
  private Long idGiornale = null;
 /**
   * Codice IPA dell&#39;ente
   * @return codiceIpa
  **/
  @XmlElement(name="codice_ipa")
  public String getCodiceIpa() {
    return codiceIpa;
  }

  public void setCodiceIpa(String codiceIpa) {
    this.codiceIpa = codiceIpa;
  }

  public RichiestaGiornaleDiCassaType codiceIpa(String codiceIpa) {
    this.codiceIpa = codiceIpa;
    return this;
  }

 /**
   * Codice moon dell&#39;applicazione
   * @return codiceMoon
  **/
  @XmlElement(name="codice_moon")
  public String getCodiceMoon() {
    return codiceMoon;
  }

  public void setCodiceMoon(String codiceMoon) {
    this.codiceMoon = codiceMoon;
  }

  public RichiestaGiornaleDiCassaType codiceMoon(String codiceMoon) {
    this.codiceMoon = codiceMoon;
    return this;
  }

 /**
   * endpoint per la restituzione del giornale arricchito
   * @return endpoint
  **/
  @XmlElement(name="endpoint")
  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public RichiestaGiornaleDiCassaType endpoint(String endpoint) {
    this.endpoint = endpoint;
    return this;
  }

 /**
   * giornale di cassa
   * @return giornaleDiCassa
  **/
  @XmlElement(name="giornale_di_cassa")
  public byte[] getGiornaleDiCassa() {
    return giornaleDiCassa;
  }

  public void setGiornaleDiCassa(byte[] giornaleDiCassa) {
    this.giornaleDiCassa = giornaleDiCassa;
  }

  public RichiestaGiornaleDiCassaType giornaleDiCassa(byte[] giornaleDiCassa) {
    this.giornaleDiCassa = giornaleDiCassa;
    return this;
  }

 /**
   * identificativo del flusso del giornale
   * @return idGiornale
  **/
  @XmlElement(name="id_giornale")
  public Long getIdGiornale() {
    return idGiornale;
  }

  public void setIdGiornale(Long idGiornale) {
    this.idGiornale = idGiornale;
  }

  public RichiestaGiornaleDiCassaType idGiornale(Long idGiornale) {
    this.idGiornale = idGiornale;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaGiornaleDiCassaType {\n");
    
    sb.append("    codiceIpa: ").append(toIndentedString(codiceIpa)).append("\n");
    sb.append("    codiceMoon: ").append(toIndentedString(codiceMoon)).append("\n");
    sb.append("    endpoint: ").append(toIndentedString(endpoint)).append("\n");
    sb.append("    giornaleDiCassa: ").append(toIndentedString(giornaleDiCassa)).append("\n");
    sb.append("    idGiornale: ").append(toIndentedString(idGiornale)).append("\n");
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

