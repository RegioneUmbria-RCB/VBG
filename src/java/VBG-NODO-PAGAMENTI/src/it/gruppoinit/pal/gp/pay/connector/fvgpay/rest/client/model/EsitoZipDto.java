package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class EsitoZipDto  {
  
  
  private String filePath = null;

  
  private Integer numPendenze = null;
 /**
   * Get filePath
   * @return filePath
  **/
  @XmlElement(name="filePath")
  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public EsitoZipDto filePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

 /**
   * Get numPendenze
   * @return numPendenze
  **/
  @XmlElement(name="numPendenze")
  public Integer getNumPendenze() {
    return numPendenze;
  }

  public void setNumPendenze(Integer numPendenze) {
    this.numPendenze = numPendenze;
  }

  public EsitoZipDto numPendenze(Integer numPendenze) {
    this.numPendenze = numPendenze;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoZipDto {\n");
    
    sb.append("    filePath: ").append(toIndentedString(filePath)).append("\n");
    sb.append("    numPendenze: ").append(toIndentedString(numPendenze)).append("\n");
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

