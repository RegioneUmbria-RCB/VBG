package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class EsitoGetEnteDto  {
  
  
  private DatiEnteDto datiEnte = null;
 /**
   * Get datiEnte
   * @return datiEnte
  **/
  @XmlElement(name="datiEnte")
  public DatiEnteDto getDatiEnte() {
    return datiEnte;
  }

  public void setDatiEnte(DatiEnteDto datiEnte) {
    this.datiEnte = datiEnte;
  }

  public EsitoGetEnteDto datiEnte(DatiEnteDto datiEnte) {
    this.datiEnte = datiEnte;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoGetEnteDto {\n");
    
    sb.append("    datiEnte: ").append(toIndentedString(datiEnte)).append("\n");
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

