package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class EsitoGetRicevutaTelematica  {
  
  
  private byte[] stampa = null;
 /**
   * Get stampa
   * @return stampa
  **/
  @XmlElement(name="stampa")
  public byte[] getStampa() {
    return stampa;
  }

  public void setStampa(byte[] stampa) {
    this.stampa = stampa;
  }

  public EsitoGetRicevutaTelematica stampa(byte[] stampa) {
    this.stampa = stampa;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoGetRicevutaTelematica {\n");
    
    sb.append("    stampa: ").append(toIndentedString(stampa)).append("\n");
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

