package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class RichiestaPosizioneDebitoriaAscotType  {
  
  
 /**
   * Codice IPA dell'ente
  **/
  private String codiceIpa = null;

  
 /**
   * Codice moon dell'applicazione
  **/
  private String codiceMoon = null;

  
 /**
   * elenco posizione debitorie in formato csv
  **/
  private byte[] csv = null;

  
 /**
   * endpoint per la restituzione del giornale arricchito
  **/
  private String endpoint = null;

  
 /**
   * id del file
  **/
  private String idFile = null;

  
 /**
   * Codice tassonomico
  **/
  private String tassonomia = null;
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

  public RichiestaPosizioneDebitoriaAscotType codiceIpa(String codiceIpa) {
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

  public RichiestaPosizioneDebitoriaAscotType codiceMoon(String codiceMoon) {
    this.codiceMoon = codiceMoon;
    return this;
  }

 /**
   * elenco posizione debitorie in formato csv
   * @return csv
  **/
  @XmlElement(name="csv")
  public byte[] getCsv() {
    return csv;
  }

  public void setCsv(byte[] csv) {
    this.csv = csv;
  }

  public RichiestaPosizioneDebitoriaAscotType csv(byte[] csv) {
    this.csv = csv;
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

  public RichiestaPosizioneDebitoriaAscotType endpoint(String endpoint) {
    this.endpoint = endpoint;
    return this;
  }

 /**
   * id del file
   * @return idFile
  **/
  @XmlElement(name="id_file")
  public String getIdFile() {
    return idFile;
  }

  public void setIdFile(String idFile) {
    this.idFile = idFile;
  }

  public RichiestaPosizioneDebitoriaAscotType idFile(String idFile) {
    this.idFile = idFile;
    return this;
  }

 /**
   * Codice tassonomico
   * @return tassonomia
  **/
  @XmlElement(name="tassonomia")
  public String getTassonomia() {
    return tassonomia;
  }

  public void setTassonomia(String tassonomia) {
    this.tassonomia = tassonomia;
  }

  public RichiestaPosizioneDebitoriaAscotType tassonomia(String tassonomia) {
    this.tassonomia = tassonomia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaPosizioneDebitoriaAscotType {\n");
    
    sb.append("    codiceIpa: ").append(toIndentedString(codiceIpa)).append("\n");
    sb.append("    codiceMoon: ").append(toIndentedString(codiceMoon)).append("\n");
    sb.append("    csv: ").append(toIndentedString(csv)).append("\n");
    sb.append("    endpoint: ").append(toIndentedString(endpoint)).append("\n");
    sb.append("    idFile: ").append(toIndentedString(idFile)).append("\n");
    sb.append("    tassonomia: ").append(toIndentedString(tassonomia)).append("\n");
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

