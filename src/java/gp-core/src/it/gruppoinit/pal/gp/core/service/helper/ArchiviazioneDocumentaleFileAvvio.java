package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.util.HashSet;
import java.util.Set;

public class ArchiviazioneDocumentaleFileAvvio {

    private String service;
    private String nome;
    private String feedbackEmail;
    private String operation;
    private String bucket;
    private String policy;
    private String path;
    private Set<ChiaveValoreBean<String, DocumentoLegalDocHelper>> documentList = new HashSet<ChiaveValoreBean<String, DocumentoLegalDocHelper>>();

    public ArchiviazioneDocumentaleFileAvvio(VerticalizzazioneArchiviazioneDocumentale vad) {

	this.service = vad.getService();
	this.nome = vad.getNome();
	this.operation = vad.getOperation();
	this.policy = vad.getPolicy();
	this.bucket = vad.getBucket();
	this.feedbackEmail = vad.getFeedbackEmail();
	this.path = vad.getLEGALDOC_TRIGGER_PATH();
    }

    public String getService() {

	return service;
    }

    public String getNome() {

	return nome;
    }

    public String getFeedbackEmail() {

	return feedbackEmail;
    }

    public String getOperation() {

	return operation;
    }

    public String getBucket() {

	return bucket;
    }

    public String getPolicy() {

	return policy;
    }

    //    public Set<ChiaveValoreBean<String, String>> getDocumentList() {
    //
    //	return documentList;
    //    }
    public Set<ChiaveValoreBean<String, DocumentoLegalDocHelper>> getDocumentList() {

	return documentList;
    }

    public String getPath() {

	return path;
    }

    public void setPath(String path) {

	this.path = path;
    }
}
