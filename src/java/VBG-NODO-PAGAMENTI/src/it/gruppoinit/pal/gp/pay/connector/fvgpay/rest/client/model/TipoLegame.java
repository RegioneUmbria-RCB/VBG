package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class TipoLegame  {
  
  
  private String codiceTipoLegame = null;

  
  private String descrizioneTipoLegame = null;

  
  private String idTipoLegame = null;
 /**
   * Get codiceTipoLegame
   * @return codiceTipoLegame
  **/
  @XmlElement(name="codiceTipoLegame")
  public String getCodiceTipoLegame() {
    return codiceTipoLegame;
  }

  public void setCodiceTipoLegame(String codiceTipoLegame) {
    this.codiceTipoLegame = codiceTipoLegame;
  }

  public TipoLegame codiceTipoLegame(String codiceTipoLegame) {
    this.codiceTipoLegame = codiceTipoLegame;
    return this;
  }

 /**
   * Get descrizioneTipoLegame
   * @return descrizioneTipoLegame
  **/
  @XmlElement(name="descrizioneTipoLegame")
  public String getDescrizioneTipoLegame() {
    return descrizioneTipoLegame;
  }

  public void setDescrizioneTipoLegame(String descrizioneTipoLegame) {
    this.descrizioneTipoLegame = descrizioneTipoLegame;
  }

  public TipoLegame descrizioneTipoLegame(String descrizioneTipoLegame) {
    this.descrizioneTipoLegame = descrizioneTipoLegame;
    return this;
  }

 /**
   * Get idTipoLegame
   * @return idTipoLegame
  **/
  @XmlElement(name="idTipoLegame")
  public String getIdTipoLegame() {
    return idTipoLegame;
  }

  public void setIdTipoLegame(String idTipoLegame) {
    this.idTipoLegame = idTipoLegame;
  }

  public TipoLegame idTipoLegame(String idTipoLegame) {
    this.idTipoLegame = idTipoLegame;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TipoLegame {\n");
    
    sb.append("    codiceTipoLegame: ").append(toIndentedString(codiceTipoLegame)).append("\n");
    sb.append("    descrizioneTipoLegame: ").append(toIndentedString(descrizioneTipoLegame)).append("\n");
    sb.append("    idTipoLegame: ").append(toIndentedString(idTipoLegame)).append("\n");
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

