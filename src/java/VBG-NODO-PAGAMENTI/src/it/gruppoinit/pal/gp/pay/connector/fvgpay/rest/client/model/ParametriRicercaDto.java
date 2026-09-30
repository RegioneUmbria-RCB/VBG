package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;



import javax.xml.bind.annotation.XmlElement;

public class ParametriRicercaDto  {
  
  
  private Boolean cercaPendenzeConRevocaCreataDallEnte = null;

  
  private Boolean cercaPendenzeRevocate = null;

  
  private String codFisc = null;

  
  private String dataCreazioneA = null;

  
  private String dataCreazioneDa = null;

  
  private String dataPubblicazioneAvvisoA = null;

  
  private String dataPubblicazioneAvvisoDa = null;

  
  private String dataScadenzaA = null;

  
  private String dataScadenzaAvvisoA = null;

  
  private String dataScadenzaAvvisoDa = null;

  
  private String dataScadenzaDa = null;

  
  private String dataTransazioneA = null;

  
  private String dataTransazioneDa = null;

  
  private String denominazione = null;

  
  private String esitoInvio = null;

  
  private String esitoTransazione = null;

  
  private String idDebito = null;

  
  private Long idEnte = null;

  
  private String idEnteString = null;

  
  private String idRichiesta = null;

  
  private Long idServizio = null;

  
  private String idServizioString = null;

  
  private String idTrasmissione = null;

  
  private String iuv = null;

  
  private Integer limit = null;

  
  private Integer offSet = null;

  
  private String provenienzaRevoca = null;

  
  private String stato = null;

  
  private Integer statoNotificaRevoca = null;

  
  private String statoRichiesta = null;

  
  private Integer tipoRevoca = null;
 /**
   * Get cercaPendenzeConRevocaCreataDallEnte
   * @return cercaPendenzeConRevocaCreataDallEnte
  **/
  @XmlElement(name="cercaPendenzeConRevocaCreataDallEnte")
  public Boolean isCercaPendenzeConRevocaCreataDallEnte() {
    return cercaPendenzeConRevocaCreataDallEnte;
  }

  public void setCercaPendenzeConRevocaCreataDallEnte(Boolean cercaPendenzeConRevocaCreataDallEnte) {
    this.cercaPendenzeConRevocaCreataDallEnte = cercaPendenzeConRevocaCreataDallEnte;
  }

  public ParametriRicercaDto cercaPendenzeConRevocaCreataDallEnte(Boolean cercaPendenzeConRevocaCreataDallEnte) {
    this.cercaPendenzeConRevocaCreataDallEnte = cercaPendenzeConRevocaCreataDallEnte;
    return this;
  }

 /**
   * Get cercaPendenzeRevocate
   * @return cercaPendenzeRevocate
  **/
  @XmlElement(name="cercaPendenzeRevocate")
  public Boolean isCercaPendenzeRevocate() {
    return cercaPendenzeRevocate;
  }

  public void setCercaPendenzeRevocate(Boolean cercaPendenzeRevocate) {
    this.cercaPendenzeRevocate = cercaPendenzeRevocate;
  }

  public ParametriRicercaDto cercaPendenzeRevocate(Boolean cercaPendenzeRevocate) {
    this.cercaPendenzeRevocate = cercaPendenzeRevocate;
    return this;
  }

 /**
   * Get codFisc
   * @return codFisc
  **/
  @XmlElement(name="codFisc")
  public String getCodFisc() {
    return codFisc;
  }

  public void setCodFisc(String codFisc) {
    this.codFisc = codFisc;
  }

  public ParametriRicercaDto codFisc(String codFisc) {
    this.codFisc = codFisc;
    return this;
  }

 /**
   * Get dataCreazioneA
   * @return dataCreazioneA
  **/
  @XmlElement(name="dataCreazioneA")
  public String getDataCreazioneA() {
    return dataCreazioneA;
  }

  public void setDataCreazioneA(String dataCreazioneA) {
    this.dataCreazioneA = dataCreazioneA;
  }

  public ParametriRicercaDto dataCreazioneA(String dataCreazioneA) {
    this.dataCreazioneA = dataCreazioneA;
    return this;
  }

 /**
   * Get dataCreazioneDa
   * @return dataCreazioneDa
  **/
  @XmlElement(name="dataCreazioneDa")
  public String getDataCreazioneDa() {
    return dataCreazioneDa;
  }

  public void setDataCreazioneDa(String dataCreazioneDa) {
    this.dataCreazioneDa = dataCreazioneDa;
  }

  public ParametriRicercaDto dataCreazioneDa(String dataCreazioneDa) {
    this.dataCreazioneDa = dataCreazioneDa;
    return this;
  }

