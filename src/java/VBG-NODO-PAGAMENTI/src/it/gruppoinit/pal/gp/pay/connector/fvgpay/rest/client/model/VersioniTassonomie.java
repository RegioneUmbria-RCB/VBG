package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class VersioniTassonomie  {
  
  
  private Date dataImportazione = null;

  
  private String descrizione = null;

  
  private Long idVersioniTassonomie = null;

  
  private String utente = null;

  
  private Long versioneCorrente = null;

  
  private String versioneTassonomia = null;
 /**
   * Get dataImportazione
   * @return dataImportazione
  **/
  @XmlElement(name="dataImportazione")
  public Date getDataImportazione() {
    return dataImportazione;
  }

  public void setDataImportazione(Date dataImportazione) {
    this.dataImportazione = dataImportazione;
  }

  public VersioniTassonomie dataImportazione(Date dataImportazione) {
    this.dataImportazione = dataImportazione;
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

  public VersioniTassonomie descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * Get idVersioniTassonomie
   * @return idVersioniTassonomie
  **/
  @XmlElement(name="idVersioniTassonomie")
  public Long getIdVersioniTassonomie() {
    return idVersioniTassonomie;
  }

  public void setIdVersioniTassonomie(Long idVersioniTassonomie) {
    this.idVersioniTassonomie = idVersioniTassonomie;
  }

  public VersioniTassonomie idVersioniTassonomie(Long idVersioniTassonomie) {
    this.idVersioniTassonomie = idVersioniTassonomie;
    return this;
  }

 /**
   * Get utente
   * @return utente
  **/
  @XmlElement(name="utente")
  public String getUtente() {
    return utente;
  }

  public void setUtente(String utente) {
    this.utente = utente;
  }

  public VersioniTassonomie utente(String utente) {
    this.utente = utente;
    return this;
  }

 /**
   * Get versioneCorrente
   * @return versioneCorrente
  **/
  @XmlElement(name="versioneCorrente")
  public Long getVersioneCorrente() {
    return versioneCorrente;
  }

  public void setVersioneCorrente(Long versioneCorrente) {
    this.versioneCorrente = versioneCorrente;
  }

  public VersioniTassonomie versioneCorrente(Long versioneCorrente) {
    this.versioneCorrente = versioneCorrente;
    return this;
  }

 /**
   * Get versioneTassonomia
   * @return versioneTassonomia
  **/
  @XmlElement(name="versioneTassonomia")
  public String getVersioneTassonomia() {
    return versioneTassonomia;
  }

  public void setVersioneTassonomia(String versioneTassonomia) {
    this.versioneTassonomia = versioneTassonomia;
  }

  public VersioniTassonomie versioneTassonomia(String versioneTassonomia) {
    this.versioneTassonomia = versioneTassonomia;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VersioniTassonomie {\n");
    
    sb.append("    dataImportazione: ").append(toIndentedString(dataImportazione)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    idVersioniTassonomie: ").append(toIndentedString(idVersioniTassonomie)).append("\n");
    sb.append("    utente: ").append(toIndentedString(utente)).append("\n");
    sb.append("    versioneCorrente: ").append(toIndentedString(versioneCorrente)).append("\n");
    sb.append("    versioneTassonomia: ").append(toIndentedString(versioneTassonomia)).append("\n");
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

