package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
  * Il Pagatore e' il soggetto che ha un debito verso un Ente Creditore - il debito puo' essere estinto anche da un soggetto Versante diverso dal Pagatore 
 **/

public class SoggettoPagatoreType  {
  
  
 /**
   * Cognome della Persona Fisica o Denominazione della Persona Giuridica tenuta ad effettuare il Pagamento 
  **/
  private String cognome = null;

  
  private IdentificativoUnivocoPagatoreType identificativoUnivoco = null;

  
 /**
   * Nome della Persona Fisica tenuta ad effettuare il Pagamento: - ignorato, se presente, negli altri casi 
  **/
  private String nome = null;

  
  private RecapitoPostaleType recapitoPostale = null;

  
  private RecapitiTelematiciType recapitoTelematico = null;
 /**
   * Cognome della Persona Fisica o Denominazione della Persona Giuridica tenuta ad effettuare il Pagamento 
   * @return cognome
  **/
  @XmlElement(name="cognome")
  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public SoggettoPagatoreType cognome(String cognome) {
    this.cognome = cognome;
    return this;
  }

 /**
   * Get identificativoUnivoco
   * @return identificativoUnivoco
  **/
  @XmlElement(name="identificativo_univoco")
  public IdentificativoUnivocoPagatoreType getIdentificativoUnivoco() {
    return identificativoUnivoco;
  }

  public void setIdentificativoUnivoco(IdentificativoUnivocoPagatoreType identificativoUnivoco) {
    this.identificativoUnivoco = identificativoUnivoco;
  }

  public SoggettoPagatoreType identificativoUnivoco(IdentificativoUnivocoPagatoreType identificativoUnivoco) {
    this.identificativoUnivoco = identificativoUnivoco;
    return this;
  }

 /**
   * Nome della Persona Fisica tenuta ad effettuare il Pagamento: - ignorato, se presente, negli altri casi 
   * @return nome
  **/
  @XmlElement(name="nome")
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public SoggettoPagatoreType nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Get recapitoPostale
   * @return recapitoPostale
  **/
  @XmlElement(name="recapito_postale")
  public RecapitoPostaleType getRecapitoPostale() {
    return recapitoPostale;
  }

  public void setRecapitoPostale(RecapitoPostaleType recapitoPostale) {
    this.recapitoPostale = recapitoPostale;
  }

  public SoggettoPagatoreType recapitoPostale(RecapitoPostaleType recapitoPostale) {
    this.recapitoPostale = recapitoPostale;
    return this;
  }

 /**
   * Get recapitoTelematico
   * @return recapitoTelematico
  **/
  @XmlElement(name="recapito_telematico")
  public RecapitiTelematiciType getRecapitoTelematico() {
    return recapitoTelematico;
  }

  public void setRecapitoTelematico(RecapitiTelematiciType recapitoTelematico) {
    this.recapitoTelematico = recapitoTelematico;
  }

  public SoggettoPagatoreType recapitoTelematico(RecapitiTelematiciType recapitoTelematico) {
    this.recapitoTelematico = recapitoTelematico;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoggettoPagatoreType {\n");
    
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    identificativoUnivoco: ").append(toIndentedString(identificativoUnivoco)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    recapitoPostale: ").append(toIndentedString(recapitoPostale)).append("\n");
    sb.append("    recapitoTelematico: ").append(toIndentedString(recapitoTelematico)).append("\n");
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

