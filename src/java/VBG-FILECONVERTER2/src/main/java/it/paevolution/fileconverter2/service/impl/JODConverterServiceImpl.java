package it.paevolution.fileconverter2.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.xml.XmpSerializer;
import org.jodconverter.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.fileconverter.ConvertResponse;
import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;
import it.paevolution.fileconverter2.service.DocumentConverterService;
import it.paevolution.fileconverter2.service.FileConverterService;
import it.paevolution.fileconverter2.service.GotenbergClient;
import it.paevolution.fileconverter2.util.Configurazione;
import it.paevolution.fileconverter2.util.Utils;

@Service
public class JODConverterServiceImpl extends BaseServiceImpl implements FileConverterService {

    private static final Logger log = LoggerFactory.getLogger(JODConverterServiceImpl.class);
    @Autowired
    private DocumentConverterService documentConverter;
    @Autowired
    private Configurazione configurazione;

    @Override
    public ConvertResponse convertBinaryContent(byte[] binaryData, FileInTypesEnum contentType, ConversionsSupportedEnum conversionType) {

	long t1 = System.currentTimeMillis();
	UUID idOne = UUID.randomUUID();
	log.info("convertBinaryContent# START. Processo con uuid: {}", idOne);
	ConvertResponse response = new ConvertResponse();
	String inputExtension = getInputExtension(contentType, response);
	// 
	String outputExtension = getOutputExtension(conversionType, response);
	File outputFile = null;
	if (!contentType.toString().equalsIgnoreCase(conversionType.toString())) {
	    // CASO 1 - CONTENT TYPE FILE INPUT != CONTENT TYPE FILE OUTPUT
	    log.debug("convertBinaryContent# 1. Conversione da {} a {}", contentType.toString(), conversionType.toString());
	    if (configurazione.isGotenbergAttivo() && conversionType == ConversionsSupportedEnum.PDF) {
		// CASO 1.1 - CONTENT TYPE FILE INPUT = HTML E CONTENT TYPE FILE OUT = PDF	
		log.debug("convertBinaryContent# 1.1 Conversione da {} a {}, conversione con Gotenberg", contentType.toString(),
			conversionType.toString());
		try {
		    response = this.convertToObjectUsingGotenberg(new ByteArrayInputStream(binaryData), contentType, outputExtension);
		} catch (Exception e) {
		    log.error("convertBinaryContent# Errore nella trasformazione in PDF da HTML con Gotenberg (UUID: {}): {} ", idOne, e);
		    throw new RuntimeException(e);
		} finally {
		    // provo a cancellare i file temporanei
		}
		// ritorno subito la response
		long t2 = System.currentTimeMillis();
		String time = "%d min, %d sec".formatted(TimeUnit.MILLISECONDS.toMinutes(t2 - t1),
			TimeUnit.MILLISECONDS.toSeconds(t2 - t1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(t2 - t1)));
		log.info("convertBinaryContent# END. Servito processo con uuid: {} in {}", idOne, time);
		return response;
	    } else {
		File inputFile = getInFile(binaryData, inputExtension, contentType);
		// CASO 1.2 - CONTENT TYPE FILE INPUT = HTML E CONTENT TYPE FILE OUT <> PDF uso le librerie OpenOffice
		// CONVERSIONE OPENOFFICE
		outputFile = getOutFile(outputExtension);
		log.debug("convertBinaryContent# 1.2 Conversione da {} a {} conversione con OpenOffice", contentType.toString(),
			conversionType.toString());
		documentConverter.convert(inputFile, outputFile, outputExtension);
	    }
	} else {
	    File inputFile = getInFile(binaryData, inputExtension, contentType);
	    outputFile = getOutFile(outputExtension);
	    // CASO 2 - CONVERSIONE DA PDF A PDF/A
	    log.debug("convertBinaryContent# 2. Conversione da {} a {}", contentType.toString(), conversionType.toString());
	    if (contentType.equals(FileInTypesEnum.PDF)) {
		try {
		    convertPDFToPdfA(inputFile, outputFile);
		} catch (Exception e) {
		    gracefullyDeleteFiles(inputFile, outputFile);
		    log.error("convertBinaryContent# Errore nella trasformazione in PDF/A del file PDF (UUID {}): {}", idOne, e);
		    throw new RuntimeException(e);
		}
	    } else {
		outputFile = inputFile;
	    }
	}
	if (null != outputFile) {
	    try {
		if (conversionType == ConversionsSupportedEnum.HTML) {
		    replaceHTML(outputFile);
		}
		byte[] fileBytes = getBytesFromFile(outputFile);
		response.setBinaryData(fileBytes);
		response.setFileName(outputFile.getName());
	    } catch (Exception e) {
		log.error(
			"convertBinaryContent# Errore nella conversione del file [binaryContent.length={},contentType={},conversionType={}]--> {} (UUID {})",
			new Object[] { (binaryData == null ? 0 : binaryData.length), contentType, conversionType, e, idOne });
		throw new RuntimeException(e);
	    } finally {
		gracefullyDeleteFiles(null, outputFile);
	    }
	}
	long t2 = System.currentTimeMillis();
	String time = "%d min, %d sec".formatted(TimeUnit.MILLISECONDS.toMinutes(t2 - t1),
		TimeUnit.MILLISECONDS.toSeconds(t2 - t1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(t2 - t1)));
	log.info("convertBinaryContent# END. Servito processo con uuid: {} in {}", idOne, time);
	return response;
    }

