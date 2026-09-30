package it.gruppoinit.pal.gp.core.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfBorderArray;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImage;
import com.lowagie.text.pdf.PdfIndirectObject;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import it.gruppoinit.pal.gp.core.service.ManipulatePdfService;
import it.gruppoinit.pal.gp.core.service.helper.AnnotazionePropertyPdfHelper;

@Service
public class ManipulatePdfServiceImpl implements ManipulatePdfService {

    private static final Logger log = LoggerFactory.getLogger(ManipulatePdfServiceImpl.class);

    @Override
    public byte[] addAnnotation(AnnotazionePropertyPdfHelper annotazionePropertyPdfHelper) {

	log.debug("addAnnotation# Start....");
	log.debug("addAnnotation# Istanzio il pdfReader....");
	PdfReader reader;
	Integer numeroPagine = 0;
	File tempFile = null;
	byte[] data;
	try {
	    reader = new PdfReader(annotazionePropertyPdfHelper.getFilePdf());
	    log.debug("addAnnotation# Recupero il numero della pagina");
	    numeroPagine = reader.getNumberOfPages();
	    //Create PdfStamp object
	    tempFile = File.createTempFile("ANNOTATION-TEMP-FILE-",
		    StringUtils.defaultIfEmpty(annotazionePropertyPdfHelper.getNomeFile(), UUID.randomUUID().toString() + ".pdf"));
	    FileOutputStream oFile = new FileOutputStream(tempFile, false);
	    log.debug("addAnnotation# Istanzio il PdfStamper....");
	    PdfStamper stamp = new PdfStamper(reader, oFile, '\0', true);
	    //Create the proper annotation
	    PdfWriter writer = stamp.getWriter();
	    PdfContentByte cb = new PdfContentByte(writer);
	    log.debug("addAnnotation# Gestione colore font");
	    Color colorFont = stringRGBToColor(annotazionePropertyPdfHelper.getColoreFontRGB());
	    if (colorFont != null) {
		cb.setColorFill(colorFont);
	    } else {
		log.warn("addAnnotation# Colore font annotazione non settato o non corretto. Verificare nella verticalizzazione ");
	    }
	    log.debug("addAnnotation# Gestione posizionamento annotazione");
	    log.debug("addAnnotation# Gestione dimensione e posizione annotazione");
	    //Rectangle rectangle = getRettangolo(annotazionePropertyPdfHelper);
	    Rectangle rectangle = getRettangolo1(annotazionePropertyPdfHelper, reader);
	    PdfAnnotation annot = PdfAnnotation.createFreeText(stamp.getWriter(), rectangle, annotazionePropertyPdfHelper.getAnnotatazione(), cb);
	    log.debug("addAnnotation# isBordoAnnotazioneAttivo={}", annotazionePropertyPdfHelper.isBordoAnnotazioneAttivo());
	    if (!annotazionePropertyPdfHelper.isBordoAnnotazioneAttivo()) {
		annot.setBorder(new PdfBorderArray(0f, 0f, 0f));
	    }
	    log.debug("addAnnotation# Gestione colore background annotazione");
	    Color colorBackGroudAnnotazione = stringRGBToColor(annotazionePropertyPdfHelper.getColoreSfondoAnnotationRBG());
	    if (colorBackGroudAnnotazione != null) {
		annot.setColor(colorBackGroudAnnotazione);
	    } else {
		log.warn("addAnnotation#Colore background  annotazione non settato o non corretto impostato quello di default.");
	    }
	    log.debug("addAnnotation# Gestione proprietà annotazione");
	    if (annotazionePropertyPdfHelper.getLockAnnotation() == 1) {
		log.debug("addAnnotation# Annotazione bloccata, non può essere ne spostata ne eliminata");
		annot.setFlags(PdfAnnotation.FLAGS_LOCKED + PdfAnnotation.FLAGS_PRINT);
	    }
	    log.debug("addAnnotation# Gestione pagine con annotazione");
	    setPagineConAnnotazione(stamp, annot, annotazionePropertyPdfHelper.getPaginaAnnotation(), numeroPagine);
	    stamp.close();
	    reader.close();
	    log.debug("addAnnotation# Converto il file in byte[]");
	    data = FileUtils.readFileToByteArray(tempFile);
	    log.debug("addAnnotation#Close PdfStamper and PdfReader");
	} catch (DocumentException e) {
	    log.error("addAnnotation#1. Errore durante la creazione dell'annotazione: {} ", e);
	    throw new RuntimeException("Errore durante la creazione dell'annotazione: " + e);
	} catch (IOException e1) {
	    log.error("addAnnotation#2. Errore durante la creazione dell'annotazione: {} ", e1);
	    throw new RuntimeException("Errore durante la creazione dell'annotazione: " + e1);
	} catch (Exception e2) {
	    log.error("addAnnotation#2. Errore durante la creazione dell'annotazione: {} ", e2);
	    throw new RuntimeException("Errore durante la creazione dell'annotazione: " + e2);
	} finally {
	    gracefullyDeleteFiles(tempFile);
	}
	return data;
    }

