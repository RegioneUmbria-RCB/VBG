package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.FiltriRicercaTestataBase;

public class FiltriRicercaTestataCommissioni extends FiltriRicercaTestataBase {

    public FiltriRicercaTestataCommissioni(Integer idTestataMassiva, String nomeFiltroEscludiDestinatariSenzaMail,
	    String nomeFiltroSceltaMailAnagrafe, String nomeFiltroConvertiInPDF) {

	super(idTestataMassiva, nomeFiltroEscludiDestinatariSenzaMail, nomeFiltroSceltaMailAnagrafe, nomeFiltroConvertiInPDF);
    }
}
