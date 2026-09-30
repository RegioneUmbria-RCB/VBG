package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class TassonomieImportRequestDto  {
  
  
  private String descrizioneImport = null;

  
  private List<TassonomieImportDto> tassonomie = null;

  
  private String user = null;
 /**
   * Get descrizioneImport
   * @return descrizioneImport
  **/
  @XmlElement(name="descrizioneImport")
  public String getDescrizioneImport() {
    return descrizioneImport;
  }

  public void setDescrizioneImport(String descrizioneImport) {
    this.descrizioneImport = descrizioneImport;
  }

  public TassonomieImportRequestDto descrizioneImport(String descrizioneImport) {
    this.descrizioneImport = descrizioneImport;
    return this;
  }

 /**
   * Get tassonomie
   * @return tassonomie
  **/
  @XmlElement(name="tassonomie")
  public List<TassonomieImportDto> getTassonomie() {
    return tassonomie;
  }

  public void setTassonomie(List<TassonomieImportDto> tassonomie) {
    this.tassonomie = tassonomie;
  }

  public TassonomieImportRequestDto tassonomie(List<TassonomieImportDto> tassonomie) {
    this.tassonomie = tassonomie;
    return this;
  }

  public TassonomieImportRequestDto addTassonomieItem(TassonomieImportDto tassonomieItem) {
    this.tassonomie.add(tassonomieItem);
    return this;
  }

 /**
   * Get user
   * @return user
  **/
  @XmlElement(name="user")
  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public TassonomieImportRequestDto user(String user) {
    this.user = user;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TassonomieImportRequestDto {\n");
    
    sb.append("    descrizioneImport: ").append(toIndentedString(descrizioneImport)).append("\n");
    sb.append("    tassonomie: ").append(toIndentedString(tassonomie)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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

