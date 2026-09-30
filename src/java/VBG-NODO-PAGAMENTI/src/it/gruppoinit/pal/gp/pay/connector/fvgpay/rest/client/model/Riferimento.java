package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class Riferimento  {
  
  
  private Indirizzo indirizzo = null;

  
  private String tipoRiferimento = null;

  
  private String valoreRiferimento = null;
 /**
   * Get indirizzo
   * @return indirizzo
  **/
  @XmlElement(name="indirizzo")
  public Indirizzo getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(Indirizzo indirizzo) {
    this.indirizzo = indirizzo;
  }

  public Riferimento indirizzo(Indirizzo indirizzo) {
    this.indirizzo = indirizzo;
    return this;
  }

 /**
   * Get tipoRiferimento
   * @return tipoRiferimento
  **/
  @XmlElement(name="tipoRiferimento")
  public String getTipoRiferimento() {
    return tipoRiferimento;
  }

  public void setTipoRiferimento(String tipoRiferimento) {
    this.tipoRiferimento = tipoRiferimento;
  }

  public Riferimento tipoRiferimento(String tipoRiferimento) {
    this.tipoRiferimento = tipoRiferimento;
    return this;
  }

 /**
   * Get valoreRiferimento
   * @return valoreRiferimento
  **/
  @XmlElement(name="valoreRiferimento")
  public String getValoreRiferimento() {
    return valoreRiferimento;
  }

  public void setValoreRiferimento(String valoreRiferimento) {
    this.valoreRiferimento = valoreRiferimento;
  }

  public Riferimento valoreRiferimento(String valoreRiferimento) {
    this.valoreRiferimento = valoreRiferimento;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Riferimento {\n");
    
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    tipoRiferimento: ").append(toIndentedString(tipoRiferimento)).append("\n");
    sb.append("    valoreRiferimento: ").append(toIndentedString(valoreRiferimento)).append("\n");
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

