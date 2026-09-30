package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DatiPendenzaDto  {
  
  
  private Long annoRiferimento = null;

  
  private String causalePagamento = null;

  
  private String codEnte = null;

  
  private String codEnteServizio = null;

  
  private String codiceAvviso = null;

  
  private String codiceContabilita = null;

  
  private String codiceErroreRichiesta = null;

  
  private Long cuspiPadre = null;

  
  private Date dataCreazione = null;

  
  private Date dataCreazioneAvvisoPagamento = null;

  
  private Date dataFineValidita = null;

  
  private Date dataInizioValidita = null;

  
  private Date dataPubblicazioneAvviso = null;

  
  private Date dataScadenza = null;

  
  private Date dataScadenzaAvviso = null;

  
  private Date dataTransazione = null;

  
  private byte[] datiAggiuntivi = null;

  
  private byte[] datiContabili = null;

  
  private String descrizioneErroreRichiesta = null;

  
  private String descrizionePagamento = null;

  
  private String esitoInvio = null;

  
  private String esitoRata = null;

  
  private String esitoTransazione = null;

  
  private String idDebito = null;

  
  private Long idDominio = null;

  
  private String idIuv = null;

  
  private String idRichiesta = null;

  
  private Long idRegPendenza = null;

  
  private Double importo = null;

  
  private Double importoPiuCommissione = null;

  
  private String importoRata = null;

  
  private String inoltroModalitaTelematica = null;

  
  private Boolean isRata = null;

  
  private String iuvRata = null;

  
  private Boolean pagamentoModello1 = null;

  
  private Boolean pagamentoModello3 = null;

  
  private Boolean rateizzato = null;

  
  private List<RichiestaRevoca> richiestaRevoca = null;

  
  private List<StorniDto> richiestaStorno = null;

  
  private String riferim1 = null;

  
  private String riferim2 = null;

  
  private String riferim3 = null;

  
  private String riferim4 = null;

  
  private String riferim5 = null;

  
  private String riferim6 = null;

  
  private String riferim7 = null;

  
  private String riferim8 = null;

  
  private String stato = null;

  
  private String statoRichiesta = null;

  
  private String tipoContabilita = null;

  
  private String tipoServizio = null;

  
  private List<TblVociPagamento> vociPagamento = null;
 /**
   * Get annoRiferimento
   * @return annoRiferimento
  **/
  @XmlElement(name="annoRiferimento")
  public Long getAnnoRiferimento() {
    return annoRiferimento;
  }

  public void setAnnoRiferimento(Long annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
  }

  public DatiPendenzaDto annoRiferimento(Long annoRiferimento) {
    this.annoRiferimento = annoRiferimento;
    return this;
  }

 /**
   * Get causalePagamento
   * @return causalePagamento
  **/
  @XmlElement(name="causalePagamento")
  public String getCausalePagamento() {
    return causalePagamento;
  }

  public void setCausalePagamento(String causalePagamento) {
    this.causalePagamento = causalePagamento;
  }

  public DatiPendenzaDto causalePagamento(String causalePagamento) {
    this.causalePagamento = causalePagamento;
    return this;
  }

 /**
   * Get codEnte
   * @return codEnte
  **/
  @XmlElement(name="codEnte")
  public String getCodEnte() {
    return codEnte;
  }

  public void setCodEnte(String codEnte) {
    this.codEnte = codEnte;
  }

  public DatiPendenzaDto codEnte(String codEnte) {
    this.codEnte = codEnte;
    return this;
  }

 /**
   * Get codEnteServizio
   * @return codEnteServizio
  **/
  @XmlElement(name="codEnteServizio")
  public String getCodEnteServizio() {
    return codEnteServizio;
  }

  public void setCodEnteServizio(String codEnteServizio) {
    this.codEnteServizio = codEnteServizio;
  }

  public DatiPendenzaDto codEnteServizio(String codEnteServizio) {
    this.codEnteServizio = codEnteServizio;
    return this;
  }

 /**
   * Get codiceAvviso
   * @return codiceAvviso
  **/
  @XmlElement(name="codiceAvviso")
  public String getCodiceAvviso() {
    return codiceAvviso;
  }

  public void setCodiceAvviso(String codiceAvviso) {
    this.codiceAvviso = codiceAvviso;
  }

  public DatiPendenzaDto codiceAvviso(String codiceAvviso) {
    this.codiceAvviso = codiceAvviso;
    return this;
  }

 /**
   * Get codiceContabilita
   * @return codiceContabilita
  **/
  @XmlElement(name="codiceContabilita")
  public String getCodiceContabilita() {
    return codiceContabilita;
  }

  public void setCodiceContabilita(String codiceContabilita) {
    this.codiceContabilita = codiceContabilita;
  }

  public DatiPendenzaDto codiceContabilita(String codiceContabilita) {
    this.codiceContabilita = codiceContabilita;
    return this;
  }

 /**
   * Get codiceErroreRichiesta
   * @return codiceErroreRichiesta
  **/
  @XmlElement(name="codiceErroreRichiesta")
  public String getCodiceErroreRichiesta() {
    return codiceErroreRichiesta;
  }

  public void setCodiceErroreRichiesta(String codiceErroreRichiesta) {
    this.codiceErroreRichiesta = codiceErroreRichiesta;
  }

  public DatiPendenzaDto codiceErroreRichiesta(String codiceErroreRichiesta) {
    this.codiceErroreRichiesta = codiceErroreRichiesta;
    return this;
  }

 /**
   * Get cuspiPadre
   * @return cuspiPadre
  **/
  @XmlElement(name="cuspiPadre")
  public Long getCuspiPadre() {
    return cuspiPadre;
  }

  public void setCuspiPadre(Long cuspiPadre) {
    this.cuspiPadre = cuspiPadre;
  }

  public DatiPendenzaDto cuspiPadre(Long cuspiPadre) {
    this.cuspiPadre = cuspiPadre;
    return this;
  }

 /**
   * Get dataCreazione
   * @return dataCreazione
  **/
  @XmlElement(name="dataCreazione")
  public Date getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public DatiPendenzaDto dataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
    return this;
  }

 /**
   * Get dataCreazioneAvvisoPagamento
   * @return dataCreazioneAvvisoPagamento
  **/
  @XmlElement(name="dataCreazioneAvvisoPagamento")
  public Date getDataCreazioneAvvisoPagamento() {
    return dataCreazioneAvvisoPagamento;
  }

  public void setDataCreazioneAvvisoPagamento(Date dataCreazioneAvvisoPagamento) {
    this.dataCreazioneAvvisoPagamento = dataCreazioneAvvisoPagamento;
  }

  public DatiPendenzaDto dataCreazioneAvvisoPagamento(Date dataCreazioneAvvisoPagamento) {
    this.dataCreazioneAvvisoPagamento = dataCreazioneAvvisoPagamento;
    return this;
  }

 /**
   * Get dataFineValidita
   * @return dataFineValidita
  **/
  @XmlElement(name="dataFineValidita")
  public Date getDataFineValidita() {
    return dataFineValidita;
  }

  public void setDataFineValidita(Date dataFineValidita) {
    this.dataFineValidita = dataFineValidita;
  }

  public DatiPendenzaDto dataFineValidita(Date dataFineValidita) {
    this.dataFineValidita = dataFineValidita;
    return this;
  }

 /**
   * Get dataInizioValidita
   * @return dataInizioValidita
  **/
  @XmlElement(name="dataInizioValidita")
  public Date getDataInizioValidita() {
    return dataInizioValidita;
  }

  public void setDataInizioValidita(Date dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
  }

  public DatiPendenzaDto dataInizioValidita(Date dataInizioValidita) {
    this.dataInizioValidita = dataInizioValidita;
    return this;
  }

 /**
   * Get dataPubblicazioneAvviso
   * @return dataPubblicazioneAvviso
  **/
  @XmlElement(name="dataPubblicazioneAvviso")
  public Date getDataPubblicazioneAvviso() {
    return dataPubblicazioneAvviso;
  }

  public void setDataPubblicazioneAvviso(Date dataPubblicazioneAvviso) {
    this.dataPubblicazioneAvviso = dataPubblicazioneAvviso;
  }

  public DatiPendenzaDto dataPubblicazioneAvviso(Date dataPubblicazioneAvviso) {
    this.dataPubblicazioneAvviso = dataPubblicazioneAvviso;
    return this;
  }

 /**
   * Get dataScadenza
   * @return dataScadenza
  **/
  @XmlElement(name="dataScadenza")
  public Date getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(Date dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public DatiPendenzaDto dataScadenza(Date dataScadenza) {
    this.dataScadenza = dataScadenza;
    return this;
  }

 /**
   * Get dataScadenzaAvviso
   * @return dataScadenzaAvviso
  **/
  @XmlElement(name="dataScadenzaAvviso")
  public Date getDataScadenzaAvviso() {
    return dataScadenzaAvviso;
  }

  public void setDataScadenzaAvviso(Date dataScadenzaAvviso) {
    this.dataScadenzaAvviso = dataScadenzaAvviso;
  }

  public DatiPendenzaDto dataScadenzaAvviso(Date dataScadenzaAvviso) {
    this.dataScadenzaAvviso = dataScadenzaAvviso;
    return this;
  }

 /**
   * Get dataTransazione
   * @return dataTransazione
  **/
  @XmlElement(name="dataTransazione")
  public Date getDataTransazione() {
    return dataTransazione;
  }

  public void setDataTransazione(Date dataTransazione) {
    this.dataTransazione = dataTransazione;
  }

  public DatiPendenzaDto dataTransazione(Date dataTransazione) {
    this.dataTransazione = dataTransazione;
    return this;
  }

 /**
   * Get datiAggiuntivi
   * @return datiAggiuntivi
  **/
  @XmlElement(name="datiAggiuntivi")
  public byte[] getDatiAggiuntivi() {
    return datiAggiuntivi;
  }

  public void setDatiAggiuntivi(byte[] datiAggiuntivi) {
    this.datiAggiuntivi = datiAggiuntivi;
  }

  public DatiPendenzaDto datiAggiuntivi(byte[] datiAggiuntivi) {
    this.datiAggiuntivi = datiAggiuntivi;
    return this;
  }

 /**
   * Get datiContabili
   * @return datiContabili
  **/
  @XmlElement(name="datiContabili")
  public byte[] getDatiContabili() {
    return datiContabili;
  }

  public void setDatiContabili(byte[] datiContabili) {
    this.datiContabili = datiContabili;
  }

  public DatiPendenzaDto datiContabili(byte[] datiContabili) {
    this.datiContabili = datiContabili;
    return this;
  }

 /**
   * Get descrizioneErroreRichiesta
   * @return descrizioneErroreRichiesta
  **/
  @XmlElement(name="descrizioneErroreRichiesta")
  public String getDescrizioneErroreRichiesta() {
    return descrizioneErroreRichiesta;
  }

  public void setDescrizioneErroreRichiesta(String descrizioneErroreRichiesta) {
    this.descrizioneErroreRichiesta = descrizioneErroreRichiesta;
  }

  public DatiPendenzaDto descrizioneErroreRichiesta(String descrizioneErroreRichiesta) {
    this.descrizioneErroreRichiesta = descrizioneErroreRichiesta;
    return this;
  }

 /**
   * Get descrizionePagamento
   * @return descrizionePagamento
  **/
  @XmlElement(name="descrizionePagamento")
  public String getDescrizionePagamento() {
    return descrizionePagamento;
  }

  public void setDescrizionePagamento(String descrizionePagamento) {
    this.descrizionePagamento = descrizionePagamento;
  }

  public DatiPendenzaDto descrizionePagamento(String descrizionePagamento) {
    this.descrizionePagamento = descrizionePagamento;
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

  public DatiPendenzaDto esitoInvio(String esitoInvio) {
    this.esitoInvio = esitoInvio;
    return this;
  }

 /**
   * Get esitoRata
   * @return esitoRata
  **/
  @XmlElement(name="esitoRata")
  public String getEsitoRata() {
    return esitoRata;
  }

  public void setEsitoRata(String esitoRata) {
    this.esitoRata = esitoRata;
  }

  public DatiPendenzaDto esitoRata(String esitoRata) {
    this.esitoRata = esitoRata;
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

  public DatiPendenzaDto esitoTransazione(String esitoTransazione) {
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

  public DatiPendenzaDto idDebito(String idDebito) {
    this.idDebito = idDebito;
    return this;
  }

 /**
   * Get idDominio
   * @return idDominio
  **/
  @XmlElement(name="idDominio")
  public Long getIdDominio() {
    return idDominio;
  }

  public void setIdDominio(Long idDominio) {
    this.idDominio = idDominio;
  }

  public DatiPendenzaDto idDominio(Long idDominio) {
    this.idDominio = idDominio;
    return this;
  }

 /**
   * Get idIuv
   * @return idIuv
  **/
  @XmlElement(name="idIuv")
  public String getIdIuv() {
    return idIuv;
  }

  public void setIdIuv(String idIuv) {
    this.idIuv = idIuv;
  }

  public DatiPendenzaDto idIuv(String idIuv) {
    this.idIuv = idIuv;
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

  public DatiPendenzaDto idRichiesta(String idRichiesta) {
    this.idRichiesta = idRichiesta;
    return this;
  }

 /**
   * Get idRegPendenza
   * @return idRegPendenza
  **/
  @XmlElement(name="id_reg_pendenza")
  public Long getIdRegPendenza() {
    return idRegPendenza;
  }

  public void setIdRegPendenza(Long idRegPendenza) {
    this.idRegPendenza = idRegPendenza;
  }

  public DatiPendenzaDto idRegPendenza(Long idRegPendenza) {
    this.idRegPendenza = idRegPendenza;
    return this;
  }

 /**
   * Get importo
   * @return importo
  **/
  @XmlElement(name="importo")
  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double importo) {
    this.importo = importo;
  }

  public DatiPendenzaDto importo(Double importo) {
    this.importo = importo;
    return this;
  }

 /**
   * Get importoPiuCommissione
   * @return importoPiuCommissione
  **/
  @XmlElement(name="importoPiuCommissione")
  public Double getImportoPiuCommissione() {
    return importoPiuCommissione;
  }

  public void setImportoPiuCommissione(Double importoPiuCommissione) {
    this.importoPiuCommissione = importoPiuCommissione;
  }

  public DatiPendenzaDto importoPiuCommissione(Double importoPiuCommissione) {
    this.importoPiuCommissione = importoPiuCommissione;
    return this;
  }

 /**
   * Get importoRata
   * @return importoRata
  **/
  @XmlElement(name="importoRata")
  public String getImportoRata() {
    return importoRata;
  }

  public void setImportoRata(String importoRata) {
    this.importoRata = importoRata;
  }

  public DatiPendenzaDto importoRata(String importoRata) {
    this.importoRata = importoRata;
    return this;
  }

 /**
   * Get inoltroModalitaTelematica
   * @return inoltroModalitaTelematica
  **/
  @XmlElement(name="inoltroModalitaTelematica")
  public String getInoltroModalitaTelematica() {
    return inoltroModalitaTelematica;
  }

  public void setInoltroModalitaTelematica(String inoltroModalitaTelematica) {
    this.inoltroModalitaTelematica = inoltroModalitaTelematica;
  }

  public DatiPendenzaDto inoltroModalitaTelematica(String inoltroModalitaTelematica) {
    this.inoltroModalitaTelematica = inoltroModalitaTelematica;
    return this;
  }

 /**
   * Get isRata
   * @return isRata
  **/
  @XmlElement(name="isRata")
  public Boolean isIsRata() {
    return isRata;
  }

  public void setIsRata(Boolean isRata) {
    this.isRata = isRata;
  }

  public DatiPendenzaDto isRata(Boolean isRata) {
    this.isRata = isRata;
    return this;
  }

 /**
   * Get iuvRata
   * @return iuvRata
  **/
  @XmlElement(name="iuvRata")
  public String getIuvRata() {
    return iuvRata;
  }

  public void setIuvRata(String iuvRata) {
    this.iuvRata = iuvRata;
  }

  public DatiPendenzaDto iuvRata(String iuvRata) {
    this.iuvRata = iuvRata;
    return this;
  }

 /**
   * Get pagamentoModello1
   * @return pagamentoModello1
  **/
  @XmlElement(name="pagamentoModello1")
  public Boolean isPagamentoModello1() {
    return pagamentoModello1;
  }

  public void setPagamentoModello1(Boolean pagamentoModello1) {
    this.pagamentoModello1 = pagamentoModello1;
  }

  public DatiPendenzaDto pagamentoModello1(Boolean pagamentoModello1) {
    this.pagamentoModello1 = pagamentoModello1;
    return this;
  }

 /**
   * Get pagamentoModello3
   * @return pagamentoModello3
  **/
  @XmlElement(name="pagamentoModello3")
  public Boolean isPagamentoModello3() {
    return pagamentoModello3;
  }

  public void setPagamentoModello3(Boolean pagamentoModello3) {
    this.pagamentoModello3 = pagamentoModello3;
  }

  public DatiPendenzaDto pagamentoModello3(Boolean pagamentoModello3) {
    this.pagamentoModello3 = pagamentoModello3;
    return this;
  }

 /**
   * Get rateizzato
   * @return rateizzato
  **/
  @XmlElement(name="rateizzato")
  public Boolean isRateizzato() {
    return rateizzato;
  }

  public void setRateizzato(Boolean rateizzato) {
    this.rateizzato = rateizzato;
  }

  public DatiPendenzaDto rateizzato(Boolean rateizzato) {
    this.rateizzato = rateizzato;
    return this;
  }

 /**
   * Get richiestaRevoca
   * @return richiestaRevoca
  **/
  @XmlElement(name="richiestaRevoca")
  public List<RichiestaRevoca> getRichiestaRevoca() {
    return richiestaRevoca;
  }

  public void setRichiestaRevoca(List<RichiestaRevoca> richiestaRevoca) {
    this.richiestaRevoca = richiestaRevoca;
  }

  public DatiPendenzaDto richiestaRevoca(List<RichiestaRevoca> richiestaRevoca) {
    this.richiestaRevoca = richiestaRevoca;
    return this;
  }

  public DatiPendenzaDto addRichiestaRevocaItem(RichiestaRevoca richiestaRevocaItem) {
    this.richiestaRevoca.add(richiestaRevocaItem);
    return this;
  }

 /**
   * Get richiestaStorno
   * @return richiestaStorno
  **/
  @XmlElement(name="richiestaStorno")
  public List<StorniDto> getRichiestaStorno() {
    return richiestaStorno;
  }

  public void setRichiestaStorno(List<StorniDto> richiestaStorno) {
    this.richiestaStorno = richiestaStorno;
  }

  public DatiPendenzaDto richiestaStorno(List<StorniDto> richiestaStorno) {
    this.richiestaStorno = richiestaStorno;
    return this;
  }

  public DatiPendenzaDto addRichiestaStornoItem(StorniDto richiestaStornoItem) {
    this.richiestaStorno.add(richiestaStornoItem);
    return this;
  }

 /**
   * Get riferim1
   * @return riferim1
  **/
  @XmlElement(name="riferim1")
  public String getRiferim1() {
    return riferim1;
  }

  public void setRiferim1(String riferim1) {
    this.riferim1 = riferim1;
  }

  public DatiPendenzaDto riferim1(String riferim1) {
    this.riferim1 = riferim1;
    return this;
  }

 /**
   * Get riferim2
   * @return riferim2
  **/
  @XmlElement(name="riferim2")
  public String getRiferim2() {
    return riferim2;
  }

  public void setRiferim2(String riferim2) {
    this.riferim2 = riferim2;
  }

  public DatiPendenzaDto riferim2(String riferim2) {
    this.riferim2 = riferim2;
    return this;
  }

 /**
   * Get riferim3
   * @return riferim3
  **/
  @XmlElement(name="riferim3")
  public String getRiferim3() {
    return riferim3;
  }

  public void setRiferim3(String riferim3) {
    this.riferim3 = riferim3;
  }

  public DatiPendenzaDto riferim3(String riferim3) {
    this.riferim3 = riferim3;
    return this;
  }

 /**
   * Get riferim4
   * @return riferim4
  **/
  @XmlElement(name="riferim4")
  public String getRiferim4() {
    return riferim4;
  }

  public void setRiferim4(String riferim4) {
    this.riferim4 = riferim4;
  }

  public DatiPendenzaDto riferim4(String riferim4) {
    this.riferim4 = riferim4;
    return this;
  }

 /**
   * Get riferim5
   * @return riferim5
  **/
  @XmlElement(name="riferim5")
  public String getRiferim5() {
    return riferim5;
  }

  public void setRiferim5(String riferim5) {
    this.riferim5 = riferim5;
  }

  public DatiPendenzaDto riferim5(String riferim5) {
    this.riferim5 = riferim5;
    return this;
  }

 /**
   * Get riferim6
   * @return riferim6
  **/
  @XmlElement(name="riferim6")
  public String getRiferim6() {
    return riferim6;
  }

  public void setRiferim6(String riferim6) {
    this.riferim6 = riferim6;
  }

  public DatiPendenzaDto riferim6(String riferim6) {
    this.riferim6 = riferim6;
    return this;
  }

 /**
   * Get riferim7
   * @return riferim7
  **/
  @XmlElement(name="riferim7")
  public String getRiferim7() {
    return riferim7;
  }

  public void setRiferim7(String riferim7) {
    this.riferim7 = riferim7;
  }

  public DatiPendenzaDto riferim7(String riferim7) {
    this.riferim7 = riferim7;
    return this;
  }

 /**
   * Get riferim8
   * @return riferim8
  **/
  @XmlElement(name="riferim8")
  public String getRiferim8() {
    return riferim8;
  }

  public void setRiferim8(String riferim8) {
    this.riferim8 = riferim8;
  }

  public DatiPendenzaDto riferim8(String riferim8) {
    this.riferim8 = riferim8;
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

  public DatiPendenzaDto stato(String stato) {
    this.stato = stato;
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

  public DatiPendenzaDto statoRichiesta(String statoRichiesta) {
    this.statoRichiesta = statoRichiesta;
    return this;
  }

 /**
   * Get tipoContabilita
   * @return tipoContabilita
  **/
  @XmlElement(name="tipoContabilita")
  public String getTipoContabilita() {
    return tipoContabilita;
  }

  public void setTipoContabilita(String tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
  }

  public DatiPendenzaDto tipoContabilita(String tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
    return this;
  }

 /**
   * Get tipoServizio
   * @return tipoServizio
  **/
  @XmlElement(name="tipoServizio")
  public String getTipoServizio() {
    return tipoServizio;
  }

  public void setTipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
  }

  public DatiPendenzaDto tipoServizio(String tipoServizio) {
    this.tipoServizio = tipoServizio;
    return this;
  }

 /**
   * Get vociPagamento
   * @return vociPagamento
  **/
  @XmlElement(name="vociPagamento")
  public List<TblVociPagamento> getVociPagamento() {
    return vociPagamento;
  }

  public void setVociPagamento(List<TblVociPagamento> vociPagamento) {
    this.vociPagamento = vociPagamento;
  }

  public DatiPendenzaDto vociPagamento(List<TblVociPagamento> vociPagamento) {
    this.vociPagamento = vociPagamento;
    return this;
  }

  public DatiPendenzaDto addVociPagamentoItem(TblVociPagamento vociPagamentoItem) {
    this.vociPagamento.add(vociPagamentoItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatiPendenzaDto {\n");
    
    sb.append("    annoRiferimento: ").append(toIndentedString(annoRiferimento)).append("\n");
    sb.append("    causalePagamento: ").append(toIndentedString(causalePagamento)).append("\n");
    sb.append("    codEnte: ").append(toIndentedString(codEnte)).append("\n");
    sb.append("    codEnteServizio: ").append(toIndentedString(codEnteServizio)).append("\n");
    sb.append("    codiceAvviso: ").append(toIndentedString(codiceAvviso)).append("\n");
    sb.append("    codiceContabilita: ").append(toIndentedString(codiceContabilita)).append("\n");
    sb.append("    codiceErroreRichiesta: ").append(toIndentedString(codiceErroreRichiesta)).append("\n");
    sb.append("    cuspiPadre: ").append(toIndentedString(cuspiPadre)).append("\n");
    sb.append("    dataCreazione: ").append(toIndentedString(dataCreazione)).append("\n");
    sb.append("    dataCreazioneAvvisoPagamento: ").append(toIndentedString(dataCreazioneAvvisoPagamento)).append("\n");
    sb.append("    dataFineValidita: ").append(toIndentedString(dataFineValidita)).append("\n");
    sb.append("    dataInizioValidita: ").append(toIndentedString(dataInizioValidita)).append("\n");
    sb.append("    dataPubblicazioneAvviso: ").append(toIndentedString(dataPubblicazioneAvviso)).append("\n");
    sb.append("    dataScadenza: ").append(toIndentedString(dataScadenza)).append("\n");
    sb.append("    dataScadenzaAvviso: ").append(toIndentedString(dataScadenzaAvviso)).append("\n");
    sb.append("    dataTransazione: ").append(toIndentedString(dataTransazione)).append("\n");
    sb.append("    datiAggiuntivi: ").append(toIndentedString(datiAggiuntivi)).append("\n");
    sb.append("    datiContabili: ").append(toIndentedString(datiContabili)).append("\n");
    sb.append("    descrizioneErroreRichiesta: ").append(toIndentedString(descrizioneErroreRichiesta)).append("\n");
    sb.append("    descrizionePagamento: ").append(toIndentedString(descrizionePagamento)).append("\n");
    sb.append("    esitoInvio: ").append(toIndentedString(esitoInvio)).append("\n");
    sb.append("    esitoRata: ").append(toIndentedString(esitoRata)).append("\n");
    sb.append("    esitoTransazione: ").append(toIndentedString(esitoTransazione)).append("\n");
    sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
    sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
    sb.append("    idIuv: ").append(toIndentedString(idIuv)).append("\n");
    sb.append("    idRichiesta: ").append(toIndentedString(idRichiesta)).append("\n");
    sb.append("    idRegPendenza: ").append(toIndentedString(idRegPendenza)).append("\n");
    sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
    sb.append("    importoPiuCommissione: ").append(toIndentedString(importoPiuCommissione)).append("\n");
    sb.append("    importoRata: ").append(toIndentedString(importoRata)).append("\n");
    sb.append("    inoltroModalitaTelematica: ").append(toIndentedString(inoltroModalitaTelematica)).append("\n");
    sb.append("    isRata: ").append(toIndentedString(isRata)).append("\n");
    sb.append("    iuvRata: ").append(toIndentedString(iuvRata)).append("\n");
    sb.append("    pagamentoModello1: ").append(toIndentedString(pagamentoModello1)).append("\n");
    sb.append("    pagamentoModello3: ").append(toIndentedString(pagamentoModello3)).append("\n");
    sb.append("    rateizzato: ").append(toIndentedString(rateizzato)).append("\n");
    sb.append("    richiestaRevoca: ").append(toIndentedString(richiestaRevoca)).append("\n");
    sb.append("    richiestaStorno: ").append(toIndentedString(richiestaStorno)).append("\n");
    sb.append("    riferim1: ").append(toIndentedString(riferim1)).append("\n");
    sb.append("    riferim2: ").append(toIndentedString(riferim2)).append("\n");
    sb.append("    riferim3: ").append(toIndentedString(riferim3)).append("\n");
    sb.append("    riferim4: ").append(toIndentedString(riferim4)).append("\n");
    sb.append("    riferim5: ").append(toIndentedString(riferim5)).append("\n");
    sb.append("    riferim6: ").append(toIndentedString(riferim6)).append("\n");
    sb.append("    riferim7: ").append(toIndentedString(riferim7)).append("\n");
    sb.append("    riferim8: ").append(toIndentedString(riferim8)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    statoRichiesta: ").append(toIndentedString(statoRichiesta)).append("\n");
    sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
    sb.append("    tipoServizio: ").append(toIndentedString(tipoServizio)).append("\n");
    sb.append("    vociPagamento: ").append(toIndentedString(vociPagamento)).append("\n");
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

