package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ProvinceDto  {
  
  
  private List<ComuniDto> comuni = null;

  
  private String descrizione = null;

  
  private String sigla = null;
 /**
   * Get comuni
   * @return comuni
  **/
  @XmlElement(name="comuni")
  public List<ComuniDto> getComuni() {
    return comuni;
  }

  public void setComuni(List<ComuniDto> comuni) {
    this.comuni = comuni;
  }

  public ProvinceDto comuni(List<ComuniDto> comuni) {
    this.comuni = comuni;
    return this;
  }

  public ProvinceDto addComuniItem(ComuniDto comuniItem) {
    this.comuni.add(comuniItem);
    return this;
  }

 /**
   * Get descrizione
   * @return descrizione
  **/
  @XmlElement(name="descrizione")
  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public ProvinceDto descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * Get sigla
   * @return sigla
  **/
  @XmlElement(name="sigla")
  public String getSigla() {
    return sigla;
  }

  public void setSigla(String sigla) {
    this.sigla = sigla;
  }

  public ProvinceDto sigla(String sigla) {
    this.sigla = sigla;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProvinceDto {\n");
    
    sb.append("    comuni: ").append(toIndentedString(comuni)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    sigla: ").append(toIndentedString(sigla)).append("\n");
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

