package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class MessaggioCommissioneChiusa extends MessaggioCommissioni {

    private String numeroCommissione;
    private String autore;
    private Date dataChiusura;

    public MessaggioCommissioneChiusa(String numeroCommissione, String autore, Date dataChiusura) {

	super(CommissioniCategorieEnum.COMMISSIONE_CHIUSA);
	this.numeroCommissione = numeroCommissione;
	this.autore = autore;
	this.dataChiusura = (dataChiusura != null) ? dataChiusura : new Date();
    }

    @Override
    public String getTestoMessaggio() {

	return "La commissione numero \"" + numeroCommissione + "\" è stata chiusa da " + autore + " in data " +
	       Utilities.formatDate(dataChiusura, WebConstants.DATE_FORMAT_PATTERN);
    }
}
