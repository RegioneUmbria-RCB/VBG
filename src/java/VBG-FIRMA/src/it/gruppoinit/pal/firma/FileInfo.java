package it.gruppoinit.pal.firma;

public class FileInfo {

    private String sessionId;
    private String fileId;
    private String clientFileId;
    private String fileName;
    private Boolean isSigned;

    public FileInfo(String sessionId, String fileId, String clientFileId, String fileName, Boolean isSigned) {

	this.sessionId = sessionId;
	this.fileId = fileId;
	this.setClientFileId(clientFileId);
	this.fileName = fileName;
	this.isSigned = isSigned;
    }

    public String getFileId() {

	return fileId;
    }

    public String getFileName() {

	return fileName;
    }

    public void setFileName(String fileName) {

	this.fileName = fileName;
    }

    public String getSessionId() {

	return sessionId;
    }

    public void setSessionId(String sessionId) {

	this.sessionId = sessionId;
    }

    public Boolean getIsSigned() {

	return isSigned;
    }

    public void setIsSigned(Boolean isSigned) {

	this.isSigned = isSigned;
    }

    public String getClientFileId() {

	return clientFileId;
    }

    public void setClientFileId(String clientFileId) {

	this.clientFileId = clientFileId;
    }

    @Override
    public String toString() {

	return "[sessionId=" + sessionId + ", fileId=" + fileId + ", clientFileId=" + clientFileId + ", fileName=" + fileName + ", isSigned="
		+ isSigned + "]";
    }
}
