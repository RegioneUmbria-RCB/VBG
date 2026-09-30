package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Luogo  {
  
  
  private CittaEstera cittaEstera = null;

  
  private Comune comuneItaliano = null;
 /**
   * Get cittaEstera
   * @return cittaEstera
  **/
  @XmlElement(name="cittaEstera")
  public CittaEstera getCittaEstera() {
    return cittaEstera;
  }

  public void setCittaEstera(CittaEstera cittaEstera) {
    this.cittaEstera = cittaEstera;
  }

  public Luogo cittaEstera(CittaEstera cittaEstera) {
    this.cittaEstera = cittaEstera;
    return this;
  }

 /**
   * Get comuneItaliano
   * @return comuneItaliano
  **/
  @XmlElement(name="comuneItaliano")
  public Comune getComuneItaliano() {
    return comuneItaliano;
  }

  public void setComuneItaliano(Comune comuneItaliano) {
    this.comuneItaliano = comuneItaliano;
  }

  public Luogo comuneItaliano(Comune comuneItaliano) {
    this.comuneItaliano = comuneItaliano;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Luogo {\n");
    
    sb.append("    cittaEstera: ").append(toIndentedString(cittaEstera)).append("\n");
    sb.append("    comuneItaliano: ").append(toIndentedString(comuneItaliano)).append("\n");
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

