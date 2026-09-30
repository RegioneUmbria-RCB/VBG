package it.gruppoinit.pal.gp.backoffice.web.util;

import java.util.ArrayList;
import java.util.List;

public class Month {

    private String codice;
    private String descrizione;

    public Month(String codice, String descrizione) {

	this.codice = codice;
	this.descrizione = descrizione;
    }

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public static List<Month> getMonth() {

	List<Month> list = new ArrayList<Month>();
	list.add(new Month("01", "GENNAIO"));
	list.add(new Month("02", "FEBBRAIO"));
	list.add(new Month("03", "MARZO"));
	list.add(new Month("04", "APRILE"));
	list.add(new Month("05", "MAGGIO"));
	list.add(new Month("06", "GIUGNO"));
	list.add(new Month("07", "LUGLIO"));
	list.add(new Month("08", "AGOSTO"));
	list.add(new Month("09", "SETTEMBRE"));
	list.add(new Month("10", "OTTOBRE"));
	list.add(new Month("11", "NOVEMBRE"));
	list.add(new Month("12", "DICEMBRE"));
	return list;
    }
}