    private Rectangle getRettangolo1(AnnotazionePropertyPdfHelper annotazionePropertyPdfHelper, PdfReader reader) {

	Rectangle rectangle = null;
	log.debug("getRettangolo# Lunghezza {}");
	float l = 190;
	float h = 50;
	if (annotazionePropertyPdfHelper.getDimensioneRettangoloX() != 0) {
	    log.warn("getRettangolo# lunghezza rettangolo non settata, valore impostato di default: {}", 190);
	    l = annotazionePropertyPdfHelper.getDimensioneRettangoloX();
	}
	if (annotazionePropertyPdfHelper.getDimensioneRettangoloY() != 0) {
	    log.warn("getRettangolo# altezza rettangolo non settata, valore impostato di default: {}", 50);
	    h = annotazionePropertyPdfHelper.getDimensioneRettangoloY();
	}
	float l1 = l;
	float h1 = h;
	// posizione rettangolo annotazione
	float x = reader.getPageSize(1).getWidth() - l - annotazionePropertyPdfHelper.getPosizioneRettangoloX();
	float y = reader.getPageSize(1).getHeight() - h - annotazionePropertyPdfHelper.getPosizioneRettangoloY();
	////
	float checKspostamentoX = x + (2 * l);
	float checKspostamentoY = y + (2 * h);
	if (checKspostamentoX > reader.getPageSize(1).getWidth()) {
	    x = reader.getPageSize(1).getWidth() - (2 * l);
	    log.warn("getRettangolo# La posizione x è maggiore di quella consentita, verrà impostato il valore massimo consentito : {}", x);
	}
	if (checKspostamentoY > reader.getPageSize(1).getHeight()) {
	    y = reader.getPageSize(1).getHeight() - (2 * h);
	    log.warn("getRettangolo# La posizione y è maggiore di quella consentita, verrà impostato il valore massimo consentito : {}", y);
	}
	if (x == 0) {
	    l1 = 0;
	}
	if (y == 0) {
	    h1 = 0;
	}
	rectangle = new Rectangle(x + l, y + h, x + (2 * l1), y + (2 * h1));
	//rectangle.setBorder(Rectangle.NO_BORDER);
	return rectangle;
    }

    @Override
    public int getNumberPagePdf(byte[] pdf) {

	log.debug("addAnnotation# Istanzio il pdfReader....");
	PdfReader reader;
	Integer numeroPagine = 0;
	try {
	    reader = new PdfReader(pdf);
	    numeroPagine = reader.getNumberOfPages();
	} catch (Exception e) {
	    log.error("getNumeberPagePdf# Errore durante il calcolo delle pagine del pdf. Applico la firma sulla prima pagina");
	    numeroPagine = 1;
	}
	return numeroPagine;
    }

    private void setPagineConAnnotazione(PdfStamper stamp, PdfAnnotation annot, Integer paginaAnnotation, Integer numeroPagine) {

	if (paginaAnnotation == null) {
	    paginaAnnotation = 1;
	}
	if (numeroPagine == 1) {
	    log.debug("setPagineConAnnotazione# Il documento contiene una sola pagina, posiziono l'annotazione sull'unica pagina");
	    stamp.addAnnotation(annot, 1);
	} else {
	    if (paginaAnnotation.equals(-1)) {
		log.debug("setPagineConAnnotazione# applico l'annotazione sull'ultima pagina");
		stamp.addAnnotation(annot, numeroPagine);
	    } else if (paginaAnnotation.equals(0)) {
		log.debug("setPagineConAnnotazione# applico l'annotazione su tutte le pagine");
		for (int page = 1; page <= numeroPagine; page++) {
		    stamp.addAnnotation(annot, page);
		}
	    } else {
		log.debug("setPagineConAnnotazione# applico l'annotazione sulla pagina {}", paginaAnnotation);
		stamp.addAnnotation(annot, paginaAnnotation);
	    }
	}
    }

