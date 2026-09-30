package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;


import javax.xml.bind.annotation.XmlElement;

public class RichiestaVoceRevocaType  {
  
  
 /**
   * Causale della singola voce di revoca Se non viene specificata dovra' essere usata la causale specificata per l'intera richiesta di revoca 
  **/
  private String causale = null;

  
 /**
   * Informazioni aggiuntive sul motivo per cui viene richiesta la revoca 
  **/
  private String datiAggiuntiviRevoca = null;

  
 /**
   * identifica la voce di pagamento da revocare 
  **/
  private String identificativoUnivocoRiscossione = null;

  
  private ImportoType importo = null;
 /**
   * Causale della singola voce di revoca Se non viene specificata dovra&#39; essere usata la causale specificata per l&#39;intera richiesta di revoca 
   * @return causale
  **/
  @XmlElement(name="causale")
  public String getCausale() {
    return causale;
  }

  public void setCausale(String causale) {
    this.causale = causale;
  }

  public RichiestaVoceRevocaType causale(String causale) {
    this.causale = causale;
    return this;
  }

 /**
   * Informazioni aggiuntive sul motivo per cui viene richiesta la revoca 
   * @return datiAggiuntiviRevoca
  **/
  @XmlElement(name="dati_aggiuntivi_revoca")
  public String getDatiAggiuntiviRevoca() {
    return datiAggiuntiviRevoca;
  }

  public void setDatiAggiuntiviRevoca(String datiAggiuntiviRevoca) {
    this.datiAggiuntiviRevoca = datiAggiuntiviRevoca;
  }

  public RichiestaVoceRevocaType datiAggiuntiviRevoca(String datiAggiuntiviRevoca) {
    this.datiAggiuntiviRevoca = datiAggiuntiviRevoca;
    return this;
  }

 /**
   * identifica la voce di pagamento da revocare 
   * @return identificativoUnivocoRiscossione
  **/
  @XmlElement(name="identificativo_univoco_riscossione")
  public String getIdentificativoUnivocoRiscossione() {
    return identificativoUnivocoRiscossione;
  }

  public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
    this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
  }

  public RichiestaVoceRevocaType identificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
    this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
    return this;
  }

 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public ImportoType getImporto() {
    return importo;
  }

  public void setImporto(ImportoType importo) {
    this.importo = importo;
  }

  public RichiestaVoceRevocaType importo(ImportoType importo) {
    this.importo = importo;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RichiestaVoceRevocaType {\n");
    
    sb.append("    causale: ").append(toIndentedString(causale)).append("\n");
    sb.append("    datiAggiuntiviRevoca: ").append(toIndentedString(datiAggiuntiviRevoca)).append("\n");
    sb.append("    identificativoUnivocoRiscossione: ").append(toIndentedString(identificativoUnivocoRiscossione)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
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

