package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class IdFlussoRendicontazioneType  {
  
  
 /**
   * identificativo del flusso del giornale
  **/
  private String idFlusso = null;
 /**
   * identificativo del flusso del giornale
   * @return idFlusso
  **/
  @XmlElement(name="id_flusso")
  public String getIdFlusso() {
    return idFlusso;
  }

  public void setIdFlusso(String idFlusso) {
    this.idFlusso = idFlusso;
  }

  public IdFlussoRendicontazioneType idFlusso(String idFlusso) {
    this.idFlusso = idFlusso;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdFlussoRendicontazioneType {\n");
    
    sb.append("    idFlusso: ").append(toIndentedString(idFlusso)).append("\n");
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

