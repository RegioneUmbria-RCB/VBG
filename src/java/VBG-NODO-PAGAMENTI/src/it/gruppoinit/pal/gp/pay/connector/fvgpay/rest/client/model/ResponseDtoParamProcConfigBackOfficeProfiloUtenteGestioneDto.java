package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class ResponseDtoParamProcConfigBackOfficeProfiloUtenteGestioneDto  {
  
  
  private Errore errore = null;

  
  private ParamProcConfigBackOfficeProfiloUtenteGestioneDto payLoad = null;

  
  private Warning warning = null;
 /**
   * Get errore
   * @return errore
  **/
  @XmlElement(name="errore")
  public Errore getErrore() {
    return errore;
  }

  public void setErrore(Errore errore) {
    this.errore = errore;
  }

  public ResponseDtoParamProcConfigBackOfficeProfiloUtenteGestioneDto errore(Errore errore) {
    this.errore = errore;
    return this;
  }

 /**
   * Get payLoad
   * @return payLoad
  **/
  @XmlElement(name="payLoad")
  public ParamProcConfigBackOfficeProfiloUtenteGestioneDto getPayLoad() {
    return payLoad;
  }

  public void setPayLoad(ParamProcConfigBackOfficeProfiloUtenteGestioneDto payLoad) {
    this.payLoad = payLoad;
  }

  public ResponseDtoParamProcConfigBackOfficeProfiloUtenteGestioneDto payLoad(ParamProcConfigBackOfficeProfiloUtenteGestioneDto payLoad) {
    this.payLoad = payLoad;
    return this;
  }

 /**
   * Get warning
   * @return warning
  **/
  @XmlElement(name="warning")
  public Warning getWarning() {
    return warning;
  }

  public void setWarning(Warning warning) {
    this.warning = warning;
  }

  public ResponseDtoParamProcConfigBackOfficeProfiloUtenteGestioneDto warning(Warning warning) {
    this.warning = warning;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseDtoParamProcConfigBackOfficeProfiloUtenteGestioneDto {\n");
    
    sb.append("    errore: ").append(toIndentedString(errore)).append("\n");
    sb.append("    payLoad: ").append(toIndentedString(payLoad)).append("\n");
    sb.append("    warning: ").append(toIndentedString(warning)).append("\n");
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

