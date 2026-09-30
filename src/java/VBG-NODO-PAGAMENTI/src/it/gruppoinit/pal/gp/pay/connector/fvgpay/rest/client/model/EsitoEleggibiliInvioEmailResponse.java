package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class EsitoEleggibiliInvioEmailResponse  {
  
  
  private EleggibiliInvioEmailDto eleggibiliInvioEmail = null;
 /**
   * Get eleggibiliInvioEmail
   * @return eleggibiliInvioEmail
  **/
  @XmlElement(name="eleggibiliInvioEmail")
  public EleggibiliInvioEmailDto getEleggibiliInvioEmail() {
    return eleggibiliInvioEmail;
  }

  public void setEleggibiliInvioEmail(EleggibiliInvioEmailDto eleggibiliInvioEmail) {
    this.eleggibiliInvioEmail = eleggibiliInvioEmail;
  }

  public EsitoEleggibiliInvioEmailResponse eleggibiliInvioEmail(EleggibiliInvioEmailDto eleggibiliInvioEmail) {
    this.eleggibiliInvioEmail = eleggibiliInvioEmail;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoEleggibiliInvioEmailResponse {\n");
    
    sb.append("    eleggibiliInvioEmail: ").append(toIndentedString(eleggibiliInvioEmail)).append("\n");
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

