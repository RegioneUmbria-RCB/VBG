package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Il Prestatore di Servizi di Pagamento (PSP)
 **/

public class IstitutoAttestanteType  {
  
  
 /**
   * Denominazione del PSP
  **/
  private String denominazioneAttestante = null;

  
  private IdentificativoUnivocoAttestanteType identificativoUnivocoAttestante = null;
 /**
   * Denominazione del PSP
   * @return denominazioneAttestante
  **/
  @XmlElement(name="denominazione_attestante")
  public String getDenominazioneAttestante() {
    return denominazioneAttestante;
  }

  public void setDenominazioneAttestante(String denominazioneAttestante) {
    this.denominazioneAttestante = denominazioneAttestante;
  }

  public IstitutoAttestanteType denominazioneAttestante(String denominazioneAttestante) {
    this.denominazioneAttestante = denominazioneAttestante;
    return this;
  }

 /**
   * Get identificativoUnivocoAttestante
   * @return identificativoUnivocoAttestante
  **/
  @XmlElement(name="identificativo_univoco_attestante")
  public IdentificativoUnivocoAttestanteType getIdentificativoUnivocoAttestante() {
    return identificativoUnivocoAttestante;
  }

  public void setIdentificativoUnivocoAttestante(IdentificativoUnivocoAttestanteType identificativoUnivocoAttestante) {
    this.identificativoUnivocoAttestante = identificativoUnivocoAttestante;
  }

  public IstitutoAttestanteType identificativoUnivocoAttestante(IdentificativoUnivocoAttestanteType identificativoUnivocoAttestante) {
    this.identificativoUnivocoAttestante = identificativoUnivocoAttestante;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IstitutoAttestanteType {\n");
    
    sb.append("    denominazioneAttestante: ").append(toIndentedString(denominazioneAttestante)).append("\n");
    sb.append("    identificativoUnivocoAttestante: ").append(toIndentedString(identificativoUnivocoAttestante)).append("\n");
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

