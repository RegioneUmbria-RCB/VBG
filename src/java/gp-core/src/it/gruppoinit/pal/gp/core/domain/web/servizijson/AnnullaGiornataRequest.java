package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class AnnullaGiornataRequest {

    String note;
    boolean isAnnulla;

    public boolean getIsisAnnulla() {

	return isAnnulla;
    }

    public void setIsAnnulla(boolean isAnnulla) {

	this.isAnnulla = isAnnulla;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
