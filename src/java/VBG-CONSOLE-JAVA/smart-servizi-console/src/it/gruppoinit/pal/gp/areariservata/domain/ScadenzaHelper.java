package it.gruppoinit.pal.gp.areariservata.domain;

import it.init.sigepro.rte.types.DocumentiType;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ScadenzaHelper {

    private DocumentiType allegato;
    private CommonsMultipartFile file;
    private String note;
    private List<DocumentiType> allegatiCaricati;

    public ScadenzaHelper() {

	allegato = new DocumentiType();
	allegatiCaricati = new ArrayList<DocumentiType>();
    }

    public CommonsMultipartFile getFile() {

	return file;
    }

    public void setFile(CommonsMultipartFile file) {

	this.file = file;
    }

    public DocumentiType getAllegato() {

	return allegato;
    }

    public void setAllegato(DocumentiType allegato) {

	this.allegato = allegato;
    }

    public List<DocumentiType> getAllegatiCaricati() {

	return allegatiCaricati;
    }

    public void setAllegatiCaricati(List<DocumentiType> allegatiCaricati) {

	this.allegatiCaricati = allegatiCaricati;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
