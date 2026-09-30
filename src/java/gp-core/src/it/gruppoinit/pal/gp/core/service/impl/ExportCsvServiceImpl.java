package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.service.ExportCsvService;
import it.gruppoinit.pal.gp.core.service.helper.CvsRecordsHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExportCsvServiceImpl implements ExportCsvService {

    private static final Logger log = LoggerFactory.getLogger(ExportCsvServiceImpl.class);

    @Override
    public byte[] writeCsvFile(String fileName, CvsRecordsHelper<?> cvsRecordsHelper) throws Exception {

	log.debug("writeCsvFile# Creo file temp locale : {}", fileName);
	File file = File.createTempFile("EXPORT-CSV-FILE-TEMP", fileName);
	FileWriter fileWriter = null;
	CSVPrinter csvFilePrinter = null;
	log.debug("writeCsvFile# converto gli header da lista ad array");
	List<String> headerList = cvsRecordsHelper.getHeader();
	String[] h = new String[cvsRecordsHelper.getHeader().size()];
	int i = 0;
	for (String _header : headerList) {
	    h[i] = _header;
	    i++;
	}
	log.debug("writeCsvFile# Imposto proprietà dile csv..");
	CSVFormat csvFileFormat = CSVFormat.EXCEL.withDelimiter(';').withQuote('"').withQuoteMode(QuoteMode.ALL).withHeader(h);
	byte[] b;
	try {
	    //initialize FileWriter object
	    fileWriter = new FileWriter(file);
	    //initialize CSVPrinter object 
	    csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	    log.debug("writeCsvFile# popolo file csv...");
	    List<List<String>> records = cvsRecordsHelper.getRecords();
	    for (List<String> singoloRecord : records) {
		csvFilePrinter.printRecord(singoloRecord);
	    }
	    log.debug("writeCsvFile# fileWriter flush... ");
	    fileWriter.flush();
	    log.debug("writeCsvFile# fileWriter close... ");
	    fileWriter.close();
	    log.debug("writeCsvFile# csvFilePrinter close... ");
	    csvFilePrinter.close();
	    FileInputStream fis = new FileInputStream(file);
	    b = IOUtils.toByteArray(fis);
	} catch (Exception e) {
	    log.error("{}", e);
	    throw e;
	} finally {
	    log.debug("writeCsvFile# csvFilePrinter close... ");
	    csvFilePrinter.close();
	    gracefullyDeleteFiles(file);
	}
	return b;
    }

    private void gracefullyDeleteFiles(File tempFile) {

	String name = tempFile.getName();
	try {
	    if (tempFile.delete()) {
		log.debug("gracefullyDeleteFiles#File {}  cancellato: {}  ", name);
	    } else {
		log.debug("gracefullyDeleteFiles#File {} non cancellato: {}  ", name);
	    }
	} catch (Exception e) {
	    log.debug("gracefullyDeleteFiles#File {} non cancellato: {}  ", name, e);
	    System.out.println(e.getMessage());
	}
    }
    //    public static void main(String[] args) {
    //
    //	ExportCsvServiceImpl exportCsvService = new ExportCsvServiceImpl();
    //	CvsRecordsHelper c = new CvsRecordsHelper();
    //	List<String> header = new ArrayList<String>();
    //	header.add("toponimo");
    //	header.add("indirizzo");
    //	c.setHeader(header);
    //	List<String> record1 = new ArrayList<String>();
    //	record1.add("via");
    //	record1.add("eleonora");
    //	List<String> record2 = new ArrayList<String>();
    //	record2.add("piazza");
    //	record2.add("armi");
    //	List<List<String>> records = new ArrayList<List<String>>();
    //	records.add(record1);
    //	records.add(record2);
    //	c.setRecords(records);
    //	try {
    //	    exportCsvService.writeCsvFile("C://", "test.csv", c);
    //	} catch (Exception e) {
    //	    // TODO Auto-generated catch block
    //	    e.printStackTrace();
    //	}
    //    }
}
