package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ListaRiferimenti  {
  
  
  private List<Riferimento> riferimento = null;
 /**
   * Get riferimento
   * @return riferimento
  **/
  @XmlElement(name="riferimento")
  public List<Riferimento> getRiferimento() {
    return riferimento;
  }

  public void setRiferimento(List<Riferimento> riferimento) {
    this.riferimento = riferimento;
  }

  public ListaRiferimenti riferimento(List<Riferimento> riferimento) {
    this.riferimento = riferimento;
    return this;
  }

  public ListaRiferimenti addRiferimentoItem(Riferimento riferimentoItem) {
    this.riferimento.add(riferimentoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaRiferimenti {\n");
    
    sb.append("    riferimento: ").append(toIndentedString(riferimento)).append("\n");
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

