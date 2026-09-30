package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ResposeRichiestaRevocaDto  {
  
  
  private List<RichiestaRevoca> richiestaRevoca = null;
 /**
   * Get richiestaRevoca
   * @return richiestaRevoca
  **/
  @XmlElement(name="richiestaRevoca")
  public List<RichiestaRevoca> getRichiestaRevoca() {
    return richiestaRevoca;
  }

  public void setRichiestaRevoca(List<RichiestaRevoca> richiestaRevoca) {
    this.richiestaRevoca = richiestaRevoca;
  }

  public ResposeRichiestaRevocaDto richiestaRevoca(List<RichiestaRevoca> richiestaRevoca) {
    this.richiestaRevoca = richiestaRevoca;
    return this;
  }

  public ResposeRichiestaRevocaDto addRichiestaRevocaItem(RichiestaRevoca richiestaRevocaItem) {
    this.richiestaRevoca.add(richiestaRevocaItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResposeRichiestaRevocaDto {\n");
    
    sb.append("    richiestaRevoca: ").append(toIndentedString(richiestaRevoca)).append("\n");
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

