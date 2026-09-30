package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class TblVociPagamento  {
  
  
  private String causale = null;

  
  private String codiceContabilita = null;

  
  private Double commissioneImporto = null;

  
  private String commissioneValuta = null;

  
  private Long cuspi = null;

  
  private String hash = null;

  
  private Double importo = null;

  
  private String provincia = null;

  
  private String tassonomia = null;

  
  private String tipoBollo = null;

  
  private String tipoContabilita = null;
 /**
   * Get causale
   * @return causale
  **/
  @XmlElement(name="causale")
  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public TblVociPagamento causale(String causale) {
    this.causale = causale;
    return this;
  }

 /**
   * Get codiceContabilita
   * @return codiceContabilita
  **/
  @XmlElement(name="codiceContabilita")
  public String getCodiceContabilita() {
    return codiceContabilita;
  }

  public void setCodiceContabilita(String codiceContabilita) {
    this.codiceContabilita = codiceContabilita;
  }

  public TblVociPagamento codiceContabilita(String codiceContabilita) {
    this.codiceContabilita = codiceContabilita;
    return this;
  }

 /**
   * Get commissioneImporto
   * @return commissioneImporto
  **/
  @XmlElement(name="commissioneImporto")
  public Double getCommissioneImporto() {
    return commissioneImporto;
  }

  public void setCommissioneImporto(Double commissioneImporto) {
    this.commissioneImporto = commissioneImporto;
  }

  public TblVociPagamento commissioneImporto(Double commissioneImporto) {
    this.commissioneImporto = commissioneImporto;
    return this;
  }

 /**
   * Get commissioneValuta
   * @return commissioneValuta
  **/
  @XmlElement(name="commissioneValuta")
  public String getCommissioneValuta() {
    return commissioneValuta;
  }

  public void setCommissioneValuta(String commissioneValuta) {
    this.commissioneValuta = commissioneValuta;
  }

  public TblVociPagamento commissioneValuta(String commissioneValuta) {
    this.commissioneValuta = commissioneValuta;
    return this;
  }

 /**
   * Get cuspi
   * @return cuspi
  **/
  @XmlElement(name="cuspi")
  public Long getCuspi() {
    return cuspi;
  }

  public void setCuspi(Long cuspi) {
    this.cuspi = cuspi;
  }

  public TblVociPagamento cuspi(Long cuspi) {
    this.cuspi = cuspi;
    return this;
  }

 /**
   * Get hash
   * @return hash
  **/
  @XmlElement(name="hash")
  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public TblVociPagamento hash(String hash) {
    this.hash = hash;
    return this;
  }

 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double importo) {
    this.importo = importo;
  }

  public TblVociPagamento importo(Double importo) {
    this.importo = importo;
    return this;
  }

 /**
   * Get provincia
   * @return provincia
  **/
  @XmlElement(name="provincia")
  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public TblVociPagamento provincia(String provincia) {
    this.provincia = provincia;
    return this;
  }

 /**
   * Get tassonomia
   * @return tassonomia
  **/
  @XmlElement(name="tassonomia")
  public String getTassonomia() {
    return tassonomia;
  }

  public void setTassonomia(String tassonomia) {
    this.tassonomia = tassonomia;
  }

  public TblVociPagamento tassonomia(String tassonomia) {
    this.tassonomia = tassonomia;
    return this;
  }

 /**
   * Get tipoBollo
   * @return tipoBollo
  **/
  @XmlElement(name="tipoBollo")
  public String getTipoBollo() {
    return tipoBollo;
  }

  public void setTipoBollo(String tipoBollo) {
    this.tipoBollo = tipoBollo;
  }

  public TblVociPagamento tipoBollo(String tipoBollo) {
    this.tipoBollo = tipoBollo;
    return this;
  }

 /**
   * Get tipoContabilita
   * @return tipoContabilita
  **/
  @XmlElement(name="tipoContabilita")
  public String getTipoContabilita() {
    return tipoContabilita;
  }

  public void setTipoContabilita(String tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
  }

  public TblVociPagamento tipoContabilita(String tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TblVociPagamento {\n");
    
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    codiceContabilita: ").append(toIndentedString(codiceContabilita)).append("\n");
    sb.append("    commissioneImporto: ").append(toIndentedString(commissioneImporto)).append("\n");
    sb.append("    commissioneValuta: ").append(toIndentedString(commissioneValuta)).append("\n");
    sb.append("    cuspi: ").append(toIndentedString(cuspi)).append("\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    tassonomia: ").append(toIndentedString(tassonomia)).append("\n");
    sb.append("    tipoBollo: ").append(toIndentedString(tipoBollo)).append("\n");
    sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
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

