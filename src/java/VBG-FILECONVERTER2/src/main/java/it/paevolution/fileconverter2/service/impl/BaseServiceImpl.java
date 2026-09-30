package it.paevolution.fileconverter2.service.impl;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.fileconverter.ConvertResponse;
import it.paevolution.fileconverter2.service.BaseService;
import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;

public class BaseServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

    protected String getOutputExtension(ConversionsSupportedEnum tipoConversione, ConvertResponse responseFile) {

	String outputExtension = "";
	switch (tipoConversione) {
	case PDF:
	    outputExtension = "pdf";
	    responseFile.setMimeType(BaseService.MIME_TYPE_PDF);
	    break;
	case DOC:
	    outputExtension = "doc";
	    responseFile.setMimeType(BaseService.MIME_TYPE_RTF_DOC);
	    break;
	case RTF:
	    outputExtension = "rtf";
	    responseFile.setMimeType(BaseService.MIME_TYPE_RTF_DOC);
	    break;
	case ODT:
	    outputExtension = "odt";
	    responseFile.setMimeType(BaseService.MIME_TYPE_ODT);
	    break;
	case TXT:
	    outputExtension = "txt";
	    responseFile.setMimeType(BaseService.MIME_TYPE_TXT);
	    break;
	case HTML:
	    outputExtension = "html";
	    responseFile.setMimeType(BaseService.MIME_TYPE_HTML);
	    break;
	default:
	    throw new RuntimeException("Tipo conversione " + tipoConversione + " non supportata");
	}
	return outputExtension;
    }

    /**
     * prepara le variabili interne alla classe
     * 
     * @param tipoContenuto
     * @param tipoConversione
     * @param responseFile
     */
    protected String getInputExtension(FileInTypesEnum tipoContenuto, ConvertResponse responseFile) {

	String inputExtension = "";
	switch (tipoContenuto) {
	case TXT:
	    inputExtension = "txt";
	    break;
	case HTML:
	    inputExtension = "html";
	    break;
	case RTF:
	    inputExtension = "rtf";
	    break;
	case DOC:
	    inputExtension = "doc";
	    break;
	case DOCX:
	    inputExtension = "docx";
	    break;
	case ODT:
	    inputExtension = "odt";
	    break;
	case PDF:
	    inputExtension = "pdf";
	    break;
	case XLSX:
	    inputExtension = "xlsx";
	    break;
	case XLS:
	    inputExtension = "xls";
	    break;	    
	default:
	    throw new RuntimeException("Tipo File in ingresso non supportato: " + tipoContenuto);
	}
	return inputExtension;
    }

    /**
     * Se non presente Tag form all'interno della sottostringa htmlContent allora wrappa tutto con il tag
     * &lt;form&gt;&lt/form&gt; in questo modo vengono renderizzati gli elementi checkbox , textbox ecc...
     * 
     * @param contenutoFile
     * @return
     */
    protected String completeHtml(StringBuffer contenutoFile, FileInTypesEnum tipoContenuto) {

	String contenuto = contenutoFile.toString();
	if (tipoContenuto == FileInTypesEnum.HTML) {
	    String stringaDiControllo = new String("<form");
	    if (!match(contenuto, stringaDiControllo)) {
		contenuto = "<form name=\"Standard\">";
		contenuto += contenutoFile;
		contenuto += "</form>";
	    }
	}
	return contenuto;
    }

    private static boolean match(String text, String find) {

	Pattern p = null;
	Matcher m = null;
	p = Pattern.compile(find, Pattern.CASE_INSENSITIVE);
	m = p.matcher(text);
	return m.find();
    }

    /**
     * Dato un file ritorna un array di bytes
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {

	InputStream is = new FileInputStream(file);
	// Get the size of the file
	long length = file.length();
	if (length > Integer.MAX_VALUE) {
	    // File is too large
	}
	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];
	// Read in the bytes
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}
	// Ensure all the bytes have been read in
	if (offset < bytes.length) {
	    try {
		is.close();
	    } catch (Exception e) {
	    }
	    throw new IOException("Could not completely read file " + file.getName());
	}
	// Close the input stream and return bytes
	is.close();
	return bytes;
    }

    /**
     * Elimina i file temporanei senza alzare eccezioni in quanto operazione non bloccante
     */
    protected void destroyTempFiles() {

	try {
	    //	    if (this.inputFile != null) {
	    //		this.inputFile.delete();
	    //	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	try {
	    //	    if (this.outputFile != null) {
	    //		this.outputFile.delete();
	    //	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
    }
}
