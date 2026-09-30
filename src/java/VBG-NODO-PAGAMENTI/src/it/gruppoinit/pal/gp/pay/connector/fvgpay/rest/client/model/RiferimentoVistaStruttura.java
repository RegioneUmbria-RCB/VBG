package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class RiferimentoVistaStruttura  {
  
  
  private List<NodoRiferimentoVistaStruttura> nodo = null;

  
  private VistaBase vista = null;
 /**
   * Get nodo
   * @return nodo
  **/
  @XmlElement(name="nodo")
  public List<NodoRiferimentoVistaStruttura> getNodo() {
    return nodo;
  }

  public void setNodo(List<NodoRiferimentoVistaStruttura> nodo) {
    this.nodo = nodo;
  }

  public RiferimentoVistaStruttura nodo(List<NodoRiferimentoVistaStruttura> nodo) {
    this.nodo = nodo;
    return this;
  }

  public RiferimentoVistaStruttura addNodoItem(NodoRiferimentoVistaStruttura nodoItem) {
    this.nodo.add(nodoItem);
    return this;
  }

 /**
   * Get vista
   * @return vista
  **/
  @XmlElement(name="vista")
  public VistaBase getVista() {
    return vista;
  }

  public void setVista(VistaBase vista) {
    this.vista = vista;
  }

  public RiferimentoVistaStruttura vista(VistaBase vista) {
    this.vista = vista;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RiferimentoVistaStruttura {\n");
    
    sb.append("    nodo: ").append(toIndentedString(nodo)).append("\n");
    sb.append("    vista: ").append(toIndentedString(vista)).append("\n");
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

