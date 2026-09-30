package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ListaRiferimentiVistaStruttura  {
  
  
  private List<RiferimentoVistaStruttura> riferimentoVista = null;
 /**
   * Get riferimentoVista
   * @return riferimentoVista
  **/
  @XmlElement(name="riferimentoVista")
  public List<RiferimentoVistaStruttura> getRiferimentoVista() {
    return riferimentoVista;
  }

  public void setRiferimentoVista(List<RiferimentoVistaStruttura> riferimentoVista) {
    this.riferimentoVista = riferimentoVista;
  }

  public ListaRiferimentiVistaStruttura riferimentoVista(List<RiferimentoVistaStruttura> riferimentoVista) {
    this.riferimentoVista = riferimentoVista;
    return this;
  }

  public ListaRiferimentiVistaStruttura addRiferimentoVistaItem(RiferimentoVistaStruttura riferimentoVistaItem) {
    this.riferimentoVista.add(riferimentoVistaItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaRiferimentiVistaStruttura {\n");
    
    sb.append("    riferimentoVista: ").append(toIndentedString(riferimentoVista)).append("\n");
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

