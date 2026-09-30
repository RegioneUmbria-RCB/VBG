package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Sede  {
  
  
  private String denominazioneSede = null;

  
  private String idSede = null;

  
  private Indirizzo indirizzoSede = null;

  
  private Intervallo intervalliAperturaSede = null;

  
  private ListaClassificazioni listaClassificazioniSede = null;

  
  private ListaOrariApertura listaOrariSede = null;

  
  private ListaRiferimenti listaRiferimentiSede = null;

  
  private Boolean principale = null;
 /**
   * Get denominazioneSede
   * @return denominazioneSede
  **/
  @XmlElement(name="denominazioneSede")
  public String getDenominazioneSede() {
    return denominazioneSede;
  }

  public void setDenominazioneSede(String denominazioneSede) {
    this.denominazioneSede = denominazioneSede;
  }

  public Sede denominazioneSede(String denominazioneSede) {
    this.denominazioneSede = denominazioneSede;
    return this;
  }

 /**
   * Get idSede
   * @return idSede
  **/
  @XmlElement(name="idSede")
  public String getIdSede() {
    return idSede;
  }

  public void setIdSede(String idSede) {
    this.idSede = idSede;
  }

  public Sede idSede(String idSede) {
    this.idSede = idSede;
    return this;
  }

 /**
   * Get indirizzoSede
   * @return indirizzoSede
  **/
  @XmlElement(name="indirizzoSede")
  public Indirizzo getIndirizzoSede() {
    return indirizzoSede;
  }

  public void setIndirizzoSede(Indirizzo indirizzoSede) {
    this.indirizzoSede = indirizzoSede;
  }

  public Sede indirizzoSede(Indirizzo indirizzoSede) {
    this.indirizzoSede = indirizzoSede;
    return this;
  }

 /**
   * Get intervalliAperturaSede
   * @return intervalliAperturaSede
  **/
  @XmlElement(name="intervalliAperturaSede")
  public Intervallo getIntervalliAperturaSede() {
    return intervalliAperturaSede;
  }

  public void setIntervalliAperturaSede(Intervallo intervalliAperturaSede) {
    this.intervalliAperturaSede = intervalliAperturaSede;
  }

  public Sede intervalliAperturaSede(Intervallo intervalliAperturaSede) {
    this.intervalliAperturaSede = intervalliAperturaSede;
    return this;
  }

 /**
   * Get listaClassificazioniSede
   * @return listaClassificazioniSede
  **/
  @XmlElement(name="listaClassificazioniSede")
  public ListaClassificazioni getListaClassificazioniSede() {
    return listaClassificazioniSede;
  }

  public void setListaClassificazioniSede(ListaClassificazioni listaClassificazioniSede) {
    this.listaClassificazioniSede = listaClassificazioniSede;
  }

  public Sede listaClassificazioniSede(ListaClassificazioni listaClassificazioniSede) {
    this.listaClassificazioniSede = listaClassificazioniSede;
    return this;
  }

 /**
   * Get listaOrariSede
   * @return listaOrariSede
  **/
  @XmlElement(name="listaOrariSede")
  public ListaOrariApertura getListaOrariSede() {
    return listaOrariSede;
  }

  public void setListaOrariSede(ListaOrariApertura listaOrariSede) {
    this.listaOrariSede = listaOrariSede;
  }

  public Sede listaOrariSede(ListaOrariApertura listaOrariSede) {
    this.listaOrariSede = listaOrariSede;
    return this;
  }

 /**
   * Get listaRiferimentiSede
   * @return listaRiferimentiSede
  **/
  @XmlElement(name="listaRiferimentiSede")
  public ListaRiferimenti getListaRiferimentiSede() {
    return listaRiferimentiSede;
  }

  public void setListaRiferimentiSede(ListaRiferimenti listaRiferimentiSede) {
    this.listaRiferimentiSede = listaRiferimentiSede;
  }

  public Sede listaRiferimentiSede(ListaRiferimenti listaRiferimentiSede) {
    this.listaRiferimentiSede = listaRiferimentiSede;
    return this;
  }

 /**
   * Get principale
   * @return principale
  **/
  @XmlElement(name="principale")
  public Boolean isPrincipale() {
    return principale;
  }

  public void setPrincipale(Boolean principale) {
    this.principale = principale;
  }

  public Sede principale(Boolean principale) {
    this.principale = principale;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sede {\n");
    
    sb.append("    denominazioneSede: ").append(toIndentedString(denominazioneSede)).append("\n");
    sb.append("    idSede: ").append(toIndentedString(idSede)).append("\n");
    sb.append("    indirizzoSede: ").append(toIndentedString(indirizzoSede)).append("\n");
    sb.append("    intervalliAperturaSede: ").append(toIndentedString(intervalliAperturaSede)).append("\n");
    sb.append("    listaClassificazioniSede: ").append(toIndentedString(listaClassificazioniSede)).append("\n");
    sb.append("    listaOrariSede: ").append(toIndentedString(listaOrariSede)).append("\n");
    sb.append("    listaRiferimentiSede: ").append(toIndentedString(listaRiferimentiSede)).append("\n");
    sb.append("    principale: ").append(toIndentedString(principale)).append("\n");
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

