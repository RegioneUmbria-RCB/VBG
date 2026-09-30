package it.gruppoinit.pal.gp.areariservata.domain;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.VwScadenzario;
import it.init.sigepro.rte.types.DocumentiType;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ScadenzaHelper {

    private VwScadenzario scadenza;
    private DocumentiType allegato;
    private CommonsMultipartFile file;
    private String note;
    private List<DocumentiType> allegatiCaricati;
    private Movimenti movFatto;
    private Movimenti movDaFare;

    public Movimenti getMovFatto() {

	return movFatto;
    }

    public void setMovFatto(Movimenti movFatto) {

	this.movFatto = movFatto;
    }

    public Movimenti getMovDaFare() {

	return movDaFare;
    }

    public void setMovDaFare(Movimenti movDaFare) {

	this.movDaFare = movDaFare;
    }

    public ScadenzaHelper() {

	allegato = new DocumentiType();
	allegatiCaricati = new ArrayList<DocumentiType>();
    }

    public VwScadenzario getScadenza() {

	return scadenza;
    }

    public void setScadenza(VwScadenzario scadenza) {

	this.scadenza = scadenza;
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
