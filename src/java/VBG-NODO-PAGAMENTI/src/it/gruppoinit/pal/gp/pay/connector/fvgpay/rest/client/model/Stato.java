package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class Stato  {
  
  
  private String codiceIstatStato = null;

  
  private String codiceNutsStato = null;

  
  private String denominazione = null;

  
  private String id = null;
 /**
   * Get codiceIstatStato
   * @return codiceIstatStato
  **/
  @XmlElement(name="codiceIstatStato")
  public String getCodiceIstatStato() {
    return codiceIstatStato;
  }

  public void setCodiceIstatStato(String codiceIstatStato) {
    this.codiceIstatStato = codiceIstatStato;
  }

  public Stato codiceIstatStato(String codiceIstatStato) {
    this.codiceIstatStato = codiceIstatStato;
    return this;
  }

 /**
   * Get codiceNutsStato
   * @return codiceNutsStato
  **/
  @XmlElement(name="codiceNutsStato")
  public String getCodiceNutsStato() {
    return codiceNutsStato;
  }

  public void setCodiceNutsStato(String codiceNutsStato) {
    this.codiceNutsStato = codiceNutsStato;
  }

  public Stato codiceNutsStato(String codiceNutsStato) {
    this.codiceNutsStato = codiceNutsStato;
    return this;
  }

 /**
   * Get denominazione
   * @return denominazione
  **/
  @XmlElement(name="denominazione")
  public String getDenominazione() {
    return denominazione;
  }

  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  public Stato denominazione(String denominazione) {
    this.denominazione = denominazione;
    return this;
  }

 /**
   * Get id
   * @return id
  **/
  @XmlElement(name="id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Stato id(String id) {
    this.id = id;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Stato {\n");
    
    sb.append("    codiceIstatStato: ").append(toIndentedString(codiceIstatStato)).append("\n");
    sb.append("    codiceNutsStato: ").append(toIndentedString(codiceNutsStato)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

