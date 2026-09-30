package it.gruppoinit.nlapec.util;

import java.io.File;

public class OriginalMessageAttachment {

    private String filename;
    private String encoding;
    private String contentType;
    private byte[] content;
    private File file;

    public String getFilename() {

	return filename;
    }

    public void setFilename(String filename) {

	this.filename = filename;
    }

    public String getEncoding() {

	return encoding;
    }

    public void setEncoding(String encoding) {

	this.encoding = encoding;
    }

    public String getContentType() {

	return contentType;
    }

    public void setContentType(String contentType) {

	this.contentType = contentType;
    }

    public byte[] getContent() {

	return content;
    }

    public void setContent(byte[] content) {

	this.content = content;
    }

    public void setFile(File file) {

	this.file = file;
    }

    public File getFile() {

	return file;
    }
}