    private void gracefullyDeleteFiles(File inputFile, File outputFile) {

	try {
	    inputFile.delete();
	} catch (Exception e) {
	}
	try {
	    if (outputFile != null) {
		outputFile.delete();
	    }
	} catch (Exception e) {
	}
    }

    @Override
    public ConvertResponse convertStringContent(String content, FileInTypesEnum contentType, ConversionsSupportedEnum conversionType) {

	log.debug("convertStringContent# start...");
	ConvertResponse response = new ConvertResponse();
	String inputExtension = getInputExtension(contentType, response);
	String outputExtension = getOutputExtension(conversionType, response);
	File inputFile = null;
	File outputFile = null;
	try {
	    if (!contentType.toString().equalsIgnoreCase(conversionType.toString())) {
		log.debug("convertStringContent# 1. Conversione da {} a {}", contentType.toString(), conversionType.toString());
		if (configurazione.isGotenbergAttivo() && conversionType == ConversionsSupportedEnum.PDF) {		  
		    log.debug("convertStringContent# 1.1 Conversione da {} a {}, conversione con Gotenberg", contentType.toString(),
			    conversionType.toString());
		    response = this.convertToObjectUsingGotenberg(new ByteArrayInputStream(content.getBytes()), contentType, outputExtension);
		    // ritorno subito la response
		    return response;
		} else {
		    log.debug("convertStringContent# 1.2 Conversione da {} a {} conversione con OpenOffice", contentType.toString(),
			    conversionType.toString());
		    inputFile = getInFile(new StringBuffer(content), inputExtension, contentType);
		    outputFile = getOutFile(outputExtension);
		    documentConverter.convert(inputFile, outputFile, outputExtension);
		}
	    } else {
		inputFile = getInFile(new StringBuffer(content), inputExtension, contentType);
		log.debug("convertStringContent# 2. Conversione da {} a {}", contentType.toString(), conversionType.toString());
		outputFile = inputFile;
	    }
	    if (outputFile != null) {
		// try {
		if (conversionType == ConversionsSupportedEnum.HTML) {
		    replaceHTML(outputFile);
		}
		byte[] fileBytes = getBytesFromFile(outputFile);
		response.setBinaryData(fileBytes);
		response.setFileName(outputFile.getName());
	    }
	} catch (Exception e) {
	    log.error("convertStringContent# errore nella conversione del file [binaryContent.length={},contentType={},conversionType={}]--> {}",
		    new Object[] { (content == null ? 0 : content.length()), contentType, conversionType, e });
	    throw new RuntimeException(e);
	} finally {
	    gracefullyDeleteFiles(inputFile, outputFile);
	}
	log.debug("convertStringContent# end...");
	return response;
    }

