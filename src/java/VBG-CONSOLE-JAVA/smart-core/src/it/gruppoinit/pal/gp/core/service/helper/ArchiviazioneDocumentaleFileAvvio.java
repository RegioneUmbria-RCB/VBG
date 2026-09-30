package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.util.HashSet;
import java.util.Set;

public class ArchiviazioneDocumentaleFileAvvio {

    private String service;
    private String endUserId;
    private String nome;
    private String feedbackEmail;
    private String sgd;
    private Integer numeroFileTotali;
    private Integer numeroFileIndice;
    private Set<String> fileIndiceNameList = new HashSet<String>();
    private Set<ChiaveValoreBean<String, Integer>> filePerEstensioneList = new HashSet<ChiaveValoreBean<String, Integer>>();

    public ArchiviazioneDocumentaleFileAvvio(VerticalizzazioneArchiviazioneDocumentale vad) {

	this.service = vad.getService();
	this.endUserId = vad.getEndUserId();
	this.nome = vad.getNome();
	this.feedbackEmail = vad.getFeedbackEmail();
	this.sgd = vad.getSgd();
	String[] fileExts = vad.getFileExtensions();
	if (fileExts != null) {
	    for (String fileExt : fileExts) {
		ChiaveValoreBean<String, Integer> cvb = new ChiaveValoreBean<String, Integer>();
		cvb.setChiave(fileExt);
		cvb.setValore(0);
		filePerEstensioneList.add(cvb);
	    }
	}
	this.numeroFileIndice = 0;
	this.numeroFileTotali = 0;
    }

    public String getService() {

	return service;
    }

    public String getEndUserId() {

	return endUserId;
    }

    public String getNome() {

	return nome;
    }

    public String getFeedbackEmail() {

	return feedbackEmail;
    }

    public String getSgd() {

	return sgd;
    }

    public Integer getNumeroFileTotali() {

	return numeroFileTotali;
    }

    public void setNumeroFileTotali(Integer numeroFileTotali) {

	this.numeroFileTotali = numeroFileTotali;
    }

    public Integer getNumeroFileIndice() {

	return numeroFileIndice;
    }

    public void setNumeroFileIndice(Integer numeroFileIndice) {

	this.numeroFileIndice = numeroFileIndice;
    }

    public Set<String> getFileIndiceNameList() {

	return fileIndiceNameList;
    }

    public void setFileIndiceNameList(Set<String> fileIndiceNameList) {

	this.fileIndiceNameList = fileIndiceNameList;
    }

    public Set<ChiaveValoreBean<String, Integer>> getFilePerEstensioneList() {

	return filePerEstensioneList;
    }

    public void setFilePerEstensioneList(Set<ChiaveValoreBean<String, Integer>> filePerEstensioneList) {

	this.filePerEstensioneList = filePerEstensioneList;
    }
}
