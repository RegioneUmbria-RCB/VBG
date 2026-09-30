package it.gruppoinit.pal.gp.backoffice.web.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppelloPratiche;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;

public class CommEdilizieAppelloPraticheHelper {

    private CommedilizieAppello appello;
    private CommissioniedilizieR commissioniedilizieR;
    private boolean checked;

    public CommEdilizieAppelloPraticheHelper(CommedilizieAppello appello, CommissioniedilizieR commissioniedilizieR, boolean checked) {

	this.appello = appello;
	this.commissioniedilizieR = commissioniedilizieR;
	this.checked = checked;
    }

    public CommedilizieAppello getAppello() {

	return appello;
    }

    public void setAppello(CommedilizieAppello appello) {

	this.appello = appello;
    }

    public CommissioniedilizieR getCommissioniedilizieR() {

	return commissioniedilizieR;
    }

    public void setCommissioniedilizieR(CommissioniedilizieR commissioniedilizieR) {

	this.commissioniedilizieR = commissioniedilizieR;
    }

    public boolean isChecked() {

	return checked;
    }

    public void setChecked(boolean checked) {

	this.checked = checked;
    }

    public static List<CommEdilizieAppelloPraticheHelper> fromCommissioniEdilizieRlist(List<CommissioniedilizieR> cr, CommedilizieAppello appello,
	    boolean checked) {

	List<CommEdilizieAppelloPraticheHelper> l = new ArrayList<CommEdilizieAppelloPraticheHelper>();
	for (CommissioniedilizieR commissioniedilizieR : cr) {
	    l.add(new CommEdilizieAppelloPraticheHelper(appello, commissioniedilizieR, checked));
	}
	return l;
    }

    public static List<CommEdilizieAppelloPraticheHelper> fromAppelloPraticheAndAppelloList(List<CommissioniedilizieR> commedilizieR,
	    List<CommedilizieAppelloPratiche> appelloPratiches, CommedilizieAppello appello) {

	List<CommEdilizieAppelloPraticheHelper> l = new ArrayList<CommEdilizieAppelloPraticheHelper>();
	Set<Integer> codiciCr = new HashSet<Integer>();
	for (CommedilizieAppelloPratiche appr : appelloPratiches) {
	    codiciCr.add(appr.getCommissioniedilizieR().getId().getCodice());
	}
	for (CommissioniedilizieR commissioniedilizieR : commedilizieR) {
	    l.add(new CommEdilizieAppelloPraticheHelper(appello, commissioniedilizieR, codiciCr.contains(commissioniedilizieR.getId().getCodice())));
	}
	return l;
    }
}
