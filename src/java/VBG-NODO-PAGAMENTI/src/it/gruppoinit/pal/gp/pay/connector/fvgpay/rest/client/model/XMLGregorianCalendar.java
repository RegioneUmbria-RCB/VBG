package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

public class XMLGregorianCalendar  {
  
  
  private Integer day = null;

  
  private Integer eon = null;

  
  private Integer eonAndYear = null;

  
  private BigDecimal fractionalSecond = null;

  
  private Integer hour = null;

  
  private Integer millisecond = null;

  
  private Integer minute = null;

  
  private Integer month = null;

  
  private Integer second = null;

  
  private Integer timezone = null;

  
  private Boolean valid = null;

  
  private QName xmlschemaType = null;

  
  private Integer year = null;
 /**
   * Get day
   * @return day
  **/
  @XmlElement(name="day")
  public Integer getDay() {
    return day;
  }

  public void setDay(Integer day) {
    this.day = day;
  }

  public XMLGregorianCalendar day(Integer day) {
    this.day = day;
    return this;
  }

 /**
   * Get eon
   * @return eon
  **/
  @XmlElement(name="eon")
  public Integer getEon() {
    return eon;
  }

  public void setEon(Integer eon) {
    this.eon = eon;
  }

  public XMLGregorianCalendar eon(Integer eon) {
    this.eon = eon;
    return this;
  }

 /**
   * Get eonAndYear
   * @return eonAndYear
  **/
  @XmlElement(name="eonAndYear")
  public Integer getEonAndYear() {
    return eonAndYear;
  }

  public void setEonAndYear(Integer eonAndYear) {
    this.eonAndYear = eonAndYear;
  }

  public XMLGregorianCalendar eonAndYear(Integer eonAndYear) {
    this.eonAndYear = eonAndYear;
    return this;
  }

 /**
   * Get fractionalSecond
   * @return fractionalSecond
  **/
  @XmlElement(name="fractionalSecond")
  public BigDecimal getFractionalSecond() {
    return fractionalSecond;
  }

  public void setFractionalSecond(BigDecimal fractionalSecond) {
    this.fractionalSecond = fractionalSecond;
  }

  public XMLGregorianCalendar fractionalSecond(BigDecimal fractionalSecond) {
    this.fractionalSecond = fractionalSecond;
    return this;
  }

 /**
   * Get hour
   * @return hour
  **/
  @XmlElement(name="hour")
  public Integer getHour() {
    return hour;
  }

  public void setHour(Integer hour) {
    this.hour = hour;
  }

  public XMLGregorianCalendar hour(Integer hour) {
    this.hour = hour;
    return this;
  }

 /**
   * Get millisecond
   * @return millisecond
  **/
  @XmlElement(name="millisecond")
  public Integer getMillisecond() {
    return millisecond;
  }

  public void setMillisecond(Integer millisecond) {
    this.millisecond = millisecond;
  }

  public XMLGregorianCalendar millisecond(Integer millisecond) {
    this.millisecond = millisecond;
    return this;
  }

 /**
   * Get minute
   * @return minute
  **/
  @XmlElement(name="minute")
  public Integer getMinute() {
    return minute;
  }

  public void setMinute(Integer minute) {
    this.minute = minute;
  }

  public XMLGregorianCalendar minute(Integer minute) {
    this.minute = minute;
    return this;
  }

 /**
   * Get month
   * @return month
  **/
  @XmlElement(name="month")
  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public XMLGregorianCalendar month(Integer month) {
    this.month = month;
    return this;
  }

 /**
   * Get second
   * @return second
  **/
  @XmlElement(name="second")
  public Integer getSecond() {
    return second;
  }

  public void setSecond(Integer second) {
    this.second = second;
  }

  public XMLGregorianCalendar second(Integer second) {
    this.second = second;
    return this;
  }

 /**
   * Get timezone
   * @return timezone
  **/
  @XmlElement(name="timezone")
  public Integer getTimezone() {
    return timezone;
  }

  public void setTimezone(Integer timezone) {
    this.timezone = timezone;
  }

  public XMLGregorianCalendar timezone(Integer timezone) {
    this.timezone = timezone;
    return this;
  }

 /**
   * Get valid
   * @return valid
  **/
  @XmlElement(name="valid")
  public Boolean isValid() {
    return valid;
  }

  public void setValid(Boolean valid) {
    this.valid = valid;
  }

  public XMLGregorianCalendar valid(Boolean valid) {
    this.valid = valid;
    return this;
  }

 /**
   * Get xmlschemaType
   * @return xmlschemaType
  **/
  @XmlElement(name="xmlschemaType")
  public QName getXmlschemaType() {
    return xmlschemaType;
  }

  public void setXmlschemaType(QName xmlschemaType) {
    this.xmlschemaType = xmlschemaType;
  }

  public XMLGregorianCalendar xmlschemaType(QName xmlschemaType) {
    this.xmlschemaType = xmlschemaType;
    return this;
  }

 /**
   * Get year
   * @return year
  **/
  @XmlElement(name="year")
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public XMLGregorianCalendar year(Integer year) {
    this.year = year;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class XMLGregorianCalendar {\n");
    
    sb.append("    day: ").append(toIndentedString(day)).append("\n");
    sb.append("    eon: ").append(toIndentedString(eon)).append("\n");
    sb.append("    eonAndYear: ").append(toIndentedString(eonAndYear)).append("\n");
    sb.append("    fractionalSecond: ").append(toIndentedString(fractionalSecond)).append("\n");
    sb.append("    hour: ").append(toIndentedString(hour)).append("\n");
    sb.append("    millisecond: ").append(toIndentedString(millisecond)).append("\n");
    sb.append("    minute: ").append(toIndentedString(minute)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    second: ").append(toIndentedString(second)).append("\n");
    sb.append("    timezone: ").append(toIndentedString(timezone)).append("\n");
    sb.append("    valid: ").append(toIndentedString(valid)).append("\n");
    sb.append("    xmlschemaType: ").append(toIndentedString(xmlschemaType)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
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

