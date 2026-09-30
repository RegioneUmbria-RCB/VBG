package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class EnteDto  {
  
  
  private Integer attivo = null;

  
  private String cf = null;

  
  private String codEnteMd = null;

  
  private String codice = null;

  
  private String codiceStrutturaMasterdata = null;

  
  private String denominazione = null;

  
  private Long idEnte = null;

  
  private String idTipoEnteCreditore = null;

  
  private List<TipoEnteCreditore> listTipoEnteCreditore = null;

  
  private byte[] logo = null;

  
  private String logoBase64 = null;

  
  private String nomeLogo = null;

  
  private Integer visibilita = null;
 /**
   * Get attivo
   * @return attivo
  **/
  @XmlElement(name="attivo")
  public Integer getAttivo() {
    return attivo;
  }

  public void setAttivo(Integer attivo) {
    this.attivo = attivo;
  }

  public EnteDto attivo(Integer attivo) {
    this.attivo = attivo;
    return this;
  }

 /**
   * Get cf
   * @return cf
  **/
  @XmlElement(name="cf")
  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public EnteDto cf(String cf) {
    this.cf = cf;
    return this;
  }

 /**
   * Get codEnteMd
   * @return codEnteMd
  **/
  @XmlElement(name="codEnteMd")
  public String getCodEnteMd() {
    return codEnteMd;
  }

  public void setCodEnteMd(String codEnteMd) {
    this.codEnteMd = codEnteMd;
  }

  public EnteDto codEnteMd(String codEnteMd) {
    this.codEnteMd = codEnteMd;
    return this;
  }

 /**
   * Get codice
   * @return codice
  **/
  @XmlElement(name="codice")
  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
    this.codice = codice;
  }

  public EnteDto codice(String codice) {
    this.codice = codice;
    return this;
  }

 /**
   * Get codiceStrutturaMasterdata
   * @return codiceStrutturaMasterdata
  **/
  @XmlElement(name="codiceStrutturaMasterdata")
  public String getCodiceStrutturaMasterdata() {
    return codiceStrutturaMasterdata;
  }

  public void setCodiceStrutturaMasterdata(String codiceStrutturaMasterdata) {
    this.codiceStrutturaMasterdata = codiceStrutturaMasterdata;
  }

  public EnteDto codiceStrutturaMasterdata(String codiceStrutturaMasterdata) {
    this.codiceStrutturaMasterdata = codiceStrutturaMasterdata;
    return this;
  }

 /**
   * Get denominazione
   * @return denominazione
  **/
  @XmlElement(name="denominazione")
  public String getDenominazione() {
    return denominazione;
  }

  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  public EnteDto denominazione(String denominazione) {
    this.denominazione = denominazione;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="idEnte")
  public Long getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(Long idEnte) {
    this.idEnte = idEnte;
  }

  public EnteDto idEnte(Long idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get idTipoEnteCreditore
   * @return idTipoEnteCreditore
  **/
  @XmlElement(name="idTipoEnteCreditore")
  public String getIdTipoEnteCreditore() {
    return idTipoEnteCreditore;
  }

  public void setIdTipoEnteCreditore(String idTipoEnteCreditore) {
    this.idTipoEnteCreditore = idTipoEnteCreditore;
  }

  public EnteDto idTipoEnteCreditore(String idTipoEnteCreditore) {
    this.idTipoEnteCreditore = idTipoEnteCreditore;
    return this;
  }

 /**
   * Get listTipoEnteCreditore
   * @return listTipoEnteCreditore
  **/
  @XmlElement(name="listTipoEnteCreditore")
  public List<TipoEnteCreditore> getListTipoEnteCreditore() {
    return listTipoEnteCreditore;
  }

  public void setListTipoEnteCreditore(List<TipoEnteCreditore> listTipoEnteCreditore) {
    this.listTipoEnteCreditore = listTipoEnteCreditore;
  }

  public EnteDto listTipoEnteCreditore(List<TipoEnteCreditore> listTipoEnteCreditore) {
    this.listTipoEnteCreditore = listTipoEnteCreditore;
    return this;
  }

  public EnteDto addListTipoEnteCreditoreItem(TipoEnteCreditore listTipoEnteCreditoreItem) {
    this.listTipoEnteCreditore.add(listTipoEnteCreditoreItem);
    return this;
  }

 /**
   * Get logo
   * @return logo
  **/
  @XmlElement(name="logo")
  public byte[] getLogo() {
    return logo;
  }

  public void setLogo(byte[] logo) {
    this.logo = logo;
  }

  public EnteDto logo(byte[] logo) {
    this.logo = logo;
    return this;
  }

 /**
   * Get logoBase64
   * @return logoBase64
  **/
  @XmlElement(name="logoBase64")
  public String getLogoBase64() {
    return logoBase64;
  }

  public void setLogoBase64(String logoBase64) {
    this.logoBase64 = logoBase64;
  }

  public EnteDto logoBase64(String logoBase64) {
    this.logoBase64 = logoBase64;
    return this;
  }

 /**
   * Get nomeLogo
   * @return nomeLogo
  **/
  @XmlElement(name="nomeLogo")
  public String getNomeLogo() {
    return nomeLogo;
  }

  public void setNomeLogo(String nomeLogo) {
    this.nomeLogo = nomeLogo;
  }

  public EnteDto nomeLogo(String nomeLogo) {
    this.nomeLogo = nomeLogo;
    return this;
  }

 /**
   * Get visibilita
   * @return visibilita
  **/
  @XmlElement(name="visibilita")
  public Integer getVisibilita() {
    return visibilita;
  }

  public void setVisibilita(Integer visibilita) {
    this.visibilita = visibilita;
  }

  public EnteDto visibilita(Integer visibilita) {
    this.visibilita = visibilita;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EnteDto {\n");
    
    sb.append("    attivo: ").append(toIndentedString(attivo)).append("\n");
    sb.append("    cf: ").append(toIndentedString(cf)).append("\n");
    sb.append("    codEnteMd: ").append(toIndentedString(codEnteMd)).append("\n");
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    codiceStrutturaMasterdata: ").append(toIndentedString(codiceStrutturaMasterdata)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    idTipoEnteCreditore: ").append(toIndentedString(idTipoEnteCreditore)).append("\n");
    sb.append("    listTipoEnteCreditore: ").append(toIndentedString(listTipoEnteCreditore)).append("\n");
    sb.append("    logo: ").append(toIndentedString(logo)).append("\n");
    sb.append("    logoBase64: ").append(toIndentedString(logoBase64)).append("\n");
    sb.append("    nomeLogo: ").append(toIndentedString(nomeLogo)).append("\n");
    sb.append("    visibilita: ").append(toIndentedString(visibilita)).append("\n");
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

