package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import it.gruppoinit.pal.gp.pay.connector.mip.genova.PdfFile;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.IPDFDebitoService;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public class FakePdfDebitoService implements IPDFDebitoService {

    private String pdfName;
    private int numPagine;

    public FakePdfDebitoService(String pdfName, int numPagine) {

	this.pdfName = pdfName;
	this.numPagine = numPagine;
    }

    @Override
    public PdfFile generaPdfDebito(String urlbase, String token, PayPosizioniDebitorie pos, String idDebito, String connectorId, String idLotto)
	    throws PayException {

	return new PdfFile(this.pdfName, this.numPagine);
    }
}
