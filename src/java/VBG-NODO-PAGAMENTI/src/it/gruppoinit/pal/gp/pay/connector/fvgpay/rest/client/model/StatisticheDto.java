package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class StatisticheDto  {
  
  
  private Long totaleAvvisature = null;

  
  private Long totaleAvvisatureElaborate = null;

  
  private Long totaleAvvisaturePagate = null;
 /**
   * Get totaleAvvisature
   * @return totaleAvvisature
  **/
  @XmlElement(name="totaleAvvisature")
  public Long getTotaleAvvisature() {
    return totaleAvvisature;
  }

  public void setTotaleAvvisature(Long totaleAvvisature) {
    this.totaleAvvisature = totaleAvvisature;
  }

  public StatisticheDto totaleAvvisature(Long totaleAvvisature) {
    this.totaleAvvisature = totaleAvvisature;
    return this;
  }

 /**
   * Get totaleAvvisatureElaborate
   * @return totaleAvvisatureElaborate
  **/
  @XmlElement(name="totaleAvvisatureElaborate")
  public Long getTotaleAvvisatureElaborate() {
    return totaleAvvisatureElaborate;
  }

  public void setTotaleAvvisatureElaborate(Long totaleAvvisatureElaborate) {
    this.totaleAvvisatureElaborate = totaleAvvisatureElaborate;
  }

  public StatisticheDto totaleAvvisatureElaborate(Long totaleAvvisatureElaborate) {
    this.totaleAvvisatureElaborate = totaleAvvisatureElaborate;
    return this;
  }

 /**
   * Get totaleAvvisaturePagate
   * @return totaleAvvisaturePagate
  **/
  @XmlElement(name="totaleAvvisaturePagate")
  public Long getTotaleAvvisaturePagate() {
    return totaleAvvisaturePagate;
  }

  public void setTotaleAvvisaturePagate(Long totaleAvvisaturePagate) {
    this.totaleAvvisaturePagate = totaleAvvisaturePagate;
  }

  public StatisticheDto totaleAvvisaturePagate(Long totaleAvvisaturePagate) {
    this.totaleAvvisaturePagate = totaleAvvisaturePagate;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatisticheDto {\n");
    
    sb.append("    totaleAvvisature: ").append(toIndentedString(totaleAvvisature)).append("\n");
    sb.append("    totaleAvvisatureElaborate: ").append(toIndentedString(totaleAvvisatureElaborate)).append("\n");
    sb.append("    totaleAvvisaturePagate: ").append(toIndentedString(totaleAvvisaturePagate)).append("\n");
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

