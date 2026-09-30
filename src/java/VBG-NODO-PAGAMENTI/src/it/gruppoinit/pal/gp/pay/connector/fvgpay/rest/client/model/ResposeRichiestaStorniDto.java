package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ResposeRichiestaStorniDto  {
  
  
  private List<StorniDto> richiestaStorni = null;
 /**
   * Get richiestaStorni
   * @return richiestaStorni
  **/
  @XmlElement(name="richiestaStorni")
  public List<StorniDto> getRichiestaStorni() {
    return richiestaStorni;
  }

  public void setRichiestaStorni(List<StorniDto> richiestaStorni) {
    this.richiestaStorni = richiestaStorni;
  }

  public ResposeRichiestaStorniDto richiestaStorni(List<StorniDto> richiestaStorni) {
    this.richiestaStorni = richiestaStorni;
    return this;
  }

  public ResposeRichiestaStorniDto addRichiestaStorniItem(StorniDto richiestaStorniItem) {
    this.richiestaStorni.add(richiestaStorniItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResposeRichiestaStorniDto {\n");
    
    sb.append("    richiestaStorni: ").append(toIndentedString(richiestaStorni)).append("\n");
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

