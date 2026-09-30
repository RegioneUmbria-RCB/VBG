package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class EleggibiliInvioEmailDto  {
  
  
  private String codiceAvvisoPagamento = null;

  
  private Long cuspi = null;

  
  private Date dataScadenza = null;

  
  private String denominazione = null;

  
  private String email = null;

  
  private String importoTotale = null;

  
  private String intestazione = null;

  
  private byte[] pdf = null;
 /**
   * Get codiceAvvisoPagamento
   * @return codiceAvvisoPagamento
  **/
  @XmlElement(name="codiceAvvisoPagamento")
  public String getCodiceAvvisoPagamento() {
    return codiceAvvisoPagamento;
  }

  public void setCodiceAvvisoPagamento(String codiceAvvisoPagamento) {
    this.codiceAvvisoPagamento = codiceAvvisoPagamento;
  }

  public EleggibiliInvioEmailDto codiceAvvisoPagamento(String codiceAvvisoPagamento) {
    this.codiceAvvisoPagamento = codiceAvvisoPagamento;
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

  public EleggibiliInvioEmailDto cuspi(Long cuspi) {
    this.cuspi = cuspi;
    return this;
  }

 /**
   * Get dataScadenza
   * @return dataScadenza
  **/
  @XmlElement(name="dataScadenza")
  public Date getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(Date dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public EleggibiliInvioEmailDto dataScadenza(Date dataScadenza) {
    this.dataScadenza = dataScadenza;
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

  public EleggibiliInvioEmailDto denominazione(String denominazione) {
    this.denominazione = denominazione;
    return this;
  }

 /**
   * Get email
   * @return email
  **/
  @XmlElement(name="email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public EleggibiliInvioEmailDto email(String email) {
    this.email = email;
    return this;
  }

 /**
   * Get importoTotale
   * @return importoTotale
  **/
  @XmlElement(name="importoTotale")
  public String getImportoTotale() {
    return importoTotale;
  }

  public void setImportoTotale(String importoTotale) {
    this.importoTotale = importoTotale;
  }

  public EleggibiliInvioEmailDto importoTotale(String importoTotale) {
    this.importoTotale = importoTotale;
    return this;
  }

 /**
   * Get intestazione
   * @return intestazione
  **/
  @XmlElement(name="intestazione")
  public String getIntestazione() {
    return intestazione;
  }

  public void setIntestazione(String intestazione) {
    this.intestazione = intestazione;
  }

  public EleggibiliInvioEmailDto intestazione(String intestazione) {
    this.intestazione = intestazione;
    return this;
  }

 /**
   * Get pdf
   * @return pdf
  **/
  @XmlElement(name="pdf")
  public byte[] getPdf() {
    return pdf;
  }

  public void setPdf(byte[] pdf) {
    this.pdf = pdf;
  }

  public EleggibiliInvioEmailDto pdf(byte[] pdf) {
    this.pdf = pdf;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EleggibiliInvioEmailDto {\n");
    
    sb.append("    codiceAvvisoPagamento: ").append(toIndentedString(codiceAvvisoPagamento)).append("\n");
    sb.append("    cuspi: ").append(toIndentedString(cuspi)).append("\n");
    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    importoTotale: ").append(toIndentedString(importoTotale)).append("\n");
    sb.append("    intestazione: ").append(toIndentedString(intestazione)).append("\n");
    sb.append("    pdf: ").append(toIndentedString(pdf)).append("\n");
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

