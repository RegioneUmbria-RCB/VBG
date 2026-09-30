package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class RispostaGetStruttura  {
  
  
  private List<Errore> errore = null;

  
  private Struttura struttura = null;
 /**
   * Get errore
   * @return errore
  **/
  @XmlElement(name="errore")
  public List<Errore> getErrore() {
    return errore;
  }

  public void setErrore(List<Errore> errore) {
    this.errore = errore;
  }

  public RispostaGetStruttura errore(List<Errore> errore) {
    this.errore = errore;
    return this;
  }

  public RispostaGetStruttura addErroreItem(Errore erroreItem) {
    this.errore.add(erroreItem);
    return this;
  }

 /**
   * Get struttura
   * @return struttura
  **/
  @XmlElement(name="struttura")
  public Struttura getStruttura() {
    return struttura;
  }

  public void setStruttura(Struttura struttura) {
    this.struttura = struttura;
  }

  public RispostaGetStruttura struttura(Struttura struttura) {
    this.struttura = struttura;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RispostaGetStruttura {\n");
    
    sb.append("    errore: ").append(toIndentedString(errore)).append("\n");
    sb.append("    struttura: ").append(toIndentedString(struttura)).append("\n");
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

