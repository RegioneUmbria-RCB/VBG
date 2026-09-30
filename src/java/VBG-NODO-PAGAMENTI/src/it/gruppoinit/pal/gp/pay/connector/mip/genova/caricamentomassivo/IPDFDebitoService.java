package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo;

import it.gruppoinit.pal.gp.pay.connector.mip.genova.PdfFile;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public interface IPDFDebitoService {

    public PdfFile generaPdfDebito(String urlbase, String token, PayPosizioniDebitorie pos, String idDebito, String connectorId, String idLotto)
	    throws PayException;
}