 /**
   * Get dataPubblicazioneAvvisoA
   * @return dataPubblicazioneAvvisoA
  **/
  @XmlElement(name="dataPubblicazioneAvvisoA")
  public String getDataPubblicazioneAvvisoA() {
    return dataPubblicazioneAvvisoA;
  }

  public void setDataPubblicazioneAvvisoA(String dataPubblicazioneAvvisoA) {
    this.dataPubblicazioneAvvisoA = dataPubblicazioneAvvisoA;
  }

  public ParametriRicercaDto dataPubblicazioneAvvisoA(String dataPubblicazioneAvvisoA) {
    this.dataPubblicazioneAvvisoA = dataPubblicazioneAvvisoA;
    return this;
  }

 /**
   * Get dataPubblicazioneAvvisoDa
   * @return dataPubblicazioneAvvisoDa
  **/
  @XmlElement(name="dataPubblicazioneAvvisoDa")
  public String getDataPubblicazioneAvvisoDa() {
    return dataPubblicazioneAvvisoDa;
  }

  public void setDataPubblicazioneAvvisoDa(String dataPubblicazioneAvvisoDa) {
    this.dataPubblicazioneAvvisoDa = dataPubblicazioneAvvisoDa;
  }

  public ParametriRicercaDto dataPubblicazioneAvvisoDa(String dataPubblicazioneAvvisoDa) {
    this.dataPubblicazioneAvvisoDa = dataPubblicazioneAvvisoDa;
    return this;
  }

 /**
   * Get dataScadenzaA
   * @return dataScadenzaA
  **/
  @XmlElement(name="dataScadenzaA")
  public String getDataScadenzaA() {
    return dataScadenzaA;
  }

  public void setDataScadenzaA(String dataScadenzaA) {
    this.dataScadenzaA = dataScadenzaA;
  }

  public ParametriRicercaDto dataScadenzaA(String dataScadenzaA) {
    this.dataScadenzaA = dataScadenzaA;
    return this;
  }

 /**
   * Get dataScadenzaAvvisoA
   * @return dataScadenzaAvvisoA
  **/
  @XmlElement(name="dataScadenzaAvvisoA")
  public String getDataScadenzaAvvisoA() {
    return dataScadenzaAvvisoA;
  }

  public void setDataScadenzaAvvisoA(String dataScadenzaAvvisoA) {
    this.dataScadenzaAvvisoA = dataScadenzaAvvisoA;
  }

  public ParametriRicercaDto dataScadenzaAvvisoA(String dataScadenzaAvvisoA) {
    this.dataScadenzaAvvisoA = dataScadenzaAvvisoA;
    return this;
  }

 /**
   * Get dataScadenzaAvvisoDa
   * @return dataScadenzaAvvisoDa
  **/
  @XmlElement(name="dataScadenzaAvvisoDa")
  public String getDataScadenzaAvvisoDa() {
    return dataScadenzaAvvisoDa;
  }

  public void setDataScadenzaAvvisoDa(String dataScadenzaAvvisoDa) {
    this.dataScadenzaAvvisoDa = dataScadenzaAvvisoDa;
  }

  public ParametriRicercaDto dataScadenzaAvvisoDa(String dataScadenzaAvvisoDa) {
    this.dataScadenzaAvvisoDa = dataScadenzaAvvisoDa;
    return this;
  }

 /**
   * Get dataScadenzaDa
   * @return dataScadenzaDa
  **/
  @XmlElement(name="dataScadenzaDa")
  public String getDataScadenzaDa() {
    return dataScadenzaDa;
  }

  public void setDataScadenzaDa(String dataScadenzaDa) {
    this.dataScadenzaDa = dataScadenzaDa;
  }

  public ParametriRicercaDto dataScadenzaDa(String dataScadenzaDa) {
    this.dataScadenzaDa = dataScadenzaDa;
    return this;
  }

 /**
   * Get dataTransazioneA
   * @return dataTransazioneA
  **/
  @XmlElement(name="dataTransazioneA")
  public String getDataTransazioneA() {
    return dataTransazioneA;
  }

  public void setDataTransazioneA(String dataTransazioneA) {
    this.dataTransazioneA = dataTransazioneA;
  }

  public ParametriRicercaDto dataTransazioneA(String dataTransazioneA) {
    this.dataTransazioneA = dataTransazioneA;
    return this;
  }

 /**
   * Get dataTransazioneDa
   * @return dataTransazioneDa
  **/
  @XmlElement(name="dataTransazioneDa")
  public String getDataTransazioneDa() {
    return dataTransazioneDa;
  }

  public void setDataTransazioneDa(String dataTransazioneDa) {
    this.dataTransazioneDa = dataTransazioneDa;
  }

  public ParametriRicercaDto dataTransazioneDa(String dataTransazioneDa) {
    this.dataTransazioneDa = dataTransazioneDa;
    return this;
  }

