package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class StrutturaBase  {
  
  
  private ClasseStruttura classeStruttura = null;

  
  private String codiceStruttura = null;

  
  private String denominazioneStruttura = null;

  
  private String descrizioneEstesa = null;

  
  private Ente enteStruttura = null;

  
  private String idStruttura = null;

  
  private String livelloStruttura = null;

  
  private String partitaIvaStruttura = null;

  
  private RapportoStruttura responsabileStruttura = null;

  
  private Boolean strutturaAttiva = null;
 /**
   * Get classeStruttura
   * @return classeStruttura
  **/
  @XmlElement(name="classeStruttura")
  public ClasseStruttura getClasseStruttura() {
    return classeStruttura;
  }

  public void setClasseStruttura(ClasseStruttura classeStruttura) {
    this.classeStruttura = classeStruttura;
  }

  public StrutturaBase classeStruttura(ClasseStruttura classeStruttura) {
    this.classeStruttura = classeStruttura;
    return this;
  }

 /**
   * Get codiceStruttura
   * @return codiceStruttura
  **/
  @XmlElement(name="codiceStruttura")
  public String getCodiceStruttura() {
    return codiceStruttura;
  }

  public void setCodiceStruttura(String codiceStruttura) {
    this.codiceStruttura = codiceStruttura;
  }

  public StrutturaBase codiceStruttura(String codiceStruttura) {
    this.codiceStruttura = codiceStruttura;
    return this;
  }

 /**
   * Get denominazioneStruttura
   * @return denominazioneStruttura
  **/
  @XmlElement(name="denominazioneStruttura")
  public String getDenominazioneStruttura() {
    return denominazioneStruttura;
  }

  public void setDenominazioneStruttura(String denominazioneStruttura) {
    this.denominazioneStruttura = denominazioneStruttura;
  }

  public StrutturaBase denominazioneStruttura(String denominazioneStruttura) {
    this.denominazioneStruttura = denominazioneStruttura;
    return this;
  }

 /**
   * Get descrizioneEstesa
   * @return descrizioneEstesa
  **/
  @XmlElement(name="descrizioneEstesa")
  public String getDescrizioneEstesa() {
    return descrizioneEstesa;
  }

  public void setDescrizioneEstesa(String descrizioneEstesa) {
    this.descrizioneEstesa = descrizioneEstesa;
  }

  public StrutturaBase descrizioneEstesa(String descrizioneEstesa) {
    this.descrizioneEstesa = descrizioneEstesa;
    return this;
  }

 /**
   * Get enteStruttura
   * @return enteStruttura
  **/
  @XmlElement(name="enteStruttura")
  public Ente getEnteStruttura() {
    return enteStruttura;
  }

  public void setEnteStruttura(Ente enteStruttura) {
    this.enteStruttura = enteStruttura;
  }

  public StrutturaBase enteStruttura(Ente enteStruttura) {
    this.enteStruttura = enteStruttura;
    return this;
  }

 /**
   * Get idStruttura
   * @return idStruttura
  **/
  @XmlElement(name="idStruttura")
  public String getIdStruttura() {
    return idStruttura;
  }

  public void setIdStruttura(String idStruttura) {
    this.idStruttura = idStruttura;
  }

  public StrutturaBase idStruttura(String idStruttura) {
    this.idStruttura = idStruttura;
    return this;
  }

 /**
   * Get livelloStruttura
   * @return livelloStruttura
  **/
  @XmlElement(name="livelloStruttura")
  public String getLivelloStruttura() {
    return livelloStruttura;
  }

  public void setLivelloStruttura(String livelloStruttura) {
    this.livelloStruttura = livelloStruttura;
  }

  public StrutturaBase livelloStruttura(String livelloStruttura) {
    this.livelloStruttura = livelloStruttura;
    return this;
  }

 /**
   * Get partitaIvaStruttura
   * @return partitaIvaStruttura
  **/
  @XmlElement(name="partitaIvaStruttura")
  public String getPartitaIvaStruttura() {
    return partitaIvaStruttura;
  }

  public void setPartitaIvaStruttura(String partitaIvaStruttura) {
    this.partitaIvaStruttura = partitaIvaStruttura;
  }

  public StrutturaBase partitaIvaStruttura(String partitaIvaStruttura) {
    this.partitaIvaStruttura = partitaIvaStruttura;
    return this;
  }

 /**
   * Get responsabileStruttura
   * @return responsabileStruttura
  **/
  @XmlElement(name="responsabileStruttura")
  public RapportoStruttura getResponsabileStruttura() {
    return responsabileStruttura;
  }

  public void setResponsabileStruttura(RapportoStruttura responsabileStruttura) {
    this.responsabileStruttura = responsabileStruttura;
  }

  public StrutturaBase responsabileStruttura(RapportoStruttura responsabileStruttura) {
    this.responsabileStruttura = responsabileStruttura;
    return this;
  }

 /**
   * Get strutturaAttiva
   * @return strutturaAttiva
  **/
  @XmlElement(name="strutturaAttiva")
  public Boolean isStrutturaAttiva() {
    return strutturaAttiva;
  }

  public void setStrutturaAttiva(Boolean strutturaAttiva) {
    this.strutturaAttiva = strutturaAttiva;
  }

  public StrutturaBase strutturaAttiva(Boolean strutturaAttiva) {
    this.strutturaAttiva = strutturaAttiva;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StrutturaBase {\n");
    
    sb.append("    classeStruttura: ").append(toIndentedString(classeStruttura)).append("\n");
    sb.append("    codiceStruttura: ").append(toIndentedString(codiceStruttura)).append("\n");
    sb.append("    denominazioneStruttura: ").append(toIndentedString(denominazioneStruttura)).append("\n");
    sb.append("    descrizioneEstesa: ").append(toIndentedString(descrizioneEstesa)).append("\n");
    sb.append("    enteStruttura: ").append(toIndentedString(enteStruttura)).append("\n");
    sb.append("    idStruttura: ").append(toIndentedString(idStruttura)).append("\n");
    sb.append("    livelloStruttura: ").append(toIndentedString(livelloStruttura)).append("\n");
    sb.append("    partitaIvaStruttura: ").append(toIndentedString(partitaIvaStruttura)).append("\n");
    sb.append("    responsabileStruttura: ").append(toIndentedString(responsabileStruttura)).append("\n");
    sb.append("    strutturaAttiva: ").append(toIndentedString(strutturaAttiva)).append("\n");
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

