package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * La richiesta e' riferita ad uno specifico documento, specificato nel campo hash_documento, contenente l'impronta (Hash SHA-256) del documento o della segnatura di protocollo dell'istanza 
 **/

public class RichiestaBolloType  {
  
  
  private byte[] hashDocumento = null;

  
 /**
   * Sigla automobilistica della provincia di residenza del Debitore per i residenti all'estero indicare la sigla della provincia dell'Ente Creditore 
  **/
  private String provinciaResidenza = null;

  
 /**
   * Tipo di bollo, 01: Imposta di bollo
  **/
  private String tipoBollo = null;
 /**
   * Get hashDocumento
   * @return hashDocumento
  **/
  @XmlElement(name="hash_documento")
  public byte[] getHashDocumento() {
    return hashDocumento;
  }

  public void setHashDocumento(byte[] hashDocumento) {
    this.hashDocumento = hashDocumento;
  }

  public RichiestaBolloType hashDocumento(byte[] hashDocumento) {
    this.hashDocumento = hashDocumento;
    return this;
  }

 /**
   * Sigla automobilistica della provincia di residenza del Debitore per i residenti all&#39;estero indicare la sigla della provincia dell&#39;Ente Creditore 
   * @return provinciaResidenza
  **/
  @XmlElement(name="provincia_residenza")
  public String getProvinciaResidenza() {
    return provinciaResidenza;
  }

  public void setProvinciaResidenza(String provinciaResidenza) {
    this.provinciaResidenza = provinciaResidenza;
  }

  public RichiestaBolloType provinciaResidenza(String provinciaResidenza) {
    this.provinciaResidenza = provinciaResidenza;
    return this;
  }

 /**
   * Tipo di bollo, 01: Imposta di bollo
   * @return tipoBollo
  **/
  @XmlElement(name="tipo_bollo")
  public String getTipoBollo() {
    return tipoBollo;
  }

  public void setTipoBollo(String tipoBollo) {
    this.tipoBollo = tipoBollo;
  }

  public RichiestaBolloType tipoBollo(String tipoBollo) {
    this.tipoBollo = tipoBollo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaBolloType {\n");
    
    sb.append("    hashDocumento: ").append(toIndentedString(hashDocumento)).append("\n");
    sb.append("    provinciaResidenza: ").append(toIndentedString(provinciaResidenza)).append("\n");
    sb.append("    tipoBollo: ").append(toIndentedString(tipoBollo)).append("\n");
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

