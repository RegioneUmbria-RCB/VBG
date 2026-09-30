package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class MacroArea  {
  
  
  private String descrizione = null;

  
  private Long idMacroArea = null;

  
  private String nome = null;

  
  private String progressivoPerEnte = null;

  
  private String tipoEnteCreditore = null;
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

  public MacroArea descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

 /**
   * Get idMacroArea
   * @return idMacroArea
  **/
  @XmlElement(name="idMacroArea")
  public Long getIdMacroArea() {
    return idMacroArea;
  }

  public void setIdMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
  }

  public MacroArea idMacroArea(Long idMacroArea) {
    this.idMacroArea = idMacroArea;
    return this;
  }

 /**
   * Get nome
   * @return nome
  **/
  @XmlElement(name="nome")
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public MacroArea nome(String nome) {
    this.nome = nome;
    return this;
  }

 /**
   * Get progressivoPerEnte
   * @return progressivoPerEnte
  **/
  @XmlElement(name="progressivoPerEnte")
  public String getProgressivoPerEnte() {
    return progressivoPerEnte;
  }

  public void setProgressivoPerEnte(String progressivoPerEnte) {
    this.progressivoPerEnte = progressivoPerEnte;
  }

  public MacroArea progressivoPerEnte(String progressivoPerEnte) {
    this.progressivoPerEnte = progressivoPerEnte;
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

  public MacroArea tipoEnteCreditore(String tipoEnteCreditore) {
    this.tipoEnteCreditore = tipoEnteCreditore;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MacroArea {\n");
    
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    idMacroArea: ").append(toIndentedString(idMacroArea)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    progressivoPerEnte: ").append(toIndentedString(progressivoPerEnte)).append("\n");
    sb.append("    tipoEnteCreditore: ").append(toIndentedString(tipoEnteCreditore)).append("\n");
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

