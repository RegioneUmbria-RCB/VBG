package it.gruppoinit.pdfutils.service;

import it.gruppoinit.pdfutils.schemas.messages.DatiPDFType;
import it.gruppoinit.pdfutils.schemas.messages.Font;
import it.gruppoinit.pdfutils.schemas.messages.Layer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public interface PDFWorkerService {

    public void precompilaPDF(String token, File xmlFileIn, File[] pdf);

    public List<DatiPDFType> pdfToModel(String token, File pdf) throws Exception;

    public File appendTextAsLayer(List<String> listaTesti, File pdf, PDDocument document, PDPage targetPage, Layer layer, Font font)
	    throws IOException;
}
