package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Debito {
	@XmlElement
    private String causaAggStato;
	@XmlElement
    private String causaleDebito;
	@XmlElement
    private String codIpaCreditore;
	@XmlElement
    private String codIpaRichiedente;
	@XmlElement
    private String codiceLotto;
	@XmlElement
    private String codiceServizio;
	@XmlElement
    private String codiceTipoDebito;
	@XmlElement
    private DatiContribuente contribuenteDto;
	@XmlElement
    private String dataAttualizzazione;
	@XmlElement
    private String dataFineValidita;
	@XmlElement
    private String dataInizioValidita;
	@XmlElement
    private String dataLimitePagabilita;
	@XmlElement
    private List<DatiAccertamento> datiAccertamento;
	@XmlElement
    private String dettaglioPosizione;
	@XmlElement
    private String gruppo;
	@XmlElement
    private String idDebito;
	@XmlElement
    private String idDebitoBO;
	@XmlElement
    private String idPosizioneBO;
	@XmlElement
    private BigDecimal importoDebito;
	@XmlElement
    private BigDecimal importoPaFee;
	@XmlElement
    private BigDecimal importoSpeseNotifica;
	@XmlElement
    private MarcaDaBollo marcaDaBollo;
	@XmlElement
    private String ordinamento;
	@XmlElement
    private List<ParametroDebito> parametriDebito;
	@XmlElement
    private String speseNotificaDaAttualizzare;
	@XmlElement
    private String speseNotificaVoceContabile;
	
	
	public String getCausaAggStato() {
		return causaAggStato;
	}
	public void setCausaAggStato(String causaAggStato) {
		this.causaAggStato = causaAggStato;
	}
	public String getCausaleDebito() {
		return causaleDebito;
	}
	public void setCausaleDebito(String causaleDebito) {
		this.causaleDebito = causaleDebito;
	}
	public String getCodIpaCreditore() {
		return codIpaCreditore;
	}
	public void setCodIpaCreditore(String codIpaCreditore) {
		this.codIpaCreditore = codIpaCreditore;
	}
	public String getCodIpaRichiedente() {
		return codIpaRichiedente;
	}
	public void setCodIpaRichiedente(String codIpaRichiedente) {
		this.codIpaRichiedente = codIpaRichiedente;
	}
	public String getCodiceLotto() {
		return codiceLotto;
	}
	public void setCodiceLotto(String codiceLotto) {
		this.codiceLotto = codiceLotto;
	}
	public String getCodiceServizio() {
		return codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	public String getCodiceTipoDebito() {
		return codiceTipoDebito;
	}
	public void setCodiceTipoDebito(String codiceTipoDebito) {
		this.codiceTipoDebito = codiceTipoDebito;
	}
	public DatiContribuente getContribuenteDto() {
		return contribuenteDto;
	}
	public void setContribuenteDto(DatiContribuente contribuenteDto) {
		this.contribuenteDto = contribuenteDto;
	}
	public String getDataAttualizzazione() {
		return dataAttualizzazione;
	}
	public void setDataAttualizzazione(String dataAttualizzazione) {
		this.dataAttualizzazione = dataAttualizzazione;
	}
	public String getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public String getDataLimitePagabilita() {
		return dataLimitePagabilita;
	}
	public void setDataLimitePagabilita(String dataLimitePagabilita) {
		this.dataLimitePagabilita = dataLimitePagabilita;
	}
	public List<DatiAccertamento> getDatiAccertamento() {
		return datiAccertamento;
	}
	public void setDatiAccertamento(List<DatiAccertamento> datiAccertamento) {
		this.datiAccertamento = datiAccertamento;
	}
	public String getDettaglioPosizione() {
		return dettaglioPosizione;
	}
	public void setDettaglioPosizione(String dettaglioPosizione) {
		this.dettaglioPosizione = dettaglioPosizione;
	}
	public String getGruppo() {
		return gruppo;
	}
	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}
	public String getIdDebito() {
		return idDebito;
	}
	public void setIdDebito(String idDebito) {
		this.idDebito = idDebito;
	}
	public String getIdDebitoBO() {
		return idDebitoBO;
	}
	public void setIdDebitoBO(String idDebitoBO) {
		this.idDebitoBO = idDebitoBO;
	}
	public String getIdPosizioneBO() {
		return idPosizioneBO;
	}
	public void setIdPosizioneBO(String idPosizioneBO) {
		this.idPosizioneBO = idPosizioneBO;
	}
	public BigDecimal getImportoDebito() {
		return importoDebito;
	}
	public void setImportoDebito(BigDecimal importoDebito) {
		this.importoDebito = importoDebito;
	}
	public BigDecimal getImportoPaFee() {
		return importoPaFee;
	}
	public void setImportoPaFee(BigDecimal importoPaFee) {
		this.importoPaFee = importoPaFee;
	}
	public BigDecimal getImportoSpeseNotifica() {
		return importoSpeseNotifica;
	}
	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}
	public MarcaDaBollo getMarcaDaBollo() {
		return marcaDaBollo;
	}
	public void setMarcaDaBollo(MarcaDaBollo marcaDaBollo) {
		this.marcaDaBollo = marcaDaBollo;
	}
	public String getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}
	public List<ParametroDebito> getParametriDebito() {
		return parametriDebito;
	}
	public void setParametriDebito(List<ParametroDebito> parametriDebito) {
		this.parametriDebito = parametriDebito;
	}
	public String getSpeseNotificaDaAttualizzare() {
		return speseNotificaDaAttualizzare;
	}
	public void setSpeseNotificaDaAttualizzare(String speseNotificaDaAttualizzare) {
		this.speseNotificaDaAttualizzare = speseNotificaDaAttualizzare;
	}
	public String getSpeseNotificaVoceContabile() {
		return speseNotificaVoceContabile;
	}
	public void setSpeseNotificaVoceContabile(String speseNotificaVoceContabile) {
		this.speseNotificaVoceContabile = speseNotificaVoceContabile;
	}

    
}
