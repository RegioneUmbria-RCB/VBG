package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Date;

public class DocumentiIstanzaRestHelper {

    private String descrizione;
    private String note;
    private Date data;
    private Integer verifica;
    private Boolean necessario;
    private String nomefile;

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public Integer getVerifica() {

	return verifica;
    }

    public void setVerifica(Integer verifica) {

	this.verifica = verifica;
    }

    public String getNomefile() {

	return nomefile;
    }

    public void setNomefile(String nomefile) {

	this.nomefile = nomefile;
    }

    public Boolean getNecessario() {

	return necessario;
    }

    public void setNecessario(Boolean necessario) {

	this.necessario = necessario;
    }
}