    @Override
    public ConvertResponse convertToObjectUsingGotenberg(InputStream file, FileInTypesEnum contentType, String outputExtension) {

	ConvertResponse response = new ConvertResponse();
	try {
	    String nomeFile = "document-g-" + System.currentTimeMillis() + "." + outputExtension;
	    byte[] b = new GotenbergClient(configurazione).convertDoc(file, contentType, outputExtension);
	    response.setBinaryData(b);
	    response.setFileName(nomeFile);
	    response.setMimeType("application/pdf");
	    // success!
	} catch (Exception e) {
	    log.error("convertHtmlToPdf# ", e);
	    throw new RuntimeException(e);
	}
	return response;
    }

    /**
     * da un contenuto formato testo (HTML, RTF, TEXT, ECC...) crea i file di input e uscita
     * 
     * @param contenuto
     * @param contentType
     * @return
     */
    private File getInFile(StringBuffer contenuto, String inputExtension, FileInTypesEnum contentType) {

	if (contentType.equals(FileInTypesEnum.HTML)) {
	    log.debug(
		    "getInFile(StringBuffer contenuto, String inputExtension, FileInTypesEnum contentType)# tipo contenuto input HTML, uso codifica UTF-8");
	    try {
		String htmlContentFixed = completeHtml(contenuto, contentType);
		File inputFile = File.createTempFile("documentIn", "." + inputExtension);
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(inputFile), "UTF-8");
		writer.write(new String(htmlContentFixed.getBytes(), "UTF-8"));
		writer.flush();
		writer.close();
		return inputFile;
	    } catch (Exception e) {
		log.error(e.getMessage());
		throw new RuntimeException(e);
	    }
	} else {
	    log.debug(
		    "getInFile(StringBuffer contenuto, String inputExtension, FileInTypesEnum contentType)# tipo contenuto input non HTML, non uso codifica UTF-8");
	    try {
		String htmlContentFixed = completeHtml(contenuto, contentType);
		File inputFile = File.createTempFile("documentIn", "." + inputExtension);
		FileOutputStream fos = new FileOutputStream(inputFile);
		PrintStream ps = new PrintStream(fos);
		ps.println(htmlContentFixed);
		fos.flush();
		fos.close();
		return inputFile;
	    } catch (Exception e) {
		log.error(e.getMessage());
		throw new RuntimeException(e);
	    }
	}
    }

    private File getOutFile(String outputExtension) {

	try {
	    File outputFile = File.createTempFile("documentOut", "." + outputExtension);
	    return outputFile;
	} catch (Exception e) {
	    log.error(e.getMessage());
	    throw new RuntimeException(e);
	}
    }

    /**
     * da un contenuto formato binario (byte[]) crea i file di input e uscita
     * 
     * @param contenutoFileIn
     * @return
     */
    private static File getInFile(byte[] contenutoFileIn, String inputExtension, FileInTypesEnum tipofileIn) {

	if (tipofileIn.equals(FileInTypesEnum.HTML)) {
	    log.debug("getInFile# tipo file input HTML, uso codifica UTF-8");
	    // In caso di file input html devo usare la codifica UTF-8
	    try {
		File inputFile = File.createTempFile("documentIn", "." + inputExtension);
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(inputFile), "UTF-8");
		if (tipofileIn != null) {
		    contenutoFileIn = replacePerSchedeDinamiche(contenutoFileIn);
		}
		writer.write(new String(contenutoFileIn, "UTF-8"));
		writer.flush();
		writer.close();
		return inputFile;
	    } catch (Exception e) {
		log.error(e.getMessage());
		throw new RuntimeException(e);
	    }
	} else {
	    log.debug("getInFile# tipo file input non HTML");
	    try {
		File inputFile = File.createTempFile("documentIn", "." + inputExtension);
		FileOutputStream fos = new FileOutputStream(inputFile);
		// if (tipofileIn != null) {
		// if (tipofileIn.equals(FileInTypesEnum.HTML)) {
		// contenutoFileIn = replacePerSchedeDinamiche(contenutoFileIn);
		// }
		// }
		fos.write(contenutoFileIn);
		fos.flush();
		fos.close();
		return inputFile;
	    } catch (Exception e) {
		log.error(e.getMessage());
		throw new RuntimeException(e);
	    }
	}
    }

    public static void main(String[] args) throws Exception {

	byte[] io = IOUtils.toByteArray(new FileInputStream(new File("D:/tmp/bandi_orig.html")));
	// byte[] out = replacePerSchedeDinamiche(io);
	File f = getInFile(io, "html", FileInTypesEnum.HTML);
	System.out.println(f);
    }

    private static byte[] replacePerSchedeDinamiche(byte[] contenutoFileIn) {

	String s = null;
	try {
	    s = new String(contenutoFileIn, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error("non è possibile generare il contenuto html in utf8 - {}", e);
	    s = new String(contenutoFileIn);
	}
	if (StringUtils.isNotBlank(s)) {
	    // <div id='datiDinamici'>
	    // <div class='titoloSchedaDinamica'>
	    // <div class="DatiDinamici" id="renderer">
	    s = s.replaceAll("<div id='datiDinamici'>", "");
	    s = s.replaceAll("<div class='titoloSchedaDinamica'>", "");
	    s = s.replaceAll("<div class=\"DatiDinamici\" id=\"renderer\">", "");
	}
	byte[] out = null;
	try {
	    out = s.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error("non è possibile generare il contenuto in bytes utf8 - {}", e);
	    out = s.getBytes();
	}
	return out;
    }

    private void replaceHTML(File outputFile) throws Exception {

	// ..eseguo solamente se sono stati specificati l'espressione regolare per
	// effettuare le sostituzioni
	if (!(null == configurazione.getExportHtmlMatcherRegexp() || configurazione.getExportHtmlMatcherRegexp().equals(""))) {
	    String htmlFileContent = readFileAsString(outputFile);
	    String replace = configurazione.getExportHtmlReplaceString();
	    if (replace == null) {
		replace = "";
	    }
	    htmlFileContent = Utils.replace(htmlFileContent, configurazione.getExportHtmlMatcherRegexp(), "");
	    writeStringAsFile(outputFile, htmlFileContent);
	}
    }

    private String readFileAsString(File file) throws Exception {

	return FileUtils.readFileToString(file, "UTF-8");
    }

    private void writeStringAsFile(File file, String data) throws Exception {

	FileUtils.writeStringToFile(file, data, "UTF-8");
    }

    private void convertPDFToPdfA(File in, File out) throws Exception {

	PDDocument doc = PDDocument.load(in);
	try {
	    PDDocumentCatalog catalog = doc.getDocumentCatalog();
	    // ✅ crea metadata XMP moderno
	    XMPMetadata xmp = XMPMetadata.createXMPMetadata();
	    // ✅ schema PDF/A
	    PDFAIdentificationSchema pdfaSchema = xmp.createAndAddPDFAIdentificationSchema();
	    pdfaSchema.setPart(1);
	    pdfaSchema.setConformance("B");
	    // ✅ serializzazione
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    new XmpSerializer().serialize(xmp, baos, true);
	    // ✅ applica metadata al documento
	    PDMetadata metadata = new PDMetadata(doc);
	    metadata.importXMPMetadata(baos.toByteArray());
	    catalog.setMetadata(metadata);
	    doc.save(out);
	} finally {
	    doc.close();
	}
    }
}
