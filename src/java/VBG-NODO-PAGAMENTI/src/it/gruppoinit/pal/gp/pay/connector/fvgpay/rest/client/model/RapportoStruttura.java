package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class RapportoStruttura  {
  
  
  private XMLGregorianCalendar dataFine = null;

  
  private XMLGregorianCalendar dataInizio = null;

  
  private String email = null;

  
  private String idRapporto = null;

  
  private Legame legame = null;

  
  private String matricola = null;

  
  private PersonaFisicaBase personaFisicaBase = null;

  
  private String telefono = null;
 /**
   * Get dataFine
   * @return dataFine
  **/
  @XmlElement(name="dataFine")
  public XMLGregorianCalendar getDataFine() {
    return dataFine;
  }

  public void setDataFine(XMLGregorianCalendar dataFine) {
    this.dataFine = dataFine;
  }

  public RapportoStruttura dataFine(XMLGregorianCalendar dataFine) {
    this.dataFine = dataFine;
    return this;
  }

 /**
   * Get dataInizio
   * @return dataInizio
  **/
  @XmlElement(name="dataInizio")
  public XMLGregorianCalendar getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(XMLGregorianCalendar dataInizio) {
    this.dataInizio = dataInizio;
  }

  public RapportoStruttura dataInizio(XMLGregorianCalendar dataInizio) {
    this.dataInizio = dataInizio;
    return this;
  }

 /**
   * Get email
   * @return email
  **/
  @XmlElement(name="email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public RapportoStruttura email(String email) {
    this.email = email;
    return this;
  }

 /**
   * Get idRapporto
   * @return idRapporto
  **/
  @XmlElement(name="idRapporto")
  public String getIdRapporto() {
    return idRapporto;
  }

  public void setIdRapporto(String idRapporto) {
    this.idRapporto = idRapporto;
  }

  public RapportoStruttura idRapporto(String idRapporto) {
    this.idRapporto = idRapporto;
    return this;
  }

 /**
   * Get legame
   * @return legame
  **/
  @XmlElement(name="legame")
  public Legame getLegame() {
    return legame;
  }

  public void setLegame(Legame legame) {
    this.legame = legame;
  }

  public RapportoStruttura legame(Legame legame) {
    this.legame = legame;
    return this;
  }

 /**
   * Get matricola
   * @return matricola
  **/
  @XmlElement(name="matricola")
  public String getMatricola() {
    return matricola;
  }

  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }

  public RapportoStruttura matricola(String matricola) {
    this.matricola = matricola;
    return this;
  }

 /**
   * Get personaFisicaBase
   * @return personaFisicaBase
  **/
  @XmlElement(name="personaFisicaBase")
  public PersonaFisicaBase getPersonaFisicaBase() {
    return personaFisicaBase;
  }

  public void setPersonaFisicaBase(PersonaFisicaBase personaFisicaBase) {
    this.personaFisicaBase = personaFisicaBase;
  }

  public RapportoStruttura personaFisicaBase(PersonaFisicaBase personaFisicaBase) {
    this.personaFisicaBase = personaFisicaBase;
    return this;
  }

 /**
   * Get telefono
   * @return telefono
  **/
  @XmlElement(name="telefono")
  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public RapportoStruttura telefono(String telefono) {
    this.telefono = telefono;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RapportoStruttura {\n");
    
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
    sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    idRapporto: ").append(toIndentedString(idRapporto)).append("\n");
    sb.append("    legame: ").append(toIndentedString(legame)).append("\n");
    sb.append("    matricola: ").append(toIndentedString(matricola)).append("\n");
    sb.append("    personaFisicaBase: ").append(toIndentedString(personaFisicaBase)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
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

