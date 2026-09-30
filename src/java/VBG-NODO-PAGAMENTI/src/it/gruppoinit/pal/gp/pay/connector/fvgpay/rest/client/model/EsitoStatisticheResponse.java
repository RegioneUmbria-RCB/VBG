package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class EsitoStatisticheResponse  {
  
  
  private StatisticheDto statistiche = null;
 /**
   * Get statistiche
   * @return statistiche
  **/
  @XmlElement(name="statistiche")
  public StatisticheDto getStatistiche() {
    return statistiche;
  }

  public void setStatistiche(StatisticheDto statistiche) {
    this.statistiche = statistiche;
  }

  public EsitoStatisticheResponse statistiche(StatisticheDto statistiche) {
    this.statistiche = statistiche;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoStatisticheResponse {\n");
    
    sb.append("    statistiche: ").append(toIndentedString(statistiche)).append("\n");
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

