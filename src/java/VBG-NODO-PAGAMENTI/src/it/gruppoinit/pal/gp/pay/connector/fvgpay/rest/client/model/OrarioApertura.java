package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class OrarioApertura  {
  
  
  private String chiusura = null;

  
  private Intervallo intervalliAperturaSede = null;

  
  private String tipoOrario = null;
 /**
   * Get chiusura
   * @return chiusura
  **/
  @XmlElement(name="chiusura")
  public String getChiusura() {
    return chiusura;
  }

  public void setChiusura(String chiusura) {
    this.chiusura = chiusura;
  }

  public OrarioApertura chiusura(String chiusura) {
    this.chiusura = chiusura;
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

  public OrarioApertura intervalliAperturaSede(Intervallo intervalliAperturaSede) {
    this.intervalliAperturaSede = intervalliAperturaSede;
    return this;
  }

 /**
   * Get tipoOrario
   * @return tipoOrario
  **/
  @XmlElement(name="tipoOrario")
  public String getTipoOrario() {
    return tipoOrario;
  }

  public void setTipoOrario(String tipoOrario) {
    this.tipoOrario = tipoOrario;
  }

  public OrarioApertura tipoOrario(String tipoOrario) {
    this.tipoOrario = tipoOrario;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrarioApertura {\n");
    
    sb.append("    chiusura: ").append(toIndentedString(chiusura)).append("\n");
    sb.append("    intervalliAperturaSede: ").append(toIndentedString(intervalliAperturaSede)).append("\n");
    sb.append("    tipoOrario: ").append(toIndentedString(tipoOrario)).append("\n");
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

