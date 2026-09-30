package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Date;

public class IstanzePerArchiviazioneFilter {

    private Date dallaData;
    private Date allaData;
    private Integer firstResult;
    private Integer maxResult;

    public IstanzePerArchiviazioneFilter(VerticalizzazioneArchiviazioneDocumentale vad) {

	this.dallaData = vad.getDallaData();
	this.allaData = vad.getAllaData();
	this.firstResult = 0;
	this.maxResult = vad.getMaxNumIstanzePerQuery();
    }

    public Date getDallaData() {

	return dallaData;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    public Integer getFirstResult() {

	return firstResult;
    }

    public void setFirstResult(Integer firstResult) {

	this.firstResult = firstResult;
    }

    public Integer getMaxResult() {

	return maxResult;
    }

    public void setMaxResult(Integer maxResult) {

	this.maxResult = maxResult;
    }
}
