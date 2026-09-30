package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;

public class OneriTipiRateizzazioneBean {

    private Integer id;
    private String descrizione;
    private Integer numeroRate;
    private String ripartizioneRate;
    private Integer determDataInizioRate;
    private Integer idScadenzaRate;
    private String tipoMovimento;
    private String movimento;
    private String frequenzaRate;
    private String interessi;
    private BigDecimal speseRateizzazione;
    private Boolean interessiLegali;
    private Integer tipoAnatocismo;
    private String tipologiaRateizzazione;
    private String scadenzePeriodi;

    public static OneriTipiRateizzazioneBean fromOneritipirateizzazione(Oneritipirateizzazione rateizzazione) {

	if (rateizzazione == null) {
	    return null;
	}
	OneriTipiRateizzazioneBean bean = new OneriTipiRateizzazioneBean();
	if (rateizzazione.getId() != null) {
	    bean.setId(rateizzazione.getId().getCodice());
	}
	bean.setDescrizione(rateizzazione.getDescrizione());
	bean.setNumeroRate(rateizzazione.getNumerorate());
	bean.setRipartizioneRate(rateizzazione.getRipartizionerate());
	bean.setDetermDataInizioRate(rateizzazione.getDetermdatainiziorate());
	if (rateizzazione.getTipimovimento() != null && rateizzazione.getTipimovimento().getId() != null) {
	    bean.setTipoMovimento(rateizzazione.getTipimovimento().getId().getTipomovimento());
	    bean.setMovimento(rateizzazione.getTipimovimento().getMovimento());
	}
	if (rateizzazione.getScadenzarate() != null) {
	    bean.setIdScadenzaRate(rateizzazione.getScadenzarate().getId());
	}
	bean.setFrequenzaRate(rateizzazione.getFrequenzarate());
	bean.setInteressi(rateizzazione.getInteressirate());
	bean.setSpeseRateizzazione(rateizzazione.getSpeseRateizzazione());
	bean.setInteressiLegali(rateizzazione.getFlagInteressiLegali());
	bean.setTipoAnatocismo(rateizzazione.getTipoAnatocismo());
	if (!StringUtils.isBlank(rateizzazione.getTipologiaRateizzazione())) {
	    bean.setTipologiaRateizzazione(rateizzazione.getTipologiaRateizzazione());
	}
	bean.setScadenzePeriodi(rateizzazione.getScadenzePeriodi());
	return bean;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getNumeroRate() {

	return numeroRate;
    }

    public void setNumeroRate(Integer numeroRate) {

	this.numeroRate = numeroRate;
    }

    public String getRipartizioneRate() {

	return ripartizioneRate;
    }

    public void setRipartizioneRate(String ripartizioneRate) {

	this.ripartizioneRate = ripartizioneRate;
    }

    public Integer getDetermDataInizioRate() {

	return determDataInizioRate;
    }

    public void setDetermDataInizioRate(Integer determDataInizioRate) {

	this.determDataInizioRate = determDataInizioRate;
    }

    public Integer getIdScadenzaRate() {

	return idScadenzaRate;
    }

    public void setIdScadenzaRate(Integer idScadenzaRate) {

	this.idScadenzaRate = idScadenzaRate;
    }

    public String getTipoMovimento() {

	return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {

	this.tipoMovimento = tipoMovimento;
    }

    public String getMovimento() {

	return movimento;
    }

    public void setMovimento(String movimento) {

	this.movimento = movimento;
    }

    public String getFrequenzaRate() {

	return frequenzaRate;
    }

    public void setFrequenzaRate(String frequenzaRate) {

	this.frequenzaRate = frequenzaRate;
    }

    public String getInteressi() {

	return interessi;
    }

    public void setInteressi(String interessi) {

	this.interessi = interessi;
    }

    public BigDecimal getSpeseRateizzazione() {

	return speseRateizzazione;
    }

    public void setSpeseRateizzazione(BigDecimal speseRateizzazione) {

	this.speseRateizzazione = speseRateizzazione;
    }

    public Boolean getInteressiLegali() {

	return interessiLegali;
    }

    public void setInteressiLegali(Boolean interessiLegali) {

	this.interessiLegali = interessiLegali;
    }

    public Integer getTipoAnatocismo() {

	return tipoAnatocismo;
    }

    public void setTipoAnatocismo(Integer tipoAnatocismo) {

	this.tipoAnatocismo = tipoAnatocismo;
    }

    public String getTipologiaRateizzazione() {

	return tipologiaRateizzazione;
    }

    public void setTipologiaRateizzazione(String tipologiaRateizzazione) {

	this.tipologiaRateizzazione = tipologiaRateizzazione;
    }

    public String getScadenzePeriodi() {

	return scadenzePeriodi;
    }

    public void setScadenzePeriodi(String scadenzePeriodi) {

	this.scadenzePeriodi = scadenzePeriodi;
    }
}
