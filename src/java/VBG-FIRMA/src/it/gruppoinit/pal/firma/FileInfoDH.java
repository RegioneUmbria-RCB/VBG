package it.gruppoinit.pal.firma;

import javax.activation.DataHandler;

public class FileInfoDH extends FileInfo {

    private DataHandler dh;

    public FileInfoDH(String sessionId, String fileId, String clientFileId, String fileName, Boolean isSigned) {

	super(sessionId, fileId, clientFileId, fileName, isSigned);
    }

    public DataHandler getDh() {

	return dh;
    }

    public void setDh(DataHandler dh) {

	this.dh = dh;
    }
}
