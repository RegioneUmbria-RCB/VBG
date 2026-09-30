package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Importo pagoPA
 **/

public class ImportoType  {
  
  
 /**
   * Gli importi in pagoPA: - usano il \".\" come separatore - hanno due cifre decimali 
  **/
  private Double importo = null;

  
 /**
   * codice valuta di 3 lettere - come definito da ISO-4217 
  **/
  private String valuta = null;
 /**
   * Gli importi in pagoPA: - usano il \&quot;.\&quot; come separatore - hanno due cifre decimali 
   * @return importo
  **/
  @XmlElement(name="importo")
  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double importo) {
    this.importo = importo;
  }

  public ImportoType importo(Double importo) {
    this.importo = importo;
    return this;
  }

 /**
   * codice valuta di 3 lettere - come definito da ISO-4217 
   * @return valuta
  **/
  @XmlElement(name="valuta")
  public String getValuta() {
    return valuta;
  }

  public void setValuta(String valuta) {
    this.valuta = valuta;
  }

  public ImportoType valuta(String valuta) {
    this.valuta = valuta;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImportoType {\n");
    
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    valuta: ").append(toIndentedString(valuta)).append("\n");
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

