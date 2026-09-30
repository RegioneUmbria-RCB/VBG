package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class EsitoVerificaBolloDto  {
  
  
  private byte[] hash = null;

  
  private Boolean valido = null;
 /**
   * Get hash
   * @return hash
  **/
  @XmlElement(name="hash")
  public byte[] getHash() {
    return hash;
  }

  public void setHash(byte[] hash) {
    this.hash = hash;
  }

  public EsitoVerificaBolloDto hash(byte[] hash) {
    this.hash = hash;
    return this;
  }

 /**
   * Get valido
   * @return valido
  **/
  @XmlElement(name="valido")
  public Boolean isValido() {
    return valido;
  }

  public void setValido(Boolean valido) {
    this.valido = valido;
  }

  public EsitoVerificaBolloDto valido(Boolean valido) {
    this.valido = valido;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoVerificaBolloDto {\n");
    
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    valido: ").append(toIndentedString(valido)).append("\n");
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

