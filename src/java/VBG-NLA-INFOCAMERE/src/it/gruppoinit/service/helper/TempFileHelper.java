package it.gruppoinit.service.helper;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;

import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.types.DocumentiType;

public class TempFileHelper {

    private File file;
    private String nomeFile;
    private String mimeType;
    private DocumentiType documentiType;

    private TempFileHelper() {

	super();
    }

    public File getFile() {

	return file;
    }

    private void setFile(File file) {

	this.file = file;
    }

    public String getNomeFile() {

	return documentiType.getAllegati().getFile().getFileName();
    }

    public String getMimeType() {

	return documentiType.getAllegati().getFile().getMimeType();
    }

    public DocumentiType getDocumentiType() {

	return documentiType;
    }

    private void setDocumentiType(DocumentiType documentiType) {

	this.documentiType = documentiType;
    }

    public static TempFileHelper fromDoc(DocumentiType doc, String folderPratica) throws IOException {

	TempFileHelper th = new TempFileHelper();
	th.setDocumentiType(doc);
	DataHandler binaryData = doc.getAllegati().getFile().getBinaryData();
	th.setFile(Utilities.createTmpAllegato(binaryData, folderPratica));
	return th;
    }

    public DataHandler getDataHandler() {

	return Utilities.fileToDataHandler(file);
    }

    public String getKey() {

	return getKeyFromDoc(documentiType);
    }

    public static String getKeyFromDoc(DocumentiType pDocumentiType) {

	return pDocumentiType.getId() + "_" + pDocumentiType.getAllegati().getId();
    }

    @Override
    public String toString() {

	return "[key: " + getKey() + ", nomeFile: " + nomeFile + ", file: " + file.getAbsolutePath() + "]";
    }
}