    private Rectangle getRettangolo(AnnotazionePropertyPdfHelper annotazionePropertyPdfHelper) {

	Rectangle rectangle = null;
	log.debug("getRettangolo# Lunghezza {}");
	float l = 190;
	float h = 50;
	if (annotazionePropertyPdfHelper.getDimensioneRettangoloX() != 0) {
	    log.warn("getRettangolo# lunghezza rettangolo non settata, valore impostato di default: {}", 190);
	    l = annotazionePropertyPdfHelper.getDimensioneRettangoloX();
	}
	if (annotazionePropertyPdfHelper.getDimensioneRettangoloY() != 0) {
	    log.warn("getRettangolo# altezza rettangolo non settata, valore impostato di default: {}", 50);
	    h = annotazionePropertyPdfHelper.getDimensioneRettangoloY();
	}
	float l1 = l;
	float h1 = h;
	// posizione rettangolo annotazione
	float x = annotazionePropertyPdfHelper.getPosizioneRettangoloX();
	float y = annotazionePropertyPdfHelper.getPosizioneRettangoloY();
	//	    float x = 200;
	//	    float y = 700;
	float checKspostamentoX = x + (2 * l);
	float checKspostamentoY = y + (2 * h);
	if (checKspostamentoX > PageSize.A4.getWidth()) {
	    x = PageSize.A4.getWidth() - (2 * l);
	    log.warn("getRettangolo# La posizione x è maggiore di quella consentita, verrà impostato il valore massimo consentito : {}", x);
	}
	if (checKspostamentoY > PageSize.A4.getHeight()) {
	    y = PageSize.A4.getHeight() - (2 * h);
	    log.warn("getRettangolo# La posizione y è maggiore di quella consentita, verrà impostato il valore massimo consentito : {}", y);
	}
	if (x == 0) {
	    l1 = 0;
	}
	if (y == 0) {
	    h1 = 0;
	}
	rectangle = new Rectangle(x + l, y + h, x + (2 * l1), y + (2 * h1));
	//rectangle.setBorder(Rectangle.NO_BORDER);
	return rectangle;
    }

    private Color stringRGBToColor(String coloreRGB) {

	Color color = null;
	int r = 0;
	int b = 0;
	int g = 0;
	if (StringUtils.isNotBlank(coloreRGB)) {
	    String[] RGB = StringUtils.split(coloreRGB, ",");
	    for (int i = 0; i < RGB.length; i++) {
		try {
		    Integer c = Integer.parseInt(RGB[i]);
		    if (c > 255) {
			log.error(
				"stringRBGToColor# Uno dei codici del colore non è maggiore di 255, il codice colore non può superare questo valore ");
			return null;
		    }
		    if (i == 0) {
			r = c;
		    } else if (i == 1) {
			g = c;
		    } else if (i == 2) {
			b = c;
		    }
		} catch (NumberFormatException ne) {
		    log.error("stringRBGToColor# Uno dei codici del colore non è un interno, il codice colore deve rispettare il pattern R,G,B ");
		    return null;
		}
	    }
	    color = new Color(r, g, b);
	} else {
	    log.debug("stringRBGToColor# String rbg non popolata");
	}
	return color;
    }

    private void gracefullyDeleteFiles(File tempFile) {

	if (tempFile != null) {
	    try {
		if (tempFile.delete()) {
		    log.debug("gracefullyDeleteFiles# File cancellato");
		} else {
		    log.debug("gracefullyDeleteFiles# File non cancellato");
		}
	    } catch (Exception e) {
		log.error("gracefullyDeleteFiles# File non cancellato: {}", e);
	    }
	}
    }

    @Override
    public byte[] addImageToPDF(byte[] oggetto, byte[] qrcode, Integer page, Integer posX, Integer posY) {

	log.debug("addAnnotation# Start....");
	log.debug("addAnnotation# Istanzio il pdfReader....");
	File tempFile = null;
	byte[] data = null;
	try {
	    PdfReader reader = new PdfReader(oggetto);
	    tempFile = File.createTempFile("IMAGE-TEMP-FILE-", StringUtils.defaultIfEmpty("", UUID.randomUUID().toString() + ".pdf"));
	    FileOutputStream oFile = new FileOutputStream(tempFile, false);
	    PdfStamper stamper = new PdfStamper(reader, oFile, '\0', true);
	    Image image = Image.getInstance(qrcode);
	    PdfImage stream = new PdfImage(image, "", null);
	    stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
	    PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
	    image.setDirectReference(ref.getIndirectReference());
	    image.setAbsolutePosition(Float.valueOf(posX), reader.getPageSize(page).getHeight() - image.getHeight() - Float.valueOf(posY));
	    PdfContentByte over = stamper.getOverContent(page);
	    over.addImage(image);
	    stamper.close();
	    reader.close();
	    data = FileUtils.readFileToByteArray(tempFile);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return data;
    }
}
