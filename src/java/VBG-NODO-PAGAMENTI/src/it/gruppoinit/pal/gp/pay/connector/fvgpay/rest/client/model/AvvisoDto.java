package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class AvvisoDto  {
  
  
  private String applicativo = null;

  
  private Date dataA = null;

  
  private Date dataDa = null;

  
  private String messaggio = null;

  
  private Integer tipo = null;
 /**
   * Get applicativo
   * @return applicativo
  **/
  @XmlElement(name="applicativo")
  public String getApplicativo() {
    return applicativo;
  }

  public void setApplicativo(String applicativo) {
    this.applicativo = applicativo;
  }

  public AvvisoDto applicativo(String applicativo) {
    this.applicativo = applicativo;
    return this;
  }

 /**
   * Get dataA
   * @return dataA
  **/
  @XmlElement(name="dataA")
  public Date getDataA() {
    return dataA;
  }

  public void setDataA(Date dataA) {
    this.dataA = dataA;
  }

  public AvvisoDto dataA(Date dataA) {
    this.dataA = dataA;
    return this;
  }

 /**
   * Get dataDa
   * @return dataDa
  **/
  @XmlElement(name="dataDa")
  public Date getDataDa() {
    return dataDa;
  }

  public void setDataDa(Date dataDa) {
    this.dataDa = dataDa;
  }

  public AvvisoDto dataDa(Date dataDa) {
    this.dataDa = dataDa;
    return this;
  }

 /**
   * Get messaggio
   * @return messaggio
  **/
  @XmlElement(name="messaggio")
  public String getMessaggio() {
    return messaggio;
  }

  public void setMessaggio(String messaggio) {
    this.messaggio = messaggio;
  }

  public AvvisoDto messaggio(String messaggio) {
    this.messaggio = messaggio;
    return this;
  }

 /**
   * Get tipo
   * @return tipo
  **/
  @XmlElement(name="tipo")
  public Integer getTipo() {
    return tipo;
  }

  public void setTipo(Integer tipo) {
    this.tipo = tipo;
  }

  public AvvisoDto tipo(Integer tipo) {
    this.tipo = tipo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvvisoDto {\n");
    
    sb.append("    applicativo: ").append(toIndentedString(applicativo)).append("\n");
    sb.append("    dataA: ").append(toIndentedString(dataA)).append("\n");
    sb.append("    dataDa: ").append(toIndentedString(dataDa)).append("\n");
    sb.append("    messaggio: ").append(toIndentedString(messaggio)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
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

