package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ListResponseAllTassonomie  {
  
  
  private String errorCode = null;

  
  private String errorMessage = null;

  
  private Integer totaleRecords = null;

  
  private List<AllTassonomie> values = null;
 /**
   * Get errorCode
   * @return errorCode
  **/
  @XmlElement(name="errorCode")
  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public ListResponseAllTassonomie errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

 /**
   * Get errorMessage
   * @return errorMessage
  **/
  @XmlElement(name="errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public ListResponseAllTassonomie errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

 /**
   * Get totaleRecords
   * @return totaleRecords
  **/
  @XmlElement(name="totaleRecords")
  public Integer getTotaleRecords() {
    return totaleRecords;
  }

  public void setTotaleRecords(Integer totaleRecords) {
    this.totaleRecords = totaleRecords;
  }

  public ListResponseAllTassonomie totaleRecords(Integer totaleRecords) {
    this.totaleRecords = totaleRecords;
    return this;
  }

 /**
   * Get values
   * @return values
  **/
  @XmlElement(name="values")
  public List<AllTassonomie> getValues() {
    return values;
  }

  public void setValues(List<AllTassonomie> values) {
    this.values = values;
  }

  public ListResponseAllTassonomie values(List<AllTassonomie> values) {
    this.values = values;
    return this;
  }

  public ListResponseAllTassonomie addValuesItem(AllTassonomie valuesItem) {
    this.values.add(valuesItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListResponseAllTassonomie {\n");
    
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    totaleRecords: ").append(toIndentedString(totaleRecords)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
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

