package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.Date;

public class CdsAttiBean {
    // select data,ora,note,codiceoggetto from cdsatti ;

    private Date data;
    private String ora;
    private String note;
    private Integer codiceoggetto;
    private String nomefile;

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getOra() {

	return ora;
    }

    public void setOra(String ora) {

	this.ora = ora;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Integer getCodiceoggetto() {

	return codiceoggetto;
    }

    public void setCodiceoggetto(Integer codiceoggetto) {

	this.codiceoggetto = codiceoggetto;
    }

    public String getNomefile() {

	return nomefile;
    }

    public void setNomefile(String nomefile) {

	this.nomefile = nomefile;
    }
}
