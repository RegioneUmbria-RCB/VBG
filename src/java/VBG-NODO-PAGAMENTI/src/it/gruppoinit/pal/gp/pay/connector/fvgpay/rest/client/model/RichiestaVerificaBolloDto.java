package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class RichiestaVerificaBolloDto  {
  
  
  private String marcaDaBollo = null;
 /**
   * Get marcaDaBollo
   * @return marcaDaBollo
  **/
  @XmlElement(name="marcaDaBollo")
  public String getMarcaDaBollo() {
    return marcaDaBollo;
  }

  public void setMarcaDaBollo(String marcaDaBollo) {
    this.marcaDaBollo = marcaDaBollo;
  }

  public RichiestaVerificaBolloDto marcaDaBollo(String marcaDaBollo) {
    this.marcaDaBollo = marcaDaBollo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaVerificaBolloDto {\n");
    
    sb.append("    marcaDaBollo: ").append(toIndentedString(marcaDaBollo)).append("\n");
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

