package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class EsitoGetAvvisi  {
  
  
  private List<AvvisoDto> listaAvvisi = null;
 /**
   * Get listaAvvisi
   * @return listaAvvisi
  **/
  @XmlElement(name="listaAvvisi")
  public List<AvvisoDto> getListaAvvisi() {
    return listaAvvisi;
  }

  public void setListaAvvisi(List<AvvisoDto> listaAvvisi) {
    this.listaAvvisi = listaAvvisi;
  }

  public EsitoGetAvvisi listaAvvisi(List<AvvisoDto> listaAvvisi) {
    this.listaAvvisi = listaAvvisi;
    return this;
  }

  public EsitoGetAvvisi addListaAvvisiItem(AvvisoDto listaAvvisiItem) {
    this.listaAvvisi.add(listaAvvisiItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoGetAvvisi {\n");
    
    sb.append("    listaAvvisi: ").append(toIndentedString(listaAvvisi)).append("\n");
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

