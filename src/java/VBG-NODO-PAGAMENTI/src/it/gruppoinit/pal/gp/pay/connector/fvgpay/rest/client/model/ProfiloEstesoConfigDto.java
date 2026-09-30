package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ProfiloEstesoConfigDto  {
  
  
  private EnteDto ente = null;

  
  private List<ProfiloCustomConfigDto> ltProfilo = null;
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

  public ProfiloEstesoConfigDto ente(EnteDto ente) {
    this.ente = ente;
    return this;
  }

 /**
   * Get ltProfilo
   * @return ltProfilo
  **/
  @XmlElement(name="ltProfilo")
  public List<ProfiloCustomConfigDto> getLtProfilo() {
    return ltProfilo;
  }

  public void setLtProfilo(List<ProfiloCustomConfigDto> ltProfilo) {
    this.ltProfilo = ltProfilo;
  }

  public ProfiloEstesoConfigDto ltProfilo(List<ProfiloCustomConfigDto> ltProfilo) {
    this.ltProfilo = ltProfilo;
    return this;
  }

  public ProfiloEstesoConfigDto addLtProfiloItem(ProfiloCustomConfigDto ltProfiloItem) {
    this.ltProfilo.add(ltProfiloItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProfiloEstesoConfigDto {\n");
    
    sb.append("    ente: ").append(toIndentedString(ente)).append("\n");
    sb.append("    ltProfilo: ").append(toIndentedString(ltProfilo)).append("\n");
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

