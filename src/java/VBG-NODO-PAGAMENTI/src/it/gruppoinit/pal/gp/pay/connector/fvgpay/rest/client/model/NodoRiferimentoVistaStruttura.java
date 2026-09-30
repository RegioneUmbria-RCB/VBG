package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class NodoRiferimentoVistaStruttura  {
  
  
  private String idNodoVista = null;

  
  private String percorso = null;
 /**
   * Get idNodoVista
   * @return idNodoVista
  **/
  @XmlElement(name="idNodoVista")
  public String getIdNodoVista() {
    return idNodoVista;
  }

  public void setIdNodoVista(String idNodoVista) {
    this.idNodoVista = idNodoVista;
  }

  public NodoRiferimentoVistaStruttura idNodoVista(String idNodoVista) {
    this.idNodoVista = idNodoVista;
    return this;
  }

 /**
   * Get percorso
   * @return percorso
  **/
  @XmlElement(name="percorso")
  public String getPercorso() {
    return percorso;
  }

  public void setPercorso(String percorso) {
    this.percorso = percorso;
  }

  public NodoRiferimentoVistaStruttura percorso(String percorso) {
    this.percorso = percorso;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodoRiferimentoVistaStruttura {\n");
    
    sb.append("    idNodoVista: ").append(toIndentedString(idNodoVista)).append("\n");
    sb.append("    percorso: ").append(toIndentedString(percorso)).append("\n");
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

