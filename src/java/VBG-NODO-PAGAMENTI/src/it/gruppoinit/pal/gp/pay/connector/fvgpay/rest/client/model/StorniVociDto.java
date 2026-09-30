package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class StorniVociDto  {
  
  
  private String causale = null;

  
  private String datiAggiuntiviRevoca = null;

  
  private Long idStorno = null;

  
  private String identificativoUnivoco = null;

  
  private Double importo = null;

  
  private Double importoRevocato = null;

  
  private Integer numeroVoce = null;
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

  public StorniVociDto causale(String causale) {
    this.causale = causale;
    return this;
  }

 /**
   * Get datiAggiuntiviRevoca
   * @return datiAggiuntiviRevoca
  **/
  @XmlElement(name="datiAggiuntiviRevoca")
  public String getDatiAggiuntiviRevoca() {
    return datiAggiuntiviRevoca;
  }

  public void setDatiAggiuntiviRevoca(String datiAggiuntiviRevoca) {
    this.datiAggiuntiviRevoca = datiAggiuntiviRevoca;
  }

  public StorniVociDto datiAggiuntiviRevoca(String datiAggiuntiviRevoca) {
    this.datiAggiuntiviRevoca = datiAggiuntiviRevoca;
    return this;
  }

 /**
   * Get idStorno
   * @return idStorno
  **/
  @XmlElement(name="idStorno")
  public Long getIdStorno() {
    return idStorno;
  }

  public void setIdStorno(Long idStorno) {
    this.idStorno = idStorno;
  }

  public StorniVociDto idStorno(Long idStorno) {
    this.idStorno = idStorno;
    return this;
  }

 /**
   * Get identificativoUnivoco
   * @return identificativoUnivoco
  **/
  @XmlElement(name="identificativoUnivoco")
  public String getIdentificativoUnivoco() {
    return identificativoUnivoco;
  }

  public void setIdentificativoUnivoco(String identificativoUnivoco) {
    this.identificativoUnivoco = identificativoUnivoco;
  }

  public StorniVociDto identificativoUnivoco(String identificativoUnivoco) {
    this.identificativoUnivoco = identificativoUnivoco;
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

  public StorniVociDto importo(Double importo) {
    this.importo = importo;
    return this;
  }

 /**
   * Get importoRevocato
   * @return importoRevocato
  **/
  @XmlElement(name="importoRevocato")
  public Double getImportoRevocato() {
    return importoRevocato;
  }

  public void setImportoRevocato(Double importoRevocato) {
    this.importoRevocato = importoRevocato;
  }

  public StorniVociDto importoRevocato(Double importoRevocato) {
    this.importoRevocato = importoRevocato;
    return this;
  }

 /**
   * Get numeroVoce
   * @return numeroVoce
  **/
  @XmlElement(name="numeroVoce")
  public Integer getNumeroVoce() {
    return numeroVoce;
  }

  public void setNumeroVoce(Integer numeroVoce) {
    this.numeroVoce = numeroVoce;
  }

  public StorniVociDto numeroVoce(Integer numeroVoce) {
    this.numeroVoce = numeroVoce;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StorniVociDto {\n");
    
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    datiAggiuntiviRevoca: ").append(toIndentedString(datiAggiuntiviRevoca)).append("\n");
    sb.append("    idStorno: ").append(toIndentedString(idStorno)).append("\n");
    sb.append("    identificativoUnivoco: ").append(toIndentedString(identificativoUnivoco)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    importoRevocato: ").append(toIndentedString(importoRevocato)).append("\n");
    sb.append("    numeroVoce: ").append(toIndentedString(numeroVoce)).append("\n");
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

