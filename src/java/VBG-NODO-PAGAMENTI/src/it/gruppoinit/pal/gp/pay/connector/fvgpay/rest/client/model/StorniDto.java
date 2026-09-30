package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class StorniDto  {
  
  
  private String causale = null;

  
  private Date dataRevoca = null;

  
  private Integer generato = null;

  
  private Long id = null;

  
  private String idDebito = null;

  
  private String idEnte = null;

  
  private String idServizio = null;

  
  private Double importoTotale = null;

  
  private String iuv = null;

  
  private String tipoRevoca = null;

  
  private List<StorniVociDto> voci = null;

  
  private byte[] xml = null;
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

  public StorniDto causale(String causale) {
    this.causale = causale;
    return this;
  }

 /**
   * Get dataRevoca
   * @return dataRevoca
  **/
  @XmlElement(name="dataRevoca")
  public Date getDataRevoca() {
    return dataRevoca;
  }

  public void setDataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
  }

  public StorniDto dataRevoca(Date dataRevoca) {
    this.dataRevoca = dataRevoca;
    return this;
  }

 /**
   * Get generato
   * @return generato
  **/
  @XmlElement(name="generato")
  public Integer getGenerato() {
    return generato;
  }

  public void setGenerato(Integer generato) {
    this.generato = generato;
  }

  public StorniDto generato(Integer generato) {
    this.generato = generato;
    return this;
  }

 /**
   * Get id
   * @return id
  **/
  @XmlElement(name="id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StorniDto id(Long id) {
    this.id = id;
    return this;
  }

 /**
   * Get idDebito
   * @return idDebito
  **/
  @XmlElement(name="idDebito")
  public String getIdDebito() {
    return idDebito;
  }

  public void setIdDebito(String idDebito) {
    this.idDebito = idDebito;
  }

  public StorniDto idDebito(String idDebito) {
    this.idDebito = idDebito;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="idEnte")
  public String getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(String idEnte) {
    this.idEnte = idEnte;
  }

  public StorniDto idEnte(String idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get idServizio
   * @return idServizio
  **/
  @XmlElement(name="idServizio")
  public String getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(String idServizio) {
    this.idServizio = idServizio;
  }

  public StorniDto idServizio(String idServizio) {
    this.idServizio = idServizio;
    return this;
  }

 /**
   * Get importoTotale
   * @return importoTotale
  **/
  @XmlElement(name="importoTotale")
  public Double getImportoTotale() {
    return importoTotale;
  }

  public void setImportoTotale(Double importoTotale) {
    this.importoTotale = importoTotale;
  }

  public StorniDto importoTotale(Double importoTotale) {
    this.importoTotale = importoTotale;
    return this;
  }

 /**
   * Get iuv
   * @return iuv
  **/
  @XmlElement(name="iuv")
  public String getIuv() {
    return iuv;
  }

  public void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public StorniDto iuv(String iuv) {
    this.iuv = iuv;
    return this;
  }

 /**
   * Get tipoRevoca
   * @return tipoRevoca
  **/
  @XmlElement(name="tipoRevoca")
  public String getTipoRevoca() {
    return tipoRevoca;
  }

  public void setTipoRevoca(String tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
  }

  public StorniDto tipoRevoca(String tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
    return this;
  }

 /**
   * Get voci
   * @return voci
  **/
  @XmlElement(name="voci")
  public List<StorniVociDto> getVoci() {
    return voci;
  }

  public void setVoci(List<StorniVociDto> voci) {
    this.voci = voci;
  }

  public StorniDto voci(List<StorniVociDto> voci) {
    this.voci = voci;
    return this;
  }

  public StorniDto addVociItem(StorniVociDto vociItem) {
    this.voci.add(vociItem);
    return this;
  }

 /**
   * Get xml
   * @return xml
  **/
  @XmlElement(name="xml")
  public byte[] getXml() {
    return xml;
  }

  public void setXml(byte[] xml) {
    this.xml = xml;
  }

  public StorniDto xml(byte[] xml) {
    this.xml = xml;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StorniDto {\n");
    
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    dataRevoca: ").append(toIndentedString(dataRevoca)).append("\n");
    sb.append("    generato: ").append(toIndentedString(generato)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    idServizio: ").append(toIndentedString(idServizio)).append("\n");
    sb.append("    importoTotale: ").append(toIndentedString(importoTotale)).append("\n");
    sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
    sb.append("    tipoRevoca: ").append(toIndentedString(tipoRevoca)).append("\n");
    sb.append("    voci: ").append(toIndentedString(voci)).append("\n");
    sb.append("    xml: ").append(toIndentedString(xml)).append("\n");
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

