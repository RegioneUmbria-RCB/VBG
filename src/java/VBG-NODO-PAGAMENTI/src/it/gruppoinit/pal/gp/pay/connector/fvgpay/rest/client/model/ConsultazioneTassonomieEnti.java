package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ConsultazioneTassonomieEnti  {
  
  
  private String codTassonomia = null;

  
  private Long idEnte = null;
 /**
   * Get codTassonomia
   * @return codTassonomia
  **/
  @XmlElement(name="codTassonomia")
  public String getCodTassonomia() {
    return codTassonomia;
  }

  public void setCodTassonomia(String codTassonomia) {
    this.codTassonomia = codTassonomia;
  }

  public ConsultazioneTassonomieEnti codTassonomia(String codTassonomia) {
    this.codTassonomia = codTassonomia;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="idEnte")
  public Long getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(Long idEnte) {
    this.idEnte = idEnte;
  }

  public ConsultazioneTassonomieEnti idEnte(Long idEnte) {
    this.idEnte = idEnte;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsultazioneTassonomieEnti {\n");
    
    sb.append("    codTassonomia: ").append(toIndentedString(codTassonomia)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
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

