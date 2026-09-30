package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class PersonaFisicaBase  {
  
  
  private String codiceFiscale = null;

  
  private String cognome = null;

  
  private String id = null;

  
  private String nome = null;
 /**
   * Get codiceFiscale
   * @return codiceFiscale
  **/
  @XmlElement(name="codiceFiscale")
  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public PersonaFisicaBase codiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
    return this;
  }

 /**
   * Get cognome
   * @return cognome
  **/
  @XmlElement(name="cognome")
  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public PersonaFisicaBase cognome(String cognome) {
    this.cognome = cognome;
    return this;
  }

 /**
   * Get id
   * @return id
  **/
  @XmlElement(name="id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PersonaFisicaBase id(String id) {
    this.id = id;
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

  public PersonaFisicaBase nome(String nome) {
    this.nome = nome;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonaFisicaBase {\n");
    
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
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

