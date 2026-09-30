package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class EsecuzioneVoceRevocaType  {
  
  
  private ImportoType importo = null;
 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public ImportoType getImporto() {
    return importo;
  }

  public void setImporto(ImportoType importo) {
    this.importo = importo;
  }

  public EsecuzioneVoceRevocaType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsecuzioneVoceRevocaType {\n");
    
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
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

