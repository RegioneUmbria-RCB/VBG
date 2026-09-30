package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Legame  {
  
  
  private String codiceLegame = null;

  
  private String descrizioneLegame = null;

  
  private String idLegame = null;

  
  private TipoLegame tipoLegame = null;
 /**
   * Get codiceLegame
   * @return codiceLegame
  **/
  @XmlElement(name="codiceLegame")
  public String getCodiceLegame() {
    return codiceLegame;
  }

  public void setCodiceLegame(String codiceLegame) {
    this.codiceLegame = codiceLegame;
  }

  public Legame codiceLegame(String codiceLegame) {
    this.codiceLegame = codiceLegame;
    return this;
  }

 /**
   * Get descrizioneLegame
   * @return descrizioneLegame
  **/
  @XmlElement(name="descrizioneLegame")
  public String getDescrizioneLegame() {
    return descrizioneLegame;
  }

  public void setDescrizioneLegame(String descrizioneLegame) {
    this.descrizioneLegame = descrizioneLegame;
  }

  public Legame descrizioneLegame(String descrizioneLegame) {
    this.descrizioneLegame = descrizioneLegame;
    return this;
  }

 /**
   * Get idLegame
   * @return idLegame
  **/
  @XmlElement(name="idLegame")
  public String getIdLegame() {
    return idLegame;
  }

  public void setIdLegame(String idLegame) {
    this.idLegame = idLegame;
  }

  public Legame idLegame(String idLegame) {
    this.idLegame = idLegame;
    return this;
  }

 /**
   * Get tipoLegame
   * @return tipoLegame
  **/
  @XmlElement(name="tipoLegame")
  public TipoLegame getTipoLegame() {
    return tipoLegame;
  }

  public void setTipoLegame(TipoLegame tipoLegame) {
    this.tipoLegame = tipoLegame;
  }

  public Legame tipoLegame(TipoLegame tipoLegame) {
    this.tipoLegame = tipoLegame;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Legame {\n");
    
    sb.append("    codiceLegame: ").append(toIndentedString(codiceLegame)).append("\n");
    sb.append("    descrizioneLegame: ").append(toIndentedString(descrizioneLegame)).append("\n");
    sb.append("    idLegame: ").append(toIndentedString(idLegame)).append("\n");
    sb.append("    tipoLegame: ").append(toIndentedString(tipoLegame)).append("\n");
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

