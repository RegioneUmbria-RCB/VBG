package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.FiltriRicercaTestataBase;

public class FiltriRicercaTestata extends FiltriRicercaTestataBase {

    private String nomeFiltroPosizioniNonPagate;
    private String nomeFiltroAllegaAvvisoPagamento;

    public FiltriRicercaTestata(Integer idTestataMassiva, String nomeFiltroEscludiDestinatariSenzaMail, String nomeFiltroPosizioniNonPagate,
	    String nomeFiltroAllegaAvvisoPagamento, String nomeFiltroSceltaMailAnagrafe, String nomeFiltroConvertiInPDF) {

	super(idTestataMassiva, nomeFiltroEscludiDestinatariSenzaMail, nomeFiltroSceltaMailAnagrafe, nomeFiltroConvertiInPDF);
	if (StringUtils.isBlank(nomeFiltroPosizioniNonPagate)) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare la classe FiltriRicercaTestata senza passare il parametro nomeFiltroPosizioniNonPagate");
	}
	if (StringUtils.isBlank(nomeFiltroAllegaAvvisoPagamento)) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare la classe FiltriRicercaTestata senza passare il parametro nomeFiltroAllegaAvvisoPagamento");
	}
	this.nomeFiltroPosizioniNonPagate = nomeFiltroPosizioniNonPagate;
	this.nomeFiltroAllegaAvvisoPagamento = nomeFiltroAllegaAvvisoPagamento;
    }

    public String getNomeFiltroPosizioniNonPagate() {

	return nomeFiltroPosizioniNonPagate;
    }

    public String getNomeFiltroAllegaAvvisoPagamento() {

	return nomeFiltroAllegaAvvisoPagamento;
    }
}
