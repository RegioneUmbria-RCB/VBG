package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Giorno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PeriodicitaHelperBean {

    private int nrGiorni = 0;
    private Calendar giornoIniziale = GregorianCalendar.getInstance();
    private List<Giorno> listGiorni = new ArrayList<Giorno>();
    List<ImportoHelperBean> listImportiConto = new ArrayList<ImportoHelperBean>();

    public PeriodicitaHelperBean(int nrGiorni, Calendar giornoIniziale, List<Giorno> listGiorni) {

	this.nrGiorni = nrGiorni;
	this.giornoIniziale = giornoIniziale;
	this.listGiorni = listGiorni;
    }

    public PeriodicitaHelperBean(int nrGiorni, String giornoInizialeFmt, List<Giorno> listGiorni) {

	this.nrGiorni = nrGiorni;
	this.listGiorni = listGiorni;
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	try {
	    Date giorno = sdf.parse(giornoInizialeFmt);
	    giornoIniziale = GregorianCalendar.getInstance();
	    giornoIniziale.setTime(giorno);
	} catch (ParseException e) {
	    giornoIniziale = GregorianCalendar.getInstance();
	}
    }

    public int getNrGiorni() {

	return nrGiorni;
    }

    public void setNrGiorni(int nrGiorni) {

	this.nrGiorni = nrGiorni;
    }

    public Calendar getGiornoIniziale() {

	return giornoIniziale;
    }

    public void setGiornoIniziale(Calendar giornoIniziale) {

	this.giornoIniziale = giornoIniziale;
    }

    public void setListGiorni(List<Giorno> listGiorni) {

	this.listGiorni = listGiorni;
    }

    public List<Giorno> getListGiorni() {

	return listGiorni;
    }

    public void setListImportiConto(List<ImportoHelperBean> listImportiConto) {

	this.listImportiConto = listImportiConto;
    }

    public List<ImportoHelperBean> getListImportiConto() {

	return listImportiConto;
    }
}
