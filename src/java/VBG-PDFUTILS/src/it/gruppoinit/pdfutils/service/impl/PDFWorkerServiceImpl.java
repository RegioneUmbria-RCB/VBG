package it.gruppoinit.pdfutils.service.impl;

import it.gruppoinit.commons.tasks.TemporaryFileCleanerJob;
import it.gruppoinit.dss.DSSWSClient;
import it.gruppoinit.dss.rest.DSSRestClient;
import it.gruppoinit.dss.rest.models.FileOriginaleDSSBean;
import it.gruppoinit.dss.wsclient.WsValidationReport;
import it.gruppoinit.pdfutils.FontTypeCostant;
import it.gruppoinit.pdfutils.Utilities;
import it.gruppoinit.pdfutils.domain.PDFMappature;
import it.gruppoinit.pdfutils.schemas.messages.DatiPDFType;
import it.gruppoinit.pdfutils.schemas.messages.Font;
import it.gruppoinit.pdfutils.schemas.messages.Layer;
import it.gruppoinit.pdfutils.service.ConfigurazioneService;
import it.gruppoinit.pdfutils.service.PDFWorkerService;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.fop.pdf.PDFImageXObject;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.pdmodel.markedcontent.PDPropertyList;
import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.apache.pdfbox.util.PDFImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PDFWorkerServiceImpl implements PDFWorkerService {

    private static final String NULL_VALUE = "(NULL)";
    private static final Logger log = LoggerFactory.getLogger(PDFWorkerServiceImpl.class);
    private ConfigurazioneService configurazioneService;

    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Override
    public void precompilaPDF(String token, File xmlFileIn, File[] pdf) {

	TemporaryFileCleanerJob.getListaFiles().add(xmlFileIn.toURI());
	if (log.isDebugEnabled()) {
	    log.debug("precompilaPDF# recupero della configurazione");
	}
	Map<String, PDFMappature> conf = new HashMap<String, PDFMappature>();
	try {
	    conf = configurazioneService.loadConfigurazione(token);
	} catch (SQLException e) {
	    log.error("precompilaPDF# errore nel recupero delle configurazioni: {}", e);
	}
	if (log.isDebugEnabled()) {
	    log.debug("precompilaPDF# per ogni file recupero i campi e tento di settarne il valore utilizzando l'xml");
	}
	try {
	    InputSource source = new InputSource(new FileInputStream(xmlFileIn));
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(source);
	    XPathFactory xpathFactory = XPathFactory.newInstance();
	    XPath xpath = xpathFactory.newXPath();
	    for (File f : pdf) {
		TemporaryFileCleanerJob.getListaFiles().add(f.toURI());
		if (log.isDebugEnabled()) {
		    log.debug("precompilaPDF# precompilo il file {}", f);
		}
		try {
		    precompilaFile(f, conf, xpath, doc);
		} catch (IOException ioe) {
		    log.error("precompilaPDF# errore nella precompilazione del file {}: {}", f, ioe);
		}
	    }
	} catch (Exception e) {
	    log.error("precompilaPDF# errore nella precompilazione {}", e);
	}
    }

    @Override
    public File appendTextAsLayer(List<String> listaTesti, File pdf, PDDocument document, PDPage targetPage, Layer layer, Font font)
	    throws IOException {

	log.debug("appendTextAsLayer# Ricavo il font, se impostato...");
	PDTrueTypeFont fontTypeCustom = getFontTypeCustom(font, document);
	PDType1Font fontType = new PDType1Font();
	if (fontTypeCustom == null) {
	    log.debug("appendTextAsLayer# Font custom non trovato, cerco tra gli standard");
	    fontType = getFontTypeStandard(font);
	}
	Integer fontDimention = getFontDimention(font);
	log.debug("appendTextAsLayer# carico proprietà del PDDocument ");
	PDDocumentCatalog catalog = document.getDocumentCatalog();
	PDOptionalContentProperties ocprops = catalog.getOCProperties();
	if (ocprops == null) {
	    ocprops = new PDOptionalContentProperties();
	    catalog.setOCProperties(ocprops);
	}
	if (ocprops.hasGroup(layer.getNomeLayer())) {
	    throw new IllegalArgumentException("Il layer " + layer.getNomeLayer() + " già esiste nel documento");
	}
	log.debug("appendTextAsLayer# Creao il layer vuoto e lo applico al PDDocument");
	PDOptionalContentGroup _layer = new PDOptionalContentGroup(layer.getNomeLayer());
	ocprops.addGroup(_layer);
	PDResources resources = targetPage.findResources();
	if (resources == null) {
	    resources = new PDResources(new COSDictionary());
	    targetPage.setResources(resources);
	}
	PDPropertyList props = resources.getProperties();
	if (props == null) {
	    props = new PDPropertyList();
	    resources.setProperties(props);
	}
	// Find first free resource name with the pattern "MC<index>"
	int index = 0;
	PDOptionalContentGroup ocg;
	COSName resourceName;
	do {
	    resourceName = COSName.getPDFName("MC" + index);
	    ocg = props.getOptionalContentGroup(resourceName);
	    index++;
	} while (ocg != null);
	props.putMapping(resourceName, _layer);
	PDPageContentStream contentStream = new PDPageContentStream(document, targetPage, true, false);
	contentStream.beginMarkedContentSequence(COSName.ACTUAL_TEXT, resourceName);
	if (fontTypeCustom != null) {
	    contentStream.setFont(fontTypeCustom, fontDimention);
	} else {
	    contentStream.setFont(fontType, fontDimention);
	}
	log.debug("appendTextAsLayer# Applico il testo al layer {}", layer.getNomeLayer());
	int fattoreCorrettivoY = 1;
	int interlinea = 2;
	for (String testo : listaTesti) {
	    contentStream.beginText();
	    contentStream.moveTextPositionByAmount(layer.getX(), (layer.getY() - fattoreCorrettivoY));
	    contentStream.drawString(testo);
	    contentStream.endText();
	    fattoreCorrettivoY += (fattoreCorrettivoY + fontDimention - 1);
	    fattoreCorrettivoY = fattoreCorrettivoY * interlinea;
	    // y += 20;
	}
	contentStream.endMarkedContentSequence();
	contentStream.close();
	try {
	    document.save(pdf);
	} catch (COSVisitorException e) {
	    throw new RuntimeException(e);
	}
	return pdf;
    }

    private Integer getFontDimention(Font font) {

	if (font != null && font.getDimensione() != null) {
	    log.debug("getFontDimention# {}", font.getDimensione());
	    return font.getDimensione();
	}
	log.debug("getFontDimention# {} Default", 12);
	return 12;
    }

    private PDType1Font getFontTypeStandard(Font font) {

	if (font != null && StringUtils.isNotBlank(font.getFontType())) {
	    if (FontTypeCostant.FONT_TIMES_ROMAN.equals(font.getFontType())) {
		log.debug("getFontType# {}", font.getFontType());
		return PDType1Font.TIMES_ROMAN;
	    } else if (FontTypeCostant.FONT_TIMES_ROMAN.equals(font.getFontType())) {
		log.debug("getFontType# {}", font.getFontType());
		return PDType1Font.HELVETICA;
	    } else {
		log.debug("getFontType# Font non trovato. Default {}", "TIMES_ROMAN");
		return PDType1Font.TIMES_ROMAN;
	    }
	}
	log.debug("getFontType# Font non impostato.Default {}", "TIMES_ROMAN");
	return PDType1Font.TIMES_ROMAN;
    }

    private PDTrueTypeFont getFontTypeCustom(Font font, PDDocument document) throws IOException {

	if (font != null && StringUtils.isNotBlank(font.getFontType())) {
	    if (FontTypeCostant.FONT_GILL_SANS_MT.equals(font.getFontType())) {
		log.debug("getFontTypeCustom# {}", font.getFontType());
		InputStream fontStream = getClass().getClassLoader().getResourceAsStream("font/gil.ttf");
		PDTrueTypeFont _font = PDTrueTypeFont.loadTTF(document, fontStream);
		return _font;
	    } else {
		log.debug("getFontTypeCustom# Font non trovato.");
		return null;
	    }
	}
	log.debug("getFontTypeCustom# Font non impostato.");
	return null;
    }

    public static void main(String[] args) throws Exception {

	PDDocument document = null;
	File pdf = new File("d:/temp/pdfnested.pdf");
	document = PDDocument.load(pdf);
	PDDocumentNameDictionary names = new PDDocumentNameDictionary(document.getDocumentCatalog());
	PDEmbeddedFilesNameTreeNode ef = names.getEmbeddedFiles();
	Map<String, COSObjectable> names2 = ef.getNames();
	for (Entry<String, COSObjectable> s : names2.entrySet()) {
	    System.out.println(s.getKey());
	    PDComplexFileSpecification p = (PDComplexFileSpecification) s.getValue();
	    PDEmbeddedFile embeddedFile = p.getEmbeddedFile();
	    System.out.println(p.getFile() + "==>" + p.getFileDescription());
	    FileOutputStream fos = new FileOutputStream("d:/temp/PDF_" + p.getFile());
	    IOUtils.write(embeddedFile.getByteArray(), fos);
	    IOUtils.closeQuietly(fos);
	}
	//	InputSource source = new InputSource(new FileInputStream(new File("C:\\Sviluppo\\workspace\\pdfutils-webapp\\resources\\domandaSTC.xml")));
	//	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	//	DocumentBuilder db = dbf.newDocumentBuilder();
	//	Document doc = db.parse(source);
	//	XPathFactory xpathFactory = XPathFactory.newInstance();
	//	XPath xpath = xpathFactory.newXPath();
	//	String result = xpath.evaluate("/InserimentoPraticaNLARequest/dettaglioPratica/richiedente/anagrafica/nome", doc);
	//	System.out.println(result);
	//	String script = "if(#input#=='F'){#output#='Femmina';}else{#output#='Maschio';}";
	//	System.out.println(conversioneJs("F", script));
	//	System.out.println(conversioneDaProperties("G478", "resources/COMUNI.properties", "testlabel"));
	//	System.out.println(conversioneDecodificaValori("A1", "A01##>Allegato 1|A1##>Allegato2"));
	//	System.out.println(conversioneRegexp("2013-12-02+01:00", "([0-9]{4})-([0-9]{2})-([0-9]{2})[+-][0-9]{2}:[0-9]{2}", "$3/$2/$1"));
	//	System.out.println("-----------------------------NULL---------------------------------------");
	//	System.out.println(conversioneJs(null, script));
	//	System.out.println(conversioneDaProperties(null, "resources/COMUNI.properties", "testlabel"));
	//	System.out.println(conversioneDecodificaValori(null, "A01##>Allegato 1|A1##>Allegato2"));
	//	System.out.println(conversioneRegexp(null, "([0-9]{4})-([0-9]{2})-([0-9]{2})[+-][0-9]{2}:[0-9]{2}", "$3/$2/$1"));
	//	System.out.println("-----------------------------EMPTY---------------------------------------");
	//	System.out.println(conversioneJs("", script));
	//	System.out.println(conversioneDaProperties("", "COMUNI2.properties", "testlabel1"));
	//	System.out.println(conversioneDecodificaValori("", "A01##>Allegato 1|A1##>Allegato2"));
	//	System.out.println(conversioneRegexp("", "([0-9]{4})-([0-9]{2})-([0-9]{2})[+-][0-9]{2}:[0-9]{2}", "$3/$2/$1"));
	//	System.out.println("-----------------------------WRONG VALUES---------------------------------------");
	//	System.out.println(conversioneJs("h", script));
	//	System.out.println(conversioneDaProperties("", "resources/COMUNI.properties", "testlabel"));
	//	System.out.println(conversioneDecodificaValori("", "A01###>Allegato 1$A1##>Allegato2"));
	//	System.out.println(conversioneRegexp("", "([0-9]{4})-([0-9]{2)-([0-9]{2})[+-][0-9]{2}:[0-9]{2}", "$32/$1"));
    }

    private void precompilaFile(File pdf, Map<String, PDFMappature> conf, XPath xpath, Document doc)
	    throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, COSVisitorException {

	if (log.isDebugEnabled()) {
	    log.debug("precompilaFile# entro nel metodo");
	}
	PDDocument document = null;
	try {
	    document = PDDocument.load(pdf);
	    if (log.isDebugEnabled()) {
		log.debug("precompilaFile# recupero il catalogo");
	    }
	    PDDocumentCatalog catalog = document.getDocumentCatalog();
	    if (log.isDebugEnabled()) {
		log.debug("precompilaFile# recupero l'acroform");
	    }
	    PDAcroForm form = catalog.getAcroForm();
	    if (form != null) {
		if (log.isDebugEnabled()) {
		    log.debug("precompilaFile# AcroForm non è nullo ciclo i campi da compilare");
		}
		List<PDField> fields = form.getFields();
		if (fields != null) {
		    for (PDField f : fields) {
			String fieldName = f.getFullyQualifiedName();
			if (log.isDebugEnabled()) {
			    log.debug("precompilaFile# processo il campo {}", fieldName);
			}
			PDFMappature m = conf.get(fieldName);
			String percorsoXml = fieldName;
			if (m != null) {
			    percorsoXml = m.getXpath();
			    if (StringUtils.isBlank(percorsoXml)) {
				if (log.isDebugEnabled()) {
				    log.debug("precompilaFile# il campo non è presente tra le label");
				}
				percorsoXml = fieldName;
			    }
			}
			try {
			    Node node = (Node) xpath.evaluate(percorsoXml, doc, XPathConstants.NODE);
			    if (node != null) {
				String valore = getValueFromXml(percorsoXml, xpath, doc);
				valore = performValueConversion(valore, m);
				// if (StringUtils.isNotBlank(valore)) {
				f.setValue(valore);
				// }
			    }
			} catch (Exception e) {
			    log.error("precompilaFile# errore nel recupero del parametro {},{}", percorsoXml, e);
			}
		    }
		}
		document.save(pdf);
	    }
	} finally {
	    if (document != null) {
		try {
		    document.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    /**
     * 
     * metodo per la sostituzione di mappature di tipo immagine il cui contenuto codificato in BASE64 è estratto dal
     * campo XML mappato. Il metodo non è utilizzato ma non va rimosso in quanto è uno studio di fattibilità per
     * funzionalità richieste per la stampa dell'avviso di pagamento AGID.
     * Da testare. Occorre anche sperimentare la sostituzione di immagini su altre immagini segnaposto in base al nome che l'immagine ha all'interno del documento PDF.
     * @param pdf
     * @param conf
     * @param xpath
     * @param doc
     * @throws IOException
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws COSVisitorException
     */
    private void precompilaFileConImmagini(File pdf, Map<String, PDFMappature> conf, XPath xpath, Document doc)
	    throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, COSVisitorException {

	if (log.isDebugEnabled()) {
	    log.debug("precompilaFile2# entro nel metodo");
	}
	PDDocument document = null;
	try {
	    document = PDDocument.load(pdf);
	    PDDocumentCatalog catalog = document.getDocumentCatalog();
	    PDAcroForm form = catalog.getAcroForm();
	    COSDictionary dic = catalog.getCOSDictionary();
	    Set<String> keySet = new HashSet<String>(conf.keySet());
	    Iterator<String> keys = keySet.iterator();
	    while (keys.hasNext()) {
		String key = keys.next();
		PDFMappature m = conf.get(key);
		String percorsoXml = key;
		String valore = null;
		if (m != null) {
		    percorsoXml = m.getXpath();
		    if (StringUtils.isBlank(percorsoXml)) {
			if (log.isDebugEnabled()) {
			    log.debug("precompilaFile# il campo non ha un XPath configurato");
			}
			percorsoXml = key;
		    }
		}
		try {
		    Node node = (Node) xpath.evaluate(percorsoXml, doc, XPathConstants.NODE);
		    if (node != null) {
			valore = getValueFromXml(percorsoXml, xpath, doc);
		    }
		} catch (Exception e) {
		    log.error("precompilaFile# errore nel recupero del parametro {},{}", percorsoXml, e);
		}
		//posizionamento immagini, nuovo tipo di mappatura da aggiungere
		if (m.getDecodTipo().equals("IM")) {
		    //posizionamento immagine per coordinate x,y
		    Point pos = this.recuperaCoordinateImmagine(m.getDecodFormatoInput());
		    //decodifica bytes immagine da valore bsase64
		    byte[] bytes = Utilities.base64Decode(valore);
		    //prova sulla prima pagina x ora
		    PDPage page = (PDPage) document.getDocumentCatalog().getAllPages().get(0);
		    PDJpeg jpeg = new PDJpeg(document, new ByteArrayInputStream(bytes));
		    PDPageContentStream stream = new PDPageContentStream(document, page);
		    stream.drawImage(jpeg, (float) pos.getX(), (float) pos.getY());
		    stream.close();
		}
		//popolamento acroform
		else {
		    PDField f = form.getField(key);
		    valore = performValueConversion(valore, m);
		    f.setValue(valore);
		}
	    }
	    document.save(pdf);
	    /*
	    if (form != null) {
	    if (log.isDebugEnabled()) {
	        log.debug("precompilaFile# AcroForm non è nullo ciclo i campi da compilare");
	    }
	    List<PDField> fields = form.getFields();
	    if (fields != null) {
	        for (PDField f : fields) {
	    	String fieldName = f.getFullyQualifiedName();
	    	if (log.isDebugEnabled()) {
	    	    log.debug("precompilaFile# processo il campo {}", fieldName);
	    	}
	    	PDFMappature m = conf.get(fieldName);
	    	String percorsoXml = fieldName;
	    	if (m != null) {
	    	    percorsoXml = m.getXpath();
	    	    if (StringUtils.isBlank(percorsoXml)) {
	    		if (log.isDebugEnabled()) {
	    		    log.debug("precompilaFile# il campo non è presente tra le label");
	    		}
	    		percorsoXml = fieldName;
	    	    }
	    	}
	    	try {
	    	    Node node = (Node) xpath.evaluate(percorsoXml, doc, XPathConstants.NODE);
	    	    if (node != null) {
	    		String valore = getValueFromXml(percorsoXml, xpath, doc);
	    		valore = performValueConversion(valore, m);
	    		// if (StringUtils.isNotBlank(valore)) {
	    		f.setValue(valore);
	    		// }
	    	    }
	    	} catch (Exception e) {
	    	    log.error("precompilaFile# errore nel recupero del parametro {},{}", percorsoXml, e);
	    	}
	        }
	    }
	    document.save(pdf);
	    }
	    */
	} finally {
	    if (document != null) {
		try {
		    document.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    private static String performValueConversion(String valore, PDFMappature m) {

	if (m == null) {
	    return valore;
	}
	String tipoConversione = StringUtils.defaultString(m.getDecodTipo()).trim();
	if (StringUtils.isNotBlank(tipoConversione)) {
	    if (tipoConversione.equalsIgnoreCase("RE")) {
		return conversioneRegexp(valore, m.getDecodFormatoInput(), m.getDecodFormatoOutput());
	    } else if (tipoConversione.equalsIgnoreCase("JS")) {
		return conversioneJs(valore, m.getDecodFormatoInput());
	    } else if (tipoConversione.equalsIgnoreCase("DV")) {
		return conversioneDecodificaValori(valore, m.getDecodFormatoInput());
	    } else if (tipoConversione.equalsIgnoreCase("PF")) {
		return conversioneDaProperties(valore, m.getDecodFormatoInput(), m.getLabel());
	    } else {
		log.error("performValueConversion# tipo di conversione sconosciuto {}", tipoConversione);
		return valore;
	    }
	}
	return valore;
    }

    private static Map<String, Properties> sostituzioniDaFileCached = new HashMap<String, Properties>();

    private static String conversioneDaProperties(String valore, String decodFormatoInput, String label) {

	Properties p = sostituzioniDaFileCached.get(label);
	if (p == null) {
	    p = caricaProperties(decodFormatoInput);
	    sostituzioniDaFileCached.put(label, p);
	}
	if (p != null) {
	    if (StringUtils.isBlank(valore)) {
		return p.getProperty(NULL_VALUE);
	    } else {
		return p.getProperty(valore);
	    }
	}
	return valore;
    }

    private static Properties caricaProperties(String file) {

	InputStream in = null;
	Properties result = null;
	try {
	    in = PDFWorkerServiceImpl.class.getClassLoader().getResourceAsStream(file);
	    result = new Properties();
	    result.load(in);
	    return result;
	} catch (Exception e) {
	    log.error("Errore durante il caricamento del file {}: {}", file, e.getMessage());
	    // throw new RuntimeException("Errore durante il caricamento della configurazione" + e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
	return result;
    }

    private static String conversioneDecodificaValori(String valore, String decodFormatoInput) {

	String listaValori = StringUtils.defaultString(decodFormatoInput).trim();
	if (StringUtils.isNotBlank(StringUtils.defaultString(decodFormatoInput).trim())) {
	    Map<String, String> m = recuperaValoriDV(listaValori);
	    if (log.isDebugEnabled()) {
		log.debug("conversioneDecodificaValori# mappa valori {}", m);
	    }
	    if (!m.isEmpty()) {
		if (StringUtils.isBlank(valore)) {
		    // GESTIRE (NULL)
		    return m.get(NULL_VALUE);
		} else {
		    return m.get(valore);
		}
	    }
	}
	return valore;
    }

    private static Map<String, String> recuperaValoriDV(String listaValori) {

	Map<String, String> result = new HashMap<String, String>();
	String[] listaValoriAr = listaValori.split("\\|");
	for (String vs : listaValoriAr) {
	    String[] codDesc = vs.split("##>");
	    String codice = null;
	    String descrizione = null;
	    if (codDesc.length == 2) {
		codice = codDesc[0];
		descrizione = codDesc[1];
	    } else {
		codice = codDesc[0];
		descrizione = codDesc[0];
	    }
	    result.put(codice, descrizione);
	}
	return result;
    }

    private Point recuperaCoordinateImmagine(String paramValue) {

	Point p = null;
	if (StringUtils.isNotBlank(paramValue)) {
	    String[] splitted = paramValue.split(",");
	    if (splitted.length == 2) {
		boolean valid = false;
		Integer[] coords = new Integer[2];
		splitted[0] = splitted[0].trim();
		if (NumberUtils.isDigits(splitted[0])) {
		    coords[0] = Integer.parseInt(splitted[0]);
		    splitted[1] = splitted[1].trim();
		    if (NumberUtils.isDigits(splitted[1])) {
			coords[1] = Integer.parseInt(splitted[1]);
			valid = true;
		    } else {
			log.error("recuperaCoordinateImmagine - valore della coordinata y non valido: " + splitted[1]);
		    }
		} else {
		    log.error("recuperaCoordinateImmagine - valore della coordinata x non valido: " + splitted[0]);
		}
		if (valid) {
		    p = new Point(coords[0], coords[1]);
		}
	    } else {
		log.error("recuperaCoordinateImmagine - valore del parametro non valido: " + paramValue);
	    }
	}
	return p;
    }

    private static ScriptEngine createEngine() {

	log.debug("ModelliDinamiciFormuleServiceImpl: Inizializzo jsEngine");
	ScriptEngineManager mgr = new ScriptEngineManager();
	ScriptEngine jsEngine = mgr.getEngineByExtension("js");
	return jsEngine;
    }

    private static String conversioneJs(String valore, String script) {

	if (StringUtils.isNotBlank(script)) {
	    script = script.replaceAll("#input#", "input_var_69").replaceAll("#output#", "output_var_69");
	    String scriptDaEseguire = new String(script);
	    ScriptEngine jsEngine = createEngine();
	    String output_var = "";
	    jsEngine.put("input_var_69", StringUtils.defaultString(valore));
	    jsEngine.put("output_var_69", output_var);
	    String imports = setImports();
	    String functions = setFunctions();
	    scriptDaEseguire = imports + functions + scriptDaEseguire;
	    log.debug("createEngineAndExecute: {}", scriptDaEseguire);
	    String val = valore;
	    try {
		jsEngine.eval(scriptDaEseguire);
		val = (String) jsEngine.get("output_var_69");
	    } catch (ScriptException e) {
		log.error("getValoreFromScript: valore:{}, script:{}==> ex:{}", new Object[] { valore, script, e.getMessage() });
	    }
	    return val;
	} else {
	    return valore;
	}
    }

    private static String setImports() {

	String imports = "";
	// imports += "\nimportPackage(Packages." + it.gruppoinit.pdfutils.Utilities.class.getPackage().getName() + ");";
	return imports;
    }

    private static String setFunctions() {

	String functions = "";
	//	"\nfunction getService(serviceName){";
	//	functions += "	\nvar result=null;";
	//	functions += "	\nresult = ContextLoader.getCurrentWebApplicationContext().getBean(serviceName);";
	//	functions += "	\nreturn result;";
	//	functions += "}\n";
	return functions;
    }

    private static String conversioneRegexp(String valore, String decodFormatoInput, String decodFormatoOutput) {

	try {
	    boolean isMatch = Pattern.matches(StringUtils.defaultString(decodFormatoInput).trim(), StringUtils.defaultString(valore).trim());
	    if (isMatch) {
		Pattern datePattern = Pattern.compile(decodFormatoInput.trim());
		Matcher matcher = datePattern.matcher(valore.trim());
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(sb, decodFormatoOutput.trim());
		}
		matcher.appendTail(sb);
		return sb.toString();
	    }
	} catch (Exception e) {
	    log.error("conversioneRegexp# {}", e);
	}
	return valore;
    }

    @Override
    public List<DatiPDFType> pdfToModel(String token, File pdf) throws Exception {

	List<DatiPDFType> dati = new ArrayList<DatiPDFType>();
	TemporaryFileCleanerJob.getListaFiles().add(pdf.toURI());
	boolean almenoUnCampo = false;
	PDDocument document = null;
	try {
		
		DataHandler clearFile;
	    if (StringUtils.defaultString(pdf.getName()).toLowerCase().endsWith(".p7m")) {
		DataHandler dh = new DataHandler(new FileDataSource(pdf));		
			
		FileOriginaleDSSBean fileoriginale = new DSSRestClient(configurazioneService.getURLRestFirmaDigitale()).checkFirmaScaricaFileNonFirmato(dh, pdf.getName());			
		ByteArrayDataSource dataSource = new ByteArrayDataSource(fileoriginale.getContent(), fileoriginale.getContentType());			
		clearFile = new DataHandler(dataSource);		

		File pdf2 = File.createTempFile("PDF-UTILS-" + pdf.getName(), ".pdf");
		clearFile.writeTo(new FileOutputStream(pdf2));
		TemporaryFileCleanerJob.getListaFiles().add(pdf2.toURI());
		document = PDDocument.load(pdf2);
	    } else {
		document = PDDocument.load(pdf);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("precompilaFile# recupero il catalogo");
	    }
	    PDDocumentCatalog catalog = document.getDocumentCatalog();
	    if (log.isDebugEnabled()) {
		log.debug("precompilaFile# recupero l'acroform");
	    }
	    PDAcroForm form = catalog.getAcroForm();
	    if (form != null) {
		if (log.isDebugEnabled()) {
		    log.debug("precompilaFile# AcroForm non è nullo ciclo i campi da compilare");
		}
		List<PDField> fields = null;
		fields = form.getFields();
		if (fields != null) {
		    for (PDField f : fields) {
			if (!(f instanceof PDSignatureField)) {
			    almenoUnCampo = true;
			    DatiPDFType dato = new DatiPDFType();
			    dato.setCodice(f.getFullyQualifiedName());
			    if (!StringUtils.defaultString(f.getValue()).equalsIgnoreCase("þÿ")) {
				dato.getValore().add(f.getValue());
			    } else {
				dato.getValore().add("");
			    }
			    dati.add(dato);
			}
		    }
		}
	    }
	} finally {
	    if (document != null) {
		try {
		    document.close();
		} catch (Exception e) {
		}
	    }
	}
	if (almenoUnCampo) {
	    return dati;
	}
	return null;
    }

    private String getValueFromXml(String xpathParam, XPath xpath, Document document)
	    throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

	String result = xpath.evaluate(xpathParam, document);
	return result;
    }

    private void dumpForm(PDAcroForm form) throws IOException {

	List<PDField> fields = form.getFields();
	for (PDField f : fields) {
	    if (f.getFullyQualifiedName().equalsIgnoreCase("NOME_CENTRO_COM")) {
		f.setValue("IPERCOOP COLLESTRADA");
	    }
	    //System.out.println("----------------------------");
	    System.out.println("alternateFieldName: " + f.getAlternateFieldName());
	    System.out.println("partialName: " + f.getPartialName());
	    System.out.println("fullyQualifiedName: " + f.getFullyQualifiedName());
	    // System.out.println("dictionary: " + f.getDictionary());
	    System.out.println("value:" + f.getValue());
	    System.out.println("type:" + f.getFieldType());
	    System.out.println("fieldflag: " + f.getFieldFlags());
	    System.out.println("kids:" + f.getKids());
	    System.out.println("noeExport?:" + f.isNoExport());
	    System.out.println("actions:" + f.getActions());
	    System.out.println("----------------------------");
	}
    }

    private void validate(File f) throws Exception {

	ValidationResult result = null;
	FileDataSource fd = new FileDataSource(f);
	PreflightParser parser = new PreflightParser(fd);
	try {
	    /* Parse the PDF file with PreflightParser that inherits from the NonSequentialParser.
	     * Some additional controls are present to check a set of PDF/A requirements. 
	     * (Stream length consistency, EOL after some Keyword...)
	     */
	    parser.parse();
	    /* Once the syntax validation is done, 
	     * the parser can provide a PreflightDocument 
	     * (that inherits from PDDocument) 
	     * This document process the end of PDF/A validation.
	     */
	    PreflightDocument document = parser.getPreflightDocument();
	    document.validate();
	    // Get validation result
	    result = document.getResult();
	    document.close();
	} catch (SyntaxValidationException e) {
	    /* the parse method can throw a SyntaxValidationException 
	     *if the PDF file can't be parsed.
	     */
	    result = e.getResult();
	}
	// display validation result
	if (result.isValid()) {
	    System.out.println("The file " + f + " is a valid PDF/A-1b file");
	} else {
	    System.out.println("The file" + f + " is not valid, error(s) :");
	    for (ValidationError error : result.getErrorsList()) {
		System.out.println(error.getErrorCode() + " : " + error.getDetails());
	    }
	}
    }
}
