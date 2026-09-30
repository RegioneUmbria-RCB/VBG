package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class EsitoGetRTDto  {
  
  
  private String contentType = null;

  
  private RtDto rt = null;
 /**
   * Get contentType
   * @return contentType
  **/
  @XmlElement(name="contentType")
  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public EsitoGetRTDto contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

 /**
   * Get rt
   * @return rt
  **/
  @XmlElement(name="rt")
  public RtDto getRt() {
    return rt;
  }

  public void setRt(RtDto rt) {
    this.rt = rt;
  }

  public EsitoGetRTDto rt(RtDto rt) {
    this.rt = rt;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoGetRTDto {\n");
    
    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
    sb.append("    rt: ").append(toIndentedString(rt)).append("\n");
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

