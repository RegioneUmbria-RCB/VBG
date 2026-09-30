package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class DatiEnteDto  {
  
  
  private ConfigurazioneDominioDto configDominio = null;

  
  private ConfigurazionePmpay configurazionePmpay = null;

  
  private DominioDto dominio = null;

  
  private EnteDto ente = null;
 /**
   * Get configDominio
   * @return configDominio
  **/
  @XmlElement(name="configDominio")
  public ConfigurazioneDominioDto getConfigDominio() {
    return configDominio;
  }

  public void setConfigDominio(ConfigurazioneDominioDto configDominio) {
    this.configDominio = configDominio;
  }

  public DatiEnteDto configDominio(ConfigurazioneDominioDto configDominio) {
    this.configDominio = configDominio;
    return this;
  }

 /**
   * Get configurazionePmpay
   * @return configurazionePmpay
  **/
  @XmlElement(name="configurazionePmpay")
  public ConfigurazionePmpay getConfigurazionePmpay() {
    return configurazionePmpay;
  }

  public void setConfigurazionePmpay(ConfigurazionePmpay configurazionePmpay) {
    this.configurazionePmpay = configurazionePmpay;
  }

  public DatiEnteDto configurazionePmpay(ConfigurazionePmpay configurazionePmpay) {
    this.configurazionePmpay = configurazionePmpay;
    return this;
  }

 /**
   * Get dominio
   * @return dominio
  **/
  @XmlElement(name="dominio")
  public DominioDto getDominio() {
    return dominio;
  }

  public void setDominio(DominioDto dominio) {
    this.dominio = dominio;
  }

  public DatiEnteDto dominio(DominioDto dominio) {
    this.dominio = dominio;
    return this;
  }

 /**
   * Get ente
   * @return ente
  **/
  @XmlElement(name="ente")
  public EnteDto getEnte() {
    return ente;
  }

  public void setEnte(EnteDto ente) {
    this.ente = ente;
  }

  public DatiEnteDto ente(EnteDto ente) {
    this.ente = ente;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiEnteDto {\n");
    
    sb.append("    configDominio: ").append(toIndentedString(configDominio)).append("\n");
    sb.append("    configurazionePmpay: ").append(toIndentedString(configurazionePmpay)).append("\n");
    sb.append("    dominio: ").append(toIndentedString(dominio)).append("\n");
    sb.append("    ente: ").append(toIndentedString(ente)).append("\n");
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

