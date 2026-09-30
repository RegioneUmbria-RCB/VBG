package it.alveo.firmaremota.aruba.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.PdfReader;

public class PDFUtilitiesService implements IPDFUtilitiesService {

    private static final Logger logger = LoggerFactory.getLogger(PDFUtilitiesService.class);

    @Override
    public int getNumberPagePdf(byte[] pdf) {

	logger.debug("Istanzio il pdfReader....");
	Integer numeroPagine = 0;
	try {
	    try (PdfReader reader = new PdfReader(pdf)) {
		numeroPagine = reader.getNumberOfPages();
	    }
	} catch (Exception e) {
	    logger.error("Errore durante il calcolo delle pagine del pdf. Applico la firma sulla prima pagina");
	    numeroPagine = 1;
	}
	return numeroPagine;
    }
}
