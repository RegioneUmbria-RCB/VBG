package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

public class ModelAndView  {
  
  
  private Boolean empty = null;

  
  private Object model = null;

  
  private Map<String, Object> modelMap = null;

  
  private Boolean reference = null;


@XmlType(name="StatusEnum")
@XmlEnum(String.class)
public enum StatusEnum {

@XmlEnumValue("100") _100(String.valueOf("100")), @XmlEnumValue("101") _101(String.valueOf("101")), @XmlEnumValue("102") _102(String.valueOf("102")), @XmlEnumValue("103") _103(String.valueOf("103")), @XmlEnumValue("200") _200(String.valueOf("200")), @XmlEnumValue("201") _201(String.valueOf("201")), @XmlEnumValue("202") _202(String.valueOf("202")), @XmlEnumValue("203") _203(String.valueOf("203")), @XmlEnumValue("204") _204(String.valueOf("204")), @XmlEnumValue("205") _205(String.valueOf("205")), @XmlEnumValue("206") _206(String.valueOf("206")), @XmlEnumValue("207") _207(String.valueOf("207")), @XmlEnumValue("208") _208(String.valueOf("208")), @XmlEnumValue("226") _226(String.valueOf("226")), @XmlEnumValue("300") _300(String.valueOf("300")), @XmlEnumValue("301") _301(String.valueOf("301")), @XmlEnumValue("302") _302(String.valueOf("302")), @XmlEnumValue("303") _303(String.valueOf("303")), @XmlEnumValue("304") _304(String.valueOf("304")), @XmlEnumValue("305") _305(String.valueOf("305")), @XmlEnumValue("307") _307(String.valueOf("307")), @XmlEnumValue("308") _308(String.valueOf("308")), @XmlEnumValue("400") _400(String.valueOf("400")), @XmlEnumValue("401") _401(String.valueOf("401")), @XmlEnumValue("402") _402(String.valueOf("402")), @XmlEnumValue("403") _403(String.valueOf("403")), @XmlEnumValue("404") _404(String.valueOf("404")), @XmlEnumValue("405") _405(String.valueOf("405")), @XmlEnumValue("406") _406(String.valueOf("406")), @XmlEnumValue("407") _407(String.valueOf("407")), @XmlEnumValue("408") _408(String.valueOf("408")), @XmlEnumValue("409") _409(String.valueOf("409")), @XmlEnumValue("410") _410(String.valueOf("410")), @XmlEnumValue("411") _411(String.valueOf("411")), @XmlEnumValue("412") _412(String.valueOf("412")), @XmlEnumValue("413") _413(String.valueOf("413")), @XmlEnumValue("414") _414(String.valueOf("414")), @XmlEnumValue("415") _415(String.valueOf("415")), @XmlEnumValue("416") _416(String.valueOf("416")), @XmlEnumValue("417") _417(String.valueOf("417")), @XmlEnumValue("418") _418(String.valueOf("418")), @XmlEnumValue("419") _419(String.valueOf("419")), @XmlEnumValue("420") _420(String.valueOf("420")), @XmlEnumValue("421") _421(String.valueOf("421")), @XmlEnumValue("422") _422(String.valueOf("422")), @XmlEnumValue("423") _423(String.valueOf("423")), @XmlEnumValue("424") _424(String.valueOf("424")), @XmlEnumValue("426") _426(String.valueOf("426")), @XmlEnumValue("428") _428(String.valueOf("428")), @XmlEnumValue("429") _429(String.valueOf("429")), @XmlEnumValue("431") _431(String.valueOf("431")), @XmlEnumValue("451") _451(String.valueOf("451")), @XmlEnumValue("500") _500(String.valueOf("500")), @XmlEnumValue("501") _501(String.valueOf("501")), @XmlEnumValue("502") _502(String.valueOf("502")), @XmlEnumValue("503") _503(String.valueOf("503")), @XmlEnumValue("504") _504(String.valueOf("504")), @XmlEnumValue("505") _505(String.valueOf("505")), @XmlEnumValue("506") _506(String.valueOf("506")), @XmlEnumValue("507") _507(String.valueOf("507")), @XmlEnumValue("508") _508(String.valueOf("508")), @XmlEnumValue("509") _509(String.valueOf("509")), @XmlEnumValue("510") _510(String.valueOf("510")), @XmlEnumValue("511") _511(String.valueOf("511"));


    private String value;

    StatusEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static StatusEnum fromValue(String v) {
        for (StatusEnum b : StatusEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  
  private StatusEnum status = null;

  
  private View view = null;

  
  private String viewName = null;
 /**
   * Get empty
   * @return empty
  **/
  @XmlElement(name="empty")
  public Boolean isEmpty() {
    return empty;
  }

  public void setEmpty(Boolean empty) {
    this.empty = empty;
  }

  public ModelAndView empty(Boolean empty) {
    this.empty = empty;
    return this;
  }

 /**
   * Get model
   * @return model
  **/
  @XmlElement(name="model")
  public Object getModel() {
    return model;
  }

  public void setModel(Object model) {
    this.model = model;
  }

  public ModelAndView model(Object model) {
    this.model = model;
    return this;
  }

 /**
   * Get modelMap
   * @return modelMap
  **/
  @XmlElement(name="modelMap")
  public Map<String, Object> getModelMap() {
    return modelMap;
  }

  public void setModelMap(Map<String, Object> modelMap) {
    this.modelMap = modelMap;
  }

  public ModelAndView modelMap(Map<String, Object> modelMap) {
    this.modelMap = modelMap;
    return this;
  }

  public ModelAndView putModelMapItem(String key, Object modelMapItem) {
    this.modelMap.put(key, modelMapItem);
    return this;
  }

 /**
   * Get reference
   * @return reference
  **/
  @XmlElement(name="reference")
  public Boolean isReference() {
    return reference;
  }

  public void setReference(Boolean reference) {
    this.reference = reference;
  }

  public ModelAndView reference(Boolean reference) {
    this.reference = reference;
    return this;
  }

 /**
   * Get status
   * @return status
  **/
  @XmlElement(name="status")
  public String getStatus() {
    if (status == null) {
      return null;
    }
    return status.value();
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public ModelAndView status(StatusEnum status) {
    this.status = status;
    return this;
  }

 /**
   * Get view
   * @return view
  **/
  @XmlElement(name="view")
  public View getView() {
    return view;
  }

  public void setView(View view) {
    this.view = view;
  }

  public ModelAndView view(View view) {
    this.view = view;
    return this;
  }

 /**
   * Get viewName
   * @return viewName
  **/
  @XmlElement(name="viewName")
  public String getViewName() {
    return viewName;
  }

  public void setViewName(String viewName) {
    this.viewName = viewName;
  }

  public ModelAndView viewName(String viewName) {
    this.viewName = viewName;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelAndView {\n");
    
    sb.append("    empty: ").append(toIndentedString(empty)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    modelMap: ").append(toIndentedString(modelMap)).append("\n");
    sb.append("    reference: ").append(toIndentedString(reference)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    view: ").append(toIndentedString(view)).append("\n");
    sb.append("    viewName: ").append(toIndentedString(viewName)).append("\n");
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

