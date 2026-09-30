package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class EsitoValidazioneType  {
  
  
  private Boolean esito = null;

  
 /**
   * identificativo della posizione debitoria non valida
  **/
  private String idDebito = null;

  
 /**
   * lista dei motivi di rifiuto
  **/
  private List<String> motiviRifiuto = null;
 /**
   * Get esito
   * @return esito
  **/
  @XmlElement(name="esito")
  public Boolean isEsito() {
    return esito;
  }

  public void setEsito(Boolean esito) {
    this.esito = esito;
  }

  public EsitoValidazioneType esito(Boolean esito) {
    this.esito = esito;
    return this;
  }

 /**
   * identificativo della posizione debitoria non valida
   * @return idDebito
  **/
  @XmlElement(name="id_debito")
  public String getIdDebito() {
    return idDebito;
  }

  public void setIdDebito(String idDebito) {
    this.idDebito = idDebito;
  }

  public EsitoValidazioneType idDebito(String idDebito) {
    this.idDebito = idDebito;
    return this;
  }

 /**
   * lista dei motivi di rifiuto
   * @return motiviRifiuto
  **/
  @XmlElement(name="motivi_rifiuto")
  public List<String> getMotiviRifiuto() {
    return motiviRifiuto;
  }

  public void setMotiviRifiuto(List<String> motiviRifiuto) {
    this.motiviRifiuto = motiviRifiuto;
  }

  public EsitoValidazioneType motiviRifiuto(List<String> motiviRifiuto) {
    this.motiviRifiuto = motiviRifiuto;
    return this;
  }

  public EsitoValidazioneType addMotiviRifiutoItem(String motiviRifiutoItem) {
    this.motiviRifiuto.add(motiviRifiutoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoValidazioneType {\n");
    
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
    sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
    sb.append("    motiviRifiuto: ").append(toIndentedString(motiviRifiuto)).append("\n");
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

