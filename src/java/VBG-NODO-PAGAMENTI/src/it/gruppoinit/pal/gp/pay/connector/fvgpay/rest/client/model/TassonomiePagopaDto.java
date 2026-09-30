package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class TassonomiePagopaDto  {
  
  
  private String codice = null;

  
  private String codicePagopa = null;

  
  private String descrizioneTipoServizio = null;

  
  private Long idTassonomiaPagopa = null;

  
  private String macroAreaDescrizione = null;

  
  private String macroAreaNome = null;

  
  private String tipoEnteCreditore = null;

  
  private String tipoServizio = null;
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

  public TassonomiePagopaDto codice(String codice) {
    this.codice = codice;
    return this;
  }

 /**
   * Get codicePagopa
   * @return codicePagopa
  **/
  @XmlElement(name="codicePagopa")
  public String getCodicePagopa() {
    return codicePagopa;
  }

  public void setCodicePagopa(String codicePagopa) {
    this.codicePagopa = codicePagopa;
  }

  public TassonomiePagopaDto codicePagopa(String codicePagopa) {
    this.codicePagopa = codicePagopa;
    return this;
  }

 /**
   * Get descrizioneTipoServizio
   * @return descrizioneTipoServizio
  **/
  @XmlElement(name="descrizioneTipoServizio")
  public String getDescrizioneTipoServizio() {
    return descrizioneTipoServizio;
  }

  public void setDescrizioneTipoServizio(String descrizioneTipoServizio) {
    this.descrizioneTipoServizio = descrizioneTipoServizio;
  }

  public TassonomiePagopaDto descrizioneTipoServizio(String descrizioneTipoServizio) {
    this.descrizioneTipoServizio = descrizioneTipoServizio;
    return this;
  }

 /**
   * Get idTassonomiaPagopa
   * @return idTassonomiaPagopa
  **/
  @XmlElement(name="idTassonomiaPagopa")
  public Long getIdTassonomiaPagopa() {
    return idTassonomiaPagopa;
  }

  public void setIdTassonomiaPagopa(Long idTassonomiaPagopa) {
    this.idTassonomiaPagopa = idTassonomiaPagopa;
  }

  public TassonomiePagopaDto idTassonomiaPagopa(Long idTassonomiaPagopa) {
    this.idTassonomiaPagopa = idTassonomiaPagopa;
    return this;
  }

 /**
   * Get macroAreaDescrizione
   * @return macroAreaDescrizione
  **/
  @XmlElement(name="macroAreaDescrizione")
  public String getMacroAreaDescrizione() {
    return macroAreaDescrizione;
  }

  public void setMacroAreaDescrizione(String macroAreaDescrizione) {
    this.macroAreaDescrizione = macroAreaDescrizione;
  }

  public TassonomiePagopaDto macroAreaDescrizione(String macroAreaDescrizione) {
    this.macroAreaDescrizione = macroAreaDescrizione;
    return this;
  }

 /**
   * Get macroAreaNome
   * @return macroAreaNome
  **/
  @XmlElement(name="macroAreaNome")
  public String getMacroAreaNome() {
    return macroAreaNome;
  }

  public void setMacroAreaNome(String macroAreaNome) {
    this.macroAreaNome = macroAreaNome;
  }

  public TassonomiePagopaDto macroAreaNome(String macroAreaNome) {
    this.macroAreaNome = macroAreaNome;
    return this;
  }

 /**
   * Get tipoEnteCreditore
   * @return tipoEnteCreditore
  **/
  @XmlElement(name="tipoEnteCreditore")
  public String getTipoEnteCreditore() {
    return tipoEnteCreditore;
  }

  public void setTipoEnteCreditore(String tipoEnteCreditore) {
    this.tipoEnteCreditore = tipoEnteCreditore;
  }

  public TassonomiePagopaDto tipoEnteCreditore(String tipoEnteCreditore) {
    this.tipoEnteCreditore = tipoEnteCreditore;
    return this;
  }

 /**
   * Get tipoServizio
   * @return tipoServizio
  **/
  @XmlElement(name="tipoServizio")
  public String getTipoServizio() {
    return tipoServizio;
  }

  public void setTipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
  }

  public TassonomiePagopaDto tipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TassonomiePagopaDto {\n");
    
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    codicePagopa: ").append(toIndentedString(codicePagopa)).append("\n");
    sb.append("    descrizioneTipoServizio: ").append(toIndentedString(descrizioneTipoServizio)).append("\n");
    sb.append("    idTassonomiaPagopa: ").append(toIndentedString(idTassonomiaPagopa)).append("\n");
    sb.append("    macroAreaDescrizione: ").append(toIndentedString(macroAreaDescrizione)).append("\n");
    sb.append("    macroAreaNome: ").append(toIndentedString(macroAreaNome)).append("\n");
    sb.append("    tipoEnteCreditore: ").append(toIndentedString(tipoEnteCreditore)).append("\n");
    sb.append("    tipoServizio: ").append(toIndentedString(tipoServizio)).append("\n");
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

