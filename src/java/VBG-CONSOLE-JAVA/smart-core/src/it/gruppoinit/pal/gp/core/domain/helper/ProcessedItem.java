package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.dss.wsclient.WsValidationReport;

import java.util.List;
import java.util.Vector;

public class ProcessedItem {

    private String fieldName = null;
    private boolean fileUpload = false;
    private String fileName = null;
    //nome del file in chiaro (solo per p7m e p7d)
    private String contentFileName = null;
    private String contentWritedFileName = null;
    private String writedFileName = null;
    private String fullFileName = null;
    private String contentType = null;
    private long size = -1;
    private boolean inMemory = true;
    private Vector<String> errors = null;
    private WsValidationReport report;

    public ProcessedItem(String fieldName) {

	super();
	this.fieldName = fieldName;
	this.errors = new Vector<String>();
    }

    public String getContentFileName() {

	return contentFileName;
    }

    public boolean isFileUpload() {

	return fileUpload;
    }

    public void setContentFileName(String contentFileName) {

	this.contentFileName = contentFileName;
    }

    public String getFullFileName() {

	return fullFileName;
    }

    public void setFullFileName(String fullFileName) {

	this.fullFileName = fullFileName;
    }

    public String getWritedFileName() {

	return writedFileName;
    }

    public void setWritedFileName(String writedFileName) {

	this.writedFileName = writedFileName;
    }

    public String getContentType() {

	return contentType;
    }

    public void setContentType(String contentType) {

	this.contentType = contentType;
    }

    public long getSize() {

	return size;
    }

    public void setSize(long size) {

	this.size = size;
    }

    public List<String> getErrors() {

	return errors;
    }

    public boolean isInMemory() {

	return inMemory;
    }

    public void setInMemory(boolean inMemory) {

	this.inMemory = inMemory;
    }

    public String getFileName() {

	return fileName;
    }

    public void setFileName(String fileName) {

	this.fileName = fileName;
    }

    private String string;

    public String getString() {

	return string;
    }

    public void setString(String string) {

	this.string = string;
    }

    public void setFieldName(String fieldName) {

	this.fieldName = fieldName;
    }

    public String getFieldName() {

	return fieldName;
    }

    public WsValidationReport getReport() {

	return report;
    }

    public void setReport(WsValidationReport report) {

	this.report = report;
    }

    public String getContentWritedFileName() {

	return contentWritedFileName;
    }

    public void setContentWritedFileName(String contentWritedFileName) {

	this.contentWritedFileName = contentWritedFileName;
    }
}
