package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class DatiSpecificiRiscossioneRendicontazioneAscotType  {
  
  
  private String tassonomia = null;
 /**
   * Get tassonomia
   * @return tassonomia
  **/
  @XmlElement(name="tassonomia")
  public String getTassonomia() {
    return tassonomia;
  }

  public void setTassonomia(String tassonomia) {
    this.tassonomia = tassonomia;
  }

  public DatiSpecificiRiscossioneRendicontazioneAscotType tassonomia(String tassonomia) {
    this.tassonomia = tassonomia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiSpecificiRiscossioneRendicontazioneAscotType {\n");
    
    sb.append("    tassonomia: ").append(toIndentedString(tassonomia)).append("\n");
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

