package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class ProfiloCustomDto  {
  
  
  private String attivazione = null;

  
  private DominioDto dominio = null;

  
  private Long idMacroArea = null;

  
  private String idProfilo = null;

  
  private String idUtenteGestione = null;

  
  private RuoloDto ruolo = null;
 /**
   * Get attivazione
   * @return attivazione
  **/
  @XmlElement(name="attivazione")
  public String getAttivazione() {
    return attivazione;
  }

  public void setAttivazione(String attivazione) {
    this.attivazione = attivazione;
  }

  public ProfiloCustomDto attivazione(String attivazione) {
    this.attivazione = attivazione;
    return this;
  }

 /**
   * Get dominio
   * @return dominio
  **/
  @XmlElement(name="dominio")
  public DominioDto getDominio() {
    return dominio;
  }

  public void setDominio(DominioDto dominio) {
    this.dominio = dominio;
  }

  public ProfiloCustomDto dominio(DominioDto dominio) {
    this.dominio = dominio;
    return this;
  }

 /**
   * Get idMacroArea
   * @return idMacroArea
  **/
  @XmlElement(name="idMacroArea")
  public Long getIdMacroArea() {
    return idMacroArea;
  }

  public void setIdMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
  }

  public ProfiloCustomDto idMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
    return this;
  }

 /**
   * Get idProfilo
   * @return idProfilo
  **/
  @XmlElement(name="idProfilo")
  public String getIdProfilo() {
    return idProfilo;
  }

  public void setIdProfilo(String idProfilo) {
    this.idProfilo = idProfilo;
  }

  public ProfiloCustomDto idProfilo(String idProfilo) {
    this.idProfilo = idProfilo;
    return this;
  }

 /**
   * Get idUtenteGestione
   * @return idUtenteGestione
  **/
  @XmlElement(name="idUtenteGestione")
  public String getIdUtenteGestione() {
    return idUtenteGestione;
  }

  public void setIdUtenteGestione(String idUtenteGestione) {
    this.idUtenteGestione = idUtenteGestione;
  }

  public ProfiloCustomDto idUtenteGestione(String idUtenteGestione) {
    this.idUtenteGestione = idUtenteGestione;
    return this;
  }

 /**
   * Get ruolo
   * @return ruolo
  **/
  @XmlElement(name="ruolo")
  public RuoloDto getRuolo() {
    return ruolo;
  }

  public void setRuolo(RuoloDto ruolo) {
    this.ruolo = ruolo;
  }

  public ProfiloCustomDto ruolo(RuoloDto ruolo) {
    this.ruolo = ruolo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProfiloCustomDto {\n");
    
    sb.append("    attivazione: ").append(toIndentedString(attivazione)).append("\n");
    sb.append("    dominio: ").append(toIndentedString(dominio)).append("\n");
    sb.append("    idMacroArea: ").append(toIndentedString(idMacroArea)).append("\n");
    sb.append("    idProfilo: ").append(toIndentedString(idProfilo)).append("\n");
    sb.append("    idUtenteGestione: ").append(toIndentedString(idUtenteGestione)).append("\n");
    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
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

