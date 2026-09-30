/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Franco.Leone
 *
 */
public class TracciatoRecordSet<T extends TracciatoRecord> {

    private static final Logger log = LoggerFactory.getLogger(TracciatoRecordSet.class);
    public static final String DEFAULT_LINE_SEPARATOR = "\r\n";
    private List<T> records = new ArrayList<>();
    private String lineSeparator;

    public TracciatoRecordSet() {

	this.lineSeparator = DEFAULT_LINE_SEPARATOR;
    }

    public List<T> getRecords() {

	if (this.records == null) {
	    this.records = new ArrayList<>();
	}
	return records;
    }

    public String getLineSeparator() {

	return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {

	this.lineSeparator = StringUtils.defaultString(lineSeparator);
    }

    public void writeRecordsToStream(OutputStream os) throws IOException {

	if (os != null) {
	    for (TracciatoRecord rec : records) {
		String recLine = rec.writeRecord();
		recLine = recLine.replaceAll("[^\\x00-\\x7F]", " "); // ELIMINO I CARATTERI NON ASCII
		if (log.isDebugEnabled()) {
		    log.debug("writeRecordsToStream - writing record of type " + rec.getClass().getName() + ": " + recLine);
		}
		os.write(recLine.getBytes("UTF-8"));
		if (StringUtils.isNotEmpty(getLineSeparator())) {
		    os.write(getLineSeparator().getBytes("UTF-8"));
		}
	    }
	    os.close();
	}
    }

    public void writeRecordsToFile(File outFile) throws IOException {

	if (!outFile.exists()) {
	    outFile.createNewFile();
	}
	if (log.isDebugEnabled()) {
	    log.debug("writeRecordsToStream - writing records of file " + outFile.getAbsolutePath());
	}
	FileOutputStream fos = FileUtils.openOutputStream(outFile);
	this.writeRecordsToStream(fos);
    }

    public void readRecordsFromStream(InputStream is, Class<T> recordClass) throws IOException {

	//popolamento del record set tramite il parsing dei record da un input stream
	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader reader = new BufferedReader(isr);
	String line = null;
	this.records.clear();
	while ((line = reader.readLine()) != null) {
	    if (StringUtils.isNotBlank(line)) {
		try {
		    T tr = (T) recordClass.newInstance();
		    tr.readRecord(line);
		    this.records.add(tr);
		} catch (InstantiationException | IllegalAccessException e) {
		    // TODO Auto-generated catch block
		    String message = "errore java nell'istanziare records di classe " + recordClass.getName();
		    log.error("readRecordsFromStream - ", e);
		    throw new TracciatoRecordException(message, e);
		}
	    }
	}
	reader.close();
    }

    public void readRecordsFromFile(File inFile, Class<T> recordClass) throws IOException {

	FileInputStream fis = FileUtils.openInputStream(inFile);
	this.readRecordsFromStream(fis, recordClass);
    }

    public static void main(String[] args) {

	System.out.println("N°28".replaceAll("[^\\x00-\\x7F]", " "));
	/*
	TracciatoRecordSet<TracciatoRecordEsiti> datiEsiti = new TracciatoRecordSet<>();
	try {
	    datiEsiti.readRecordsFromFile(new File(
		    "C:\\sviluppo\\progetti\\nodo-pagamenti\\analisi\\Genova\\EsempioEsitiPagamenti\\MERCATICOPERTICANONI_NODO_Notifica_20200115103550.txt"),
		    TracciatoRecordEsiti.class);
	    File copy = new File(
		    "C:\\sviluppo\\progetti\\nodo-pagamenti\\analisi\\Genova\\EsempioEsitiPagamenti\\COPIA_MERCATICOPERTICANONI_NODO_Notifica_20200115103550.txt");
	    if (!copy.exists()) {
		copy.createNewFile();
	    }
	    datiEsiti.writeRecordsToFile(copy);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	*/
    }
}
