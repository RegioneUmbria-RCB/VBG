package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class AnagraficaDebitoreDto  {
  
  
  private String cap = null;

  
  private String citta = null;

  
  private String codiceFiscale = null;

  
  private String cognome = null;

  
  private String indirizzo = null;

  
  private String mail = null;

  
  private String mailPEC = null;

  
  private String nazione = null;

  
  private String nome = null;

  
  private String provincia = null;

  
  private String rifTelefonico = null;

  
  private String tipoAnagrafica = null;
 /**
   * Get cap
   * @return cap
  **/
  @XmlElement(name="cap")
  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public AnagraficaDebitoreDto cap(String cap) {
    this.cap = cap;
    return this;
  }

 /**
   * Get citta
   * @return citta
  **/
  @XmlElement(name="citta")
  public String getCitta() {
    return citta;
  }

  public void setCitta(String citta) {
    this.citta = citta;
  }

  public AnagraficaDebitoreDto citta(String citta) {
    this.citta = citta;
    return this;
  }

 /**
   * Get codiceFiscale
   * @return codiceFiscale
  **/
  @XmlElement(name="codiceFiscale")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public AnagraficaDebitoreDto codiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
    return this;
  }

 /**
   * Get cognome
   * @return cognome
  **/
  @XmlElement(name="cognome")
  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public AnagraficaDebitoreDto cognome(String cognome) {
    this.cognome = cognome;
    return this;
  }

 /**
   * Get indirizzo
   * @return indirizzo
  **/
  @XmlElement(name="indirizzo")
  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public AnagraficaDebitoreDto indirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
    return this;
  }

 /**
   * Get mail
   * @return mail
  **/
  @XmlElement(name="mail")
  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public AnagraficaDebitoreDto mail(String mail) {
    this.mail = mail;
    return this;
  }

 /**
   * Get mailPEC
   * @return mailPEC
  **/
  @XmlElement(name="mailPEC")
  public String getMailPEC() {
    return mailPEC;
  }

  public void setMailPEC(String mailPEC) {
    this.mailPEC = mailPEC;
  }

  public AnagraficaDebitoreDto mailPEC(String mailPEC) {
    this.mailPEC = mailPEC;
    return this;
  }

 /**
   * Get nazione
   * @return nazione
  **/
  @XmlElement(name="nazione")
  public String getNazione() {
    return nazione;
  }

  public void setNazione(String nazione) {
    this.nazione = nazione;
  }

  public AnagraficaDebitoreDto nazione(String nazione) {
    this.nazione = nazione;
    return this;
  }

 /**
   * Get nome
   * @return nome
  **/
  @XmlElement(name="nome")
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public AnagraficaDebitoreDto nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Get provincia
   * @return provincia
  **/
  @XmlElement(name="provincia")
  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public AnagraficaDebitoreDto provincia(String provincia) {
    this.provincia = provincia;
    return this;
  }

 /**
   * Get rifTelefonico
   * @return rifTelefonico
  **/
  @XmlElement(name="rifTelefonico")
  public String getRifTelefonico() {
    return rifTelefonico;
  }

  public void setRifTelefonico(String rifTelefonico) {
    this.rifTelefonico = rifTelefonico;
  }

  public AnagraficaDebitoreDto rifTelefonico(String rifTelefonico) {
    this.rifTelefonico = rifTelefonico;
    return this;
  }

 /**
   * Get tipoAnagrafica
   * @return tipoAnagrafica
  **/
  @XmlElement(name="tipoAnagrafica")
  public String getTipoAnagrafica() {
    return tipoAnagrafica;
  }

  public void setTipoAnagrafica(String tipoAnagrafica) {
    this.tipoAnagrafica = tipoAnagrafica;
  }

  public AnagraficaDebitoreDto tipoAnagrafica(String tipoAnagrafica) {
    this.tipoAnagrafica = tipoAnagrafica;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnagraficaDebitoreDto {\n");
    
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    citta: ").append(toIndentedString(citta)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    mailPEC: ").append(toIndentedString(mailPEC)).append("\n");
    sb.append("    nazione: ").append(toIndentedString(nazione)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    rifTelefonico: ").append(toIndentedString(rifTelefonico)).append("\n");
    sb.append("    tipoAnagrafica: ").append(toIndentedString(tipoAnagrafica)).append("\n");
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