 /**
   * Get denominazione
   * @return denominazione
  **/
  @XmlElement(name="denominazione")
  public String getDenominazione() {
    return denominazione;
  }

  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  public ParametriRicercaDto denominazione(String denominazione) {
    this.denominazione = denominazione;
    return this;
  }

 /**
   * Get esitoInvio
   * @return esitoInvio
  **/
  @XmlElement(name="esitoInvio")
  public String getEsitoInvio() {
    return esitoInvio;
  }

  public void setEsitoInvio(String esitoInvio) {
    this.esitoInvio = esitoInvio;
  }

  public ParametriRicercaDto esitoInvio(String esitoInvio) {
    this.esitoInvio = esitoInvio;
    return this;
  }

 /**
   * Get esitoTransazione
   * @return esitoTransazione
  **/
  @XmlElement(name="esitoTransazione")
  public String getEsitoTransazione() {
    return esitoTransazione;
  }

  public void setEsitoTransazione(String esitoTransazione) {
    this.esitoTransazione = esitoTransazione;
  }

  public ParametriRicercaDto esitoTransazione(String esitoTransazione) {
    this.esitoTransazione = esitoTransazione;
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

  public ParametriRicercaDto idDebito(String idDebito) {
    this.idDebito = idDebito;
    return this;
  }

 /**
   * Get idEnte
   * @return idEnte
  **/
  @XmlElement(name="idEnte")
  public Long getIdEnte() {
    return idEnte;
  }

  public void setIdEnte(Long idEnte) {
    this.idEnte = idEnte;
  }

  public ParametriRicercaDto idEnte(Long idEnte) {
    this.idEnte = idEnte;
    return this;
  }

 /**
   * Get idEnteString
   * @return idEnteString
  **/
  @XmlElement(name="idEnteString")
  public String getIdEnteString() {
    return idEnteString;
  }

  public void setIdEnteString(String idEnteString) {
    this.idEnteString = idEnteString;
  }

  public ParametriRicercaDto idEnteString(String idEnteString) {
    this.idEnteString = idEnteString;
    return this;
  }

 /**
   * Get idRichiesta
   * @return idRichiesta
  **/
  @XmlElement(name="idRichiesta")
  public String getIdRichiesta() {
    return idRichiesta;
  }

  public void setIdRichiesta(String idRichiesta) {
    this.idRichiesta = idRichiesta;
  }

  public ParametriRicercaDto idRichiesta(String idRichiesta) {
    this.idRichiesta = idRichiesta;
    return this;
  }

 /**
   * Get idServizio
   * @return idServizio
  **/
  @XmlElement(name="idServizio")
  public Long getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(Long idServizio) {
    this.idServizio = idServizio;
  }

  public ParametriRicercaDto idServizio(Long idServizio) {
    this.idServizio = idServizio;
    return this;
  }

 /**
   * Get idServizioString
   * @return idServizioString
  **/
  @XmlElement(name="idServizioString")
  public String getIdServizioString() {
    return idServizioString;
  }

  public void setIdServizioString(String idServizioString) {
    this.idServizioString = idServizioString;
  }

  public ParametriRicercaDto idServizioString(String idServizioString) {
    this.idServizioString = idServizioString;
    return this;
  }

 /**
   * Get idTrasmissione
   * @return idTrasmissione
  **/
  @XmlElement(name="idTrasmissione")
  public String getIdTrasmissione() {
    return idTrasmissione;
  }

  public void setIdTrasmissione(String idTrasmissione) {
    this.idTrasmissione = idTrasmissione;
  }

  public ParametriRicercaDto idTrasmissione(String idTrasmissione) {
    this.idTrasmissione = idTrasmissione;
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

  public ParametriRicercaDto iuv(String iuv) {
    this.iuv = iuv;
    return this;
  }

 /**
   * Get limit
   * @return limit
  **/
  @XmlElement(name="limit")
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public ParametriRicercaDto limit(Integer limit) {
    this.limit = limit;
    return this;
  }

 /**
   * Get offSet
   * @return offSet
  **/
  @XmlElement(name="offSet")
  public Integer getOffSet() {
    return offSet;
  }

  public void setOffSet(Integer offSet) {
    this.offSet = offSet;
  }

  public ParametriRicercaDto offSet(Integer offSet) {
    this.offSet = offSet;
    return this;
  }

 /**
   * Get provenienzaRevoca
   * @return provenienzaRevoca
  **/
  @XmlElement(name="provenienzaRevoca")
  public String getProvenienzaRevoca() {
    return provenienzaRevoca;
  }

  public void setProvenienzaRevoca(String provenienzaRevoca) {
    this.provenienzaRevoca = provenienzaRevoca;
  }

  public ParametriRicercaDto provenienzaRevoca(String provenienzaRevoca) {
    this.provenienzaRevoca = provenienzaRevoca;
    return this;
  }

 /**
   * Get stato
   * @return stato
  **/
  @XmlElement(name="stato")
  public String getStato() {
    return stato;
  }

  public void setStato(String stato) {
    this.stato = stato;
  }

  public ParametriRicercaDto stato(String stato) {
    this.stato = stato;
    return this;
  }

 /**
   * Get statoNotificaRevoca
   * @return statoNotificaRevoca
  **/
  @XmlElement(name="statoNotificaRevoca")
  public Integer getStatoNotificaRevoca() {
    return statoNotificaRevoca;
  }

  public void setStatoNotificaRevoca(Integer statoNotificaRevoca) {
    this.statoNotificaRevoca = statoNotificaRevoca;
  }

  public ParametriRicercaDto statoNotificaRevoca(Integer statoNotificaRevoca) {
    this.statoNotificaRevoca = statoNotificaRevoca;
    return this;
  }

 /**
   * Get statoRichiesta
   * @return statoRichiesta
  **/
  @XmlElement(name="statoRichiesta")
  public String getStatoRichiesta() {
    return statoRichiesta;
  }

  public void setStatoRichiesta(String statoRichiesta) {
    this.statoRichiesta = statoRichiesta;
  }

  public ParametriRicercaDto statoRichiesta(String statoRichiesta) {
    this.statoRichiesta = statoRichiesta;
    return this;
  }

 /**
   * Get tipoRevoca
   * @return tipoRevoca
  **/
  @XmlElement(name="tipoRevoca")
  public Integer getTipoRevoca() {
    return tipoRevoca;
  }

  public void setTipoRevoca(Integer tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
  }

  public ParametriRicercaDto tipoRevoca(Integer tipoRevoca) {
    this.tipoRevoca = tipoRevoca;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParametriRicercaDto {\n");
    
    sb.append("    cercaPendenzeConRevocaCreataDallEnte: ").append(toIndentedString(cercaPendenzeConRevocaCreataDallEnte)).append("\n");
    sb.append("    cercaPendenzeRevocate: ").append(toIndentedString(cercaPendenzeRevocate)).append("\n");
    sb.append("    codFisc: ").append(toIndentedString(codFisc)).append("\n");
    sb.append("    dataCreazioneA: ").append(toIndentedString(dataCreazioneA)).append("\n");
    sb.append("    dataCreazioneDa: ").append(toIndentedString(dataCreazioneDa)).append("\n");
    sb.append("    dataPubblicazioneAvvisoA: ").append(toIndentedString(dataPubblicazioneAvvisoA)).append("\n");
    sb.append("    dataPubblicazioneAvvisoDa: ").append(toIndentedString(dataPubblicazioneAvvisoDa)).append("\n");
    sb.append("    dataScadenzaA: ").append(toIndentedString(dataScadenzaA)).append("\n");
    sb.append("    dataScadenzaAvvisoA: ").append(toIndentedString(dataScadenzaAvvisoA)).append("\n");
    sb.append("    dataScadenzaAvvisoDa: ").append(toIndentedString(dataScadenzaAvvisoDa)).append("\n");
    sb.append("    dataScadenzaDa: ").append(toIndentedString(dataScadenzaDa)).append("\n");
    sb.append("    dataTransazioneA: ").append(toIndentedString(dataTransazioneA)).append("\n");
    sb.append("    dataTransazioneDa: ").append(toIndentedString(dataTransazioneDa)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    esitoInvio: ").append(toIndentedString(esitoInvio)).append("\n");
    sb.append("    esitoTransazione: ").append(toIndentedString(esitoTransazione)).append("\n");
    sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
    sb.append("    idEnte: ").append(toIndentedString(idEnte)).append("\n");
    sb.append("    idEnteString: ").append(toIndentedString(idEnteString)).append("\n");
    sb.append("    idRichiesta: ").append(toIndentedString(idRichiesta)).append("\n");
    sb.append("    idServizio: ").append(toIndentedString(idServizio)).append("\n");
    sb.append("    idServizioString: ").append(toIndentedString(idServizioString)).append("\n");
    sb.append("    idTrasmissione: ").append(toIndentedString(idTrasmissione)).append("\n");
    sb.append("    iuv: ").append(toIndentedString(iuv)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    offSet: ").append(toIndentedString(offSet)).append("\n");
    sb.append("    provenienzaRevoca: ").append(toIndentedString(provenienzaRevoca)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    statoNotificaRevoca: ").append(toIndentedString(statoNotificaRevoca)).append("\n");
    sb.append("    statoRichiesta: ").append(toIndentedString(statoRichiesta)).append("\n");
    sb.append("    tipoRevoca: ").append(toIndentedString(tipoRevoca)).append("\n");
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

