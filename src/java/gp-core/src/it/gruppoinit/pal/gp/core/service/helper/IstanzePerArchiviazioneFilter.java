package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Date;
import java.util.List;

public class IstanzePerArchiviazioneFilter {

    private Date dallaData;
    private Date allaData;
    private List<String> mimeTypeFileAmmessi;
    private Integer firstResult;
    private Integer maxResultIstanze;
    private Integer maxResultOggetti;

    public IstanzePerArchiviazioneFilter(VerticalizzazioneArchiviazioneDocumentale vad) {

	this.dallaData = vad.getDallaData();
	this.allaData = vad.getAllaData();
	this.firstResult = 0;
	this.maxResultIstanze = vad.getMaxNumIstanzePerQuery();
	this.maxResultOggetti = vad.getMaxNumFiles();
	this.mimeTypeFileAmmessi = vad.getFileExtensionsType();
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

    public Integer getMaxResultIstanze() {

	return maxResultIstanze;
    }

    public void setMaxResultIstanze(Integer maxResultIstanze) {

	this.maxResultIstanze = maxResultIstanze;
    }

    public Integer getMaxResultOggetti() {

	return maxResultOggetti;
    }

    public void setMaxResultOggetti(Integer maxResultOggetti) {

	this.maxResultOggetti = maxResultOggetti;
    }

    public List<String> getMimeTypeFileAmmessi() {

	return mimeTypeFileAmmessi;
    }

    public void setMimeTypeFileAmmessi(List<String> mimeTypeFileAmmessi) {

	this.mimeTypeFileAmmessi = mimeTypeFileAmmessi;
    }
}
