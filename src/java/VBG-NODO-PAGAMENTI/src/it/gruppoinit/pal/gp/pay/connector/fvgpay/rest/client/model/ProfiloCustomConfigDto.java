package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class ProfiloCustomConfigDto  {
  
  
  private String attivazione = null;

  
  private DominioDto dominio = null;

  
  private String idConfigProfilo = null;

  
  private String idConfigUtgest = null;

  
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

  public ProfiloCustomConfigDto attivazione(String attivazione) {
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

  public ProfiloCustomConfigDto dominio(DominioDto dominio) {
    this.dominio = dominio;
    return this;
  }

 /**
   * Get idConfigProfilo
   * @return idConfigProfilo
  **/
  @XmlElement(name="idConfigProfilo")
  public String getIdConfigProfilo() {
    return idConfigProfilo;
  }

  public void setIdConfigProfilo(String idConfigProfilo) {
    this.idConfigProfilo = idConfigProfilo;
  }

  public ProfiloCustomConfigDto idConfigProfilo(String idConfigProfilo) {
    this.idConfigProfilo = idConfigProfilo;
    return this;
  }

 /**
   * Get idConfigUtgest
   * @return idConfigUtgest
  **/
  @XmlElement(name="idConfigUtgest")
  public String getIdConfigUtgest() {
    return idConfigUtgest;
  }

  public void setIdConfigUtgest(String idConfigUtgest) {
    this.idConfigUtgest = idConfigUtgest;
  }

  public ProfiloCustomConfigDto idConfigUtgest(String idConfigUtgest) {
    this.idConfigUtgest = idConfigUtgest;
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

  public ProfiloCustomConfigDto ruolo(RuoloDto ruolo) {
    this.ruolo = ruolo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProfiloCustomConfigDto {\n");
    
    sb.append("    attivazione: ").append(toIndentedString(attivazione)).append("\n");
    sb.append("    dominio: ").append(toIndentedString(dominio)).append("\n");
    sb.append("    idConfigProfilo: ").append(toIndentedString(idConfigProfilo)).append("\n");
    sb.append("    idConfigUtgest: ").append(toIndentedString(idConfigUtgest)).append("\n");
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

