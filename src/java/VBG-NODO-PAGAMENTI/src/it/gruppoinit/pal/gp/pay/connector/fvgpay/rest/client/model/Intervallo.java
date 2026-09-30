package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Intervallo  {
  
  
  private List<Periodo> periodo = null;
 /**
   * Get periodo
   * @return periodo
  **/
  @XmlElement(name="periodo")
  public List<Periodo> getPeriodo() {
    return periodo;
  }

  public void setPeriodo(List<Periodo> periodo) {
    this.periodo = periodo;
  }

  public Intervallo periodo(List<Periodo> periodo) {
    this.periodo = periodo;
    return this;
  }

  public Intervallo addPeriodoItem(Periodo periodoItem) {
    this.periodo.add(periodoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Intervallo {\n");
    
    sb.append("    periodo: ").append(toIndentedString(periodo)).append("\n");
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

