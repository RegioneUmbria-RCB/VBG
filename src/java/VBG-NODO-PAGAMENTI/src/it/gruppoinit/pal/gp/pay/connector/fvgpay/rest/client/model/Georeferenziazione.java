package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class Georeferenziazione  {
  
  
  private String coordinataX = null;

  
  private String coordinataY = null;

  
  private String coordinataZ = null;

  
  private String sistemaDiRiferimento = null;
 /**
   * Get coordinataX
   * @return coordinataX
  **/
  @XmlElement(name="coordinataX")
  public String getCoordinataX() {
    return coordinataX;
  }

  public void setCoordinataX(String coordinataX) {
    this.coordinataX = coordinataX;
  }

  public Georeferenziazione coordinataX(String coordinataX) {
    this.coordinataX = coordinataX;
    return this;
  }

 /**
   * Get coordinataY
   * @return coordinataY
  **/
  @XmlElement(name="coordinataY")
  public String getCoordinataY() {
    return coordinataY;
  }

  public void setCoordinataY(String coordinataY) {
    this.coordinataY = coordinataY;
  }

  public Georeferenziazione coordinataY(String coordinataY) {
    this.coordinataY = coordinataY;
    return this;
  }

 /**
   * Get coordinataZ
   * @return coordinataZ
  **/
  @XmlElement(name="coordinataZ")
  public String getCoordinataZ() {
    return coordinataZ;
  }

  public void setCoordinataZ(String coordinataZ) {
    this.coordinataZ = coordinataZ;
  }

  public Georeferenziazione coordinataZ(String coordinataZ) {
    this.coordinataZ = coordinataZ;
    return this;
  }

 /**
   * Get sistemaDiRiferimento
   * @return sistemaDiRiferimento
  **/
  @XmlElement(name="sistemaDiRiferimento")
  public String getSistemaDiRiferimento() {
    return sistemaDiRiferimento;
  }

  public void setSistemaDiRiferimento(String sistemaDiRiferimento) {
    this.sistemaDiRiferimento = sistemaDiRiferimento;
  }

  public Georeferenziazione sistemaDiRiferimento(String sistemaDiRiferimento) {
    this.sistemaDiRiferimento = sistemaDiRiferimento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Georeferenziazione {\n");
    
    sb.append("    coordinataX: ").append(toIndentedString(coordinataX)).append("\n");
    sb.append("    coordinataY: ").append(toIndentedString(coordinataY)).append("\n");
    sb.append("    coordinataZ: ").append(toIndentedString(coordinataZ)).append("\n");
    sb.append("    sistemaDiRiferimento: ").append(toIndentedString(sistemaDiRiferimento)).append("\n");
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

