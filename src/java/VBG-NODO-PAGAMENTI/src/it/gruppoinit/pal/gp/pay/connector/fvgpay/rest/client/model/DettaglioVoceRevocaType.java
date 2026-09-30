package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class DettaglioVoceRevocaType  {
  
  
  private EsecuzioneVoceRevocaType esecuzioneVoceRevoca = null;

  
  private RichiestaVoceRevocaType richiestaVoceRevoca = null;
 /**
   * Get esecuzioneVoceRevoca
   * @return esecuzioneVoceRevoca
  **/
  @XmlElement(name="esecuzione_voce_revoca")
  public EsecuzioneVoceRevocaType getEsecuzioneVoceRevoca() {
    return esecuzioneVoceRevoca;
  }

  public void setEsecuzioneVoceRevoca(EsecuzioneVoceRevocaType esecuzioneVoceRevoca) {
    this.esecuzioneVoceRevoca = esecuzioneVoceRevoca;
  }

  public DettaglioVoceRevocaType esecuzioneVoceRevoca(EsecuzioneVoceRevocaType esecuzioneVoceRevoca) {
    this.esecuzioneVoceRevoca = esecuzioneVoceRevoca;
    return this;
  }

 /**
   * Get richiestaVoceRevoca
   * @return richiestaVoceRevoca
  **/
  @XmlElement(name="richiesta_voce_revoca")
  public RichiestaVoceRevocaType getRichiestaVoceRevoca() {
    return richiestaVoceRevoca;
  }

  public void setRichiestaVoceRevoca(RichiestaVoceRevocaType richiestaVoceRevoca) {
    this.richiestaVoceRevoca = richiestaVoceRevoca;
  }

  public DettaglioVoceRevocaType richiestaVoceRevoca(RichiestaVoceRevocaType richiestaVoceRevoca) {
    this.richiestaVoceRevoca = richiestaVoceRevoca;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DettaglioVoceRevocaType {\n");
    
    sb.append("    esecuzioneVoceRevoca: ").append(toIndentedString(esecuzioneVoceRevoca)).append("\n");
    sb.append("    richiestaVoceRevoca: ").append(toIndentedString(richiestaVoceRevoca)).append("\n");
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

