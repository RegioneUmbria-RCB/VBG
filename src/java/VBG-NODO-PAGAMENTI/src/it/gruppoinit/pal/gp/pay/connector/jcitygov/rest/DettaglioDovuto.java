package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DettaglioDovuto {

    @XmlElement
    private String causaleDebito;

    @XmlElement
    private String codiceIpaCreditore;

    @XmlElement
    private String codiceLotto;

    @XmlElement
    private String codiceTipoDebito;

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
    private String gruppo;

    @XmlElement
    private String idDeb;

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

	public String getCausaleDebito() {
		return causaleDebito;
	}

	public void setCausaleDebito(String causaleDebito) {
		this.causaleDebito = causaleDebito;
	}

	public String getCodiceIpaCreditore() {
		return codiceIpaCreditore;
	}

	public void setCodiceIpaCreditore(String codiceIpaCreditore) {
		this.codiceIpaCreditore = codiceIpaCreditore;
	}

	public String getCodiceLotto() {
		return codiceLotto;
	}

	public void setCodiceLotto(String codiceLotto) {
		this.codiceLotto = codiceLotto;
	}

	public String getCodiceTipoDebito() {
		return codiceTipoDebito;
	}

	public void setCodiceTipoDebito(String codiceTipoDebito) {
		this.codiceTipoDebito = codiceTipoDebito;
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

	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public String getIdDeb() {
		return idDeb;
	}

	public void setIdDeb(String idDeb) {
		this.idDeb = idDeb;
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
