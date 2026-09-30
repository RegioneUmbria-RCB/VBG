package it.gruppoinit.pal.gp.core.features.mailtipo.sostituzioni;

import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;

public class SegnapostiSorteggiResolver {

    private Sorteggitestata testata;

    public SegnapostiSorteggiResolver(Sorteggitestata testata) {

	this.testata = testata;
    }

    public SegnapostiSorteggiBean get() {

	SegnapostiSorteggiBean retVal = new SegnapostiSorteggiBean();
	retVal.setDataSorteggio(this.testata.getStDatasorteggio());
	retVal.setDescrizione(this.testata.getStDescrizione());
	return retVal;
    }
}
