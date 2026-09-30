package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class QName  {
  
  
  private String localPart = null;

  
  private String namespaceURI = null;

  
  private String prefix = null;
 /**
   * Get localPart
   * @return localPart
  **/
  @XmlElement(name="localPart")
  public String getLocalPart() {
    return localPart;
  }

  public void setLocalPart(String localPart) {
    this.localPart = localPart;
  }

  public QName localPart(String localPart) {
    this.localPart = localPart;
    return this;
  }

 /**
   * Get namespaceURI
   * @return namespaceURI
  **/
  @XmlElement(name="namespaceURI")
  public String getNamespaceURI() {
    return namespaceURI;
  }

  public void setNamespaceURI(String namespaceURI) {
    this.namespaceURI = namespaceURI;
  }

  public QName namespaceURI(String namespaceURI) {
    this.namespaceURI = namespaceURI;
    return this;
  }

 /**
   * Get prefix
   * @return prefix
  **/
  @XmlElement(name="prefix")
  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public QName prefix(String prefix) {
    this.prefix = prefix;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QName {\n");
    
    sb.append("    localPart: ").append(toIndentedString(localPart)).append("\n");
    sb.append("    namespaceURI: ").append(toIndentedString(namespaceURI)).append("\n");
    sb.append("    prefix: ").append(toIndentedString(prefix)).append("\n");
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

