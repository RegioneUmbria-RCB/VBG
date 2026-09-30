package it.gruppoinit.utilities;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.istack.ByteArrayDataSource;

public class Utilities {

    private static final Logger log = LoggerFactory.getLogger(Utilities.class);

    public static void gracefullyDeleteFiles(File tempFile) {

	try {
	    FileUtils.forceDelete(tempFile);
	} catch (Exception e) {
	    log.error("gracefullyDeleteFiles# {}-{}", tempFile.getName(), e.getMessage());
	}
    }

    public static DataHandler bytesToDataHandler(byte[] content) {

	try {
	    DataSource ds = new ByteArrayDataSource(content, "application/octet-stream");
	    return new DataHandler(ds);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public static StringBuffer leftPaddigZero(StringBuffer sb, String valore, int lunghezzaAttesaCampo) {

	int numberPadding = lunghezzaAttesaCampo - valore.length();
	for (int i = 0; i < numberPadding; i++) {
	    sb = sb.append("0");
	}
	sb = sb.append(valore);
	return sb;
    }

    public static StringBuffer appendSpace(StringBuffer sb, int lunghezzaRealeCampo, int lunghezzaAttesaCampo) {

	int numberSpace = lunghezzaAttesaCampo - lunghezzaRealeCampo;
	for (int i = 0; i < numberSpace; i++) {
	    sb = sb.append(" ");
	}
	return sb;
    }
}
