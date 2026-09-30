package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class Periodo  {
  
  
  private String alle = null;

  
  private String dalle = null;

  
  private String giorno = null;
 /**
   * Get alle
   * @return alle
  **/
  @XmlElement(name="alle")
  public String getAlle() {
    return alle;
  }

  public void setAlle(String alle) {
    this.alle = alle;
  }

  public Periodo alle(String alle) {
    this.alle = alle;
    return this;
  }

 /**
   * Get dalle
   * @return dalle
  **/
  @XmlElement(name="dalle")
  public String getDalle() {
    return dalle;
  }

  public void setDalle(String dalle) {
    this.dalle = dalle;
  }

  public Periodo dalle(String dalle) {
    this.dalle = dalle;
    return this;
  }

 /**
   * Get giorno
   * @return giorno
  **/
  @XmlElement(name="giorno")
  public String getGiorno() {
    return giorno;
  }

  public void setGiorno(String giorno) {
    this.giorno = giorno;
  }

  public Periodo giorno(String giorno) {
    this.giorno = giorno;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Periodo {\n");
    
    sb.append("    alle: ").append(toIndentedString(alle)).append("\n");
    sb.append("    dalle: ").append(toIndentedString(dalle)).append("\n");
    sb.append("    giorno: ").append(toIndentedString(giorno)).append("\n");
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

