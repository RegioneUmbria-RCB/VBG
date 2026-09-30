package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class RtDto  {
  
  
  private String capAttestante = null;

  
  private String civicoAttestante = null;

  
  private String codiceContestoPagamento = null;

  
  private String codiceEsitoPagamento = null;

  
  private String codiceUnitOperAttestante = null;

  
  private Date dataOramessaggioRicevuta = null;

  
  private String denomUnitOperAttestante = null;

  
  private String denominazioneAttestante = null;

  
  private String esitoInvioRt = null;

  
  private Long id = null;

  
  private Long identificativoDominio = null;

  
  private String identificativoMsgRicevuta = null;

  
  private String identificativoStRichiedente = null;

  
  private String identificativoUnVersamento = null;

  
  private Double importoTotalePagato = null;

  
  private String indirizzoAttestante = null;

  
  private String iuv = null;

  
  private String localitaAttestante = null;

  
  private String nazioneAttestante = null;

  
  private String provinciaAttestante = null;

  
  private String riferimentoDataRichiesta = null;

  
  private String riferimentoMessaggioRicevuta = null;

  
  private String tipoIdentificativoUnivoco = null;

  
  private String versioneOggetto = null;

  
  private byte[] xmlRt = null;
 /**
   * Get capAttestante
   * @return capAttestante
  **/
  @XmlElement(name="capAttestante")
  public String getCapAttestante() {
    return capAttestante;
  }

  public void setCapAttestante(String capAttestante) {
    this.capAttestante = capAttestante;
  }

  public RtDto capAttestante(String capAttestante) {
    this.capAttestante = capAttestante;
    return this;
  }

 /**
   * Get civicoAttestante
   * @return civicoAttestante
  **/
  @XmlElement(name="civicoAttestante")
  public String getCivicoAttestante() {
    return civicoAttestante;
  }

  public void setCivicoAttestante(String civicoAttestante) {
    this.civicoAttestante = civicoAttestante;
  }

  public RtDto civicoAttestante(String civicoAttestante) {
    this.civicoAttestante = civicoAttestante;
    return this;
  }

 /**
   * Get codiceContestoPagamento
   * @return codiceContestoPagamento
  **/
  @XmlElement(name="codiceContestoPagamento")
  public String getCodiceContestoPagamento() {
    return codiceContestoPagamento;
  }

  public void setCodiceContestoPagamento(String codiceContestoPagamento) {
    this.codiceContestoPagamento = codiceContestoPagamento;
  }

  public RtDto codiceContestoPagamento(String codiceContestoPagamento) {
    this.codiceContestoPagamento = codiceContestoPagamento;
    return this;
  }

 /**
   * Get codiceEsitoPagamento
   * @return codiceEsitoPagamento
  **/
  @XmlElement(name="codiceEsitoPagamento")
  public String getCodiceEsitoPagamento() {
    return codiceEsitoPagamento;
  }

  public void setCodiceEsitoPagamento(String codiceEsitoPagamento) {
    this.codiceEsitoPagamento = codiceEsitoPagamento;
  }

  public RtDto codiceEsitoPagamento(String codiceEsitoPagamento) {
    this.codiceEsitoPagamento = codiceEsitoPagamento;
    return this;
  }

 /**
   * Get codiceUnitOperAttestante
   * @return codiceUnitOperAttestante
  **/
  @XmlElement(name="codiceUnitOperAttestante")
  public String getCodiceUnitOperAttestante() {
    return codiceUnitOperAttestante;
  }

  public void setCodiceUnitOperAttestante(String codiceUnitOperAttestante) {
    this.codiceUnitOperAttestante = codiceUnitOperAttestante;
  }

  public RtDto codiceUnitOperAttestante(String codiceUnitOperAttestante) {
    this.codiceUnitOperAttestante = codiceUnitOperAttestante;
    return this;
  }

 /**
   * Get dataOramessaggioRicevuta
   * @return dataOramessaggioRicevuta
  **/
  @XmlElement(name="dataOramessaggioRicevuta")
  public Date getDataOramessaggioRicevuta() {
    return dataOramessaggioRicevuta;
  }

  public void setDataOramessaggioRicevuta(Date dataOramessaggioRicevuta) {
    this.dataOramessaggioRicevuta = dataOramessaggioRicevuta;
  }

  public RtDto dataOramessaggioRicevuta(Date dataOramessaggioRicevuta) {
    this.dataOramessaggioRicevuta = dataOramessaggioRicevuta;
    return this;
  }

 /**
   * Get denomUnitOperAttestante
   * @return denomUnitOperAttestante
  **/
  @XmlElement(name="denomUnitOperAttestante")
  public String getDenomUnitOperAttestante() {
    return denomUnitOperAttestante;
  }

  public void setDenomUnitOperAttestante(String denomUnitOperAttestante) {
    this.denomUnitOperAttestante = denomUnitOperAttestante;
  }

  public RtDto denomUnitOperAttestante(String denomUnitOperAttestante) {
    this.denomUnitOperAttestante = denomUnitOperAttestante;
    return this;
  }

 /**
   * Get denominazioneAttestante
   * @return denominazioneAttestante
  **/
  @XmlElement(name="denominazioneAttestante")
  public String getDenominazioneAttestante() {
    return denominazioneAttestante;
  }

  public void setDenominazioneAttestante(String denominazioneAttestante) {
    this.denominazioneAttestante = denominazioneAttestante;
  }

  public RtDto denominazioneAttestante(String denominazioneAttestante) {
    this.denominazioneAttestante = denominazioneAttestante;
    return this;
  }

 /**
   * Get esitoInvioRt
   * @return esitoInvioRt
  **/
  @XmlElement(name="esitoInvioRt")
  public String getEsitoInvioRt() {
    return esitoInvioRt;
  }

  public void setEsitoInvioRt(String esitoInvioRt) {
    this.esitoInvioRt = esitoInvioRt;
  }

  public RtDto esitoInvioRt(String esitoInvioRt) {
    this.esitoInvioRt = esitoInvioRt;
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

  public RtDto id(Long id) {
    this.id = id;
    return this;
  }

 /**
   * Get identificativoDominio
   * @return identificativoDominio
  **/
  @XmlElement(name="identificativoDominio")
  public Long getIdentificativoDominio() {
    return identificativoDominio;
  }

  public void setIdentificativoDominio(Long identificativoDominio) {
    this.identificativoDominio = identificativoDominio;
  }

  public RtDto identificativoDominio(Long identificativoDominio) {
    this.identificativoDominio = identificativoDominio;
    return this;
  }

 /**
   * Get identificativoMsgRicevuta
   * @return identificativoMsgRicevuta
  **/
  @XmlElement(name="identificativoMsgRicevuta")
  public String getIdentificativoMsgRicevuta() {
    return identificativoMsgRicevuta;
  }

  public void setIdentificativoMsgRicevuta(String identificativoMsgRicevuta) {
    this.identificativoMsgRicevuta = identificativoMsgRicevuta;
  }

  public RtDto identificativoMsgRicevuta(String identificativoMsgRicevuta) {
    this.identificativoMsgRicevuta = identificativoMsgRicevuta;
    return this;
  }

 /**
   * Get identificativoStRichiedente
   * @return identificativoStRichiedente
  **/
  @XmlElement(name="identificativoStRichiedente")
  public String getIdentificativoStRichiedente() {
    return identificativoStRichiedente;
  }

  public void setIdentificativoStRichiedente(String identificativoStRichiedente) {
    this.identificativoStRichiedente = identificativoStRichiedente;
  }

  public RtDto identificativoStRichiedente(String identificativoStRichiedente) {
    this.identificativoStRichiedente = identificativoStRichiedente;
    return this;
  }

 /**
   * Get identificativoUnVersamento
   * @return identificativoUnVersamento
  **/
  @XmlElement(name="identificativoUnVersamento")
  public String getIdentificativoUnVersamento() {
    return identificativoUnVersamento;
  }

  public void setIdentificativoUnVersamento(String identificativoUnVersamento) {
    this.identificativoUnVersamento = identificativoUnVersamento;
  }

  public RtDto identificativoUnVersamento(String identificativoUnVersamento) {
    this.identificativoUnVersamento = identificativoUnVersamento;
    return this;
  }

 /**
   * Get importoTotalePagato
   * @return importoTotalePagato
  **/
  @XmlElement(name="importoTotalePagato")
  public Double getImportoTotalePagato() {
    return importoTotalePagato;
  }

  public void setImportoTotalePagato(Double importoTotalePagato) {
    this.importoTotalePagato = importoTotalePagato;
  }

  public RtDto importoTotalePagato(Double importoTotalePagato) {
    this.importoTotalePagato = importoTotalePagato;
    return this;
  }

 /**
   * Get indirizzoAttestante
   * @return indirizzoAttestante
  **/
  @XmlElement(name="indirizzoAttestante")
  public String getIndirizzoAttestante() {
    return indirizzoAttestante;
  }

  public void setIndirizzoAttestante(String indirizzoAttestante) {
    this.indirizzoAttestante = indirizzoAttestante;
  }

  public RtDto indirizzoAttestante(String indirizzoAttestante) {
    this.indirizzoAttestante = indirizzoAttestante;
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

  public RtDto iuv(String iuv) {
    this.iuv = iuv;
    return this;
  }

 /**
   * Get localitaAttestante
   * @return localitaAttestante
  **/
  @XmlElement(name="localitaAttestante")
  public String getLocalitaAttestante() {
    return localitaAttestante;
  }

  public void setLocalitaAttestante(String localitaAttestante) {
    this.localitaAttestante = localitaAttestante;
  }

  public RtDto localitaAttestante(String localitaAttestante) {
    this.localitaAttestante = localitaAttestante;
    return this;
  }

 /**
   * Get nazioneAttestante
   * @return nazioneAttestante
  **/
  @XmlElement(name="nazioneAttestante")
  public String getNazioneAttestante() {
    return nazioneAttestante;
  }

  public void setNazioneAttestante(String nazioneAttestante) {
    this.nazioneAttestante = nazioneAttestante;
  }

  public RtDto nazioneAttestante(String nazioneAttestante) {
    this.nazioneAttestante = nazioneAttestante;
    return this;
  }

 /**
   * Get provinciaAttestante
   * @return provinciaAttestante
  **/
  @XmlElement(name="provinciaAttestante")
  public String getProvinciaAttestante() {
    return provinciaAttestante;
  }

  public void setProvinciaAttestante(String provinciaAttestante) {
    this.provinciaAttestante = provinciaAttestante;
  }

  public RtDto provinciaAttestante(String provinciaAttestante) {
    this.provinciaAttestante = provinciaAttestante;
    return this;
  }

 /**
   * Get riferimentoDataRichiesta
   * @return riferimentoDataRichiesta
  **/
  @XmlElement(name="riferimentoDataRichiesta")
  public String getRiferimentoDataRichiesta() {
    return riferimentoDataRichiesta;
  }

  public void setRiferimentoDataRichiesta(String riferimentoDataRichiesta) {
    this.riferimentoDataRichiesta = riferimentoDataRichiesta;
  }

  public RtDto riferimentoDataRichiesta(String riferimentoDataRichiesta) {
    this.riferimentoDataRichiesta = riferimentoDataRichiesta;
    return this;
  }

 /**
   * Get riferimentoMessaggioRicevuta
   * @return riferimentoMessaggioRicevuta
  **/
  @XmlElement(name="riferimentoMessaggioRicevuta")
  public String getRiferimentoMessaggioRicevuta() {
    return riferimentoMessaggioRicevuta;
  }

  public void setRiferimentoMessaggioRicevuta(String riferimentoMessaggioRicevuta) {
    this.riferimentoMessaggioRicevuta = riferimentoMessaggioRicevuta;
  }

  public RtDto riferimentoMessaggioRicevuta(String riferimentoMessaggioRicevuta) {
    this.riferimentoMessaggioRicevuta = riferimentoMessaggioRicevuta;
    return this;
  }

 /**
   * Get tipoIdentificativoUnivoco
   * @return tipoIdentificativoUnivoco
  **/
  @XmlElement(name="tipoIdentificativoUnivoco")
  public String getTipoIdentificativoUnivoco() {
    return tipoIdentificativoUnivoco;
  }

  public void setTipoIdentificativoUnivoco(String tipoIdentificativoUnivoco) {
    this.tipoIdentificativoUnivoco = tipoIdentificativoUnivoco;
  }

  public RtDto tipoIdentificativoUnivoco(String tipoIdentificativoUnivoco) {
    this.tipoIdentificativoUnivoco = tipoIdentificativoUnivoco;
    return this;
  }

 /**
   * Get versioneOggetto
   * @return versioneOggetto
  **/
  @XmlElement(name="versioneOggetto")
  public String getVersioneOggetto() {
    return versioneOggetto;
  }

  public void setVersioneOggetto(String versioneOggetto) {
    this.versioneOggetto = versioneOggetto;
  }

  public RtDto versioneOggetto(String versioneOggetto) {
    this.versioneOggetto = versioneOggetto;
    return this;
  }

 /**
   * Get xmlRt
   * @return xmlRt
  **/
  @XmlElement(name="xmlRt")
  public byte[] getXmlRt() {
    return xmlRt;
  }

  public void setXmlRt(byte[] xmlRt) {
    this.xmlRt = xmlRt;
  }

  public RtDto xmlRt(byte[] xmlRt) {
    this.xmlRt = xmlRt;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RtDto {\n");
    
    sb.append("    capAttestante: ").append(toIndentedString(capAttestante)).append("\n");
    sb.append("    civicoAttestante: ").append(toIndentedString(civicoAttestante)).append("\n");
    sb.append("    codiceContestoPagamento: ").append(toIndentedString(codiceContestoPagamento)).append("\n");
    sb.append("    codiceEsitoPagamento: ").append(toIndentedString(codiceEsitoPagamento)).append("\n");
    sb.append("    codiceUnitOperAttestante: ").append(toIndentedString(codiceUnitOperAttestante)).append("\n");
    sb.append("    dataOramessaggioRicevuta: ").append(toIndentedString(dataOramessaggioRicevuta)).append("\n");
    sb.append("    denomUnitOperAttestante: ").append(toIndentedString(denomUnitOperAttestante)).append("\n");
    sb.append("    denominazioneAttestante: ").append(toIndentedString(denominazioneAttestante)).append("\n");
    sb.append("    esitoInvioRt: ").append(toIndentedString(esitoInvioRt)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    identificativoDominio: ").append(toIndentedString(identificativoDominio)).append("\n");
    sb.append("    identificativoMsgRicevuta: ").append(toIndentedString(identificativoMsgRicevuta)).append("\n");
    sb.append("    identificativoStRichiedente: ").append(toIndentedString(identificativoStRichiedente)).append("\n");
    sb.append("    identificativoUnVersamento: ").append(toIndentedString(identificativoUnVersamento)).append("\n");
    sb.append("    importoTotalePagato: ").append(toIndentedString(importoTotalePagato)).append("\n");
    sb.append("    indirizzoAttestante: ").append(toIndentedString(indirizzoAttestante)).append("\n");
    sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
    sb.append("    localitaAttestante: ").append(toIndentedString(localitaAttestante)).append("\n");
    sb.append("    nazioneAttestante: ").append(toIndentedString(nazioneAttestante)).append("\n");
    sb.append("    provinciaAttestante: ").append(toIndentedString(provinciaAttestante)).append("\n");
    sb.append("    riferimentoDataRichiesta: ").append(toIndentedString(riferimentoDataRichiesta)).append("\n");
    sb.append("    riferimentoMessaggioRicevuta: ").append(toIndentedString(riferimentoMessaggioRicevuta)).append("\n");
    sb.append("    tipoIdentificativoUnivoco: ").append(toIndentedString(tipoIdentificativoUnivoco)).append("\n");
    sb.append("    versioneOggetto: ").append(toIndentedString(versioneOggetto)).append("\n");
    sb.append("    xmlRt: ").append(toIndentedString(xmlRt)).append("\n");
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

