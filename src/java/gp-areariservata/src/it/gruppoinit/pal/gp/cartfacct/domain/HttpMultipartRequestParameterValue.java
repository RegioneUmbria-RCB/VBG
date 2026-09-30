package it.gruppoinit.pal.gp.cartfacct.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

public class HttpMultipartRequestParameterValue {

    private List<FileItem> fileValues;
    private List<String> stringValues;

    public HttpMultipartRequestParameterValue(FileItem fileValue) {

	if (null == fileValues) {
	    fileValues = new ArrayList<FileItem>();
	}
	if (null != fileValue) {
	    fileValues.add(fileValue);
	}
    }

    public HttpMultipartRequestParameterValue(String stringValue) {

	if (null == stringValues) {
	    stringValues = new ArrayList<String>();
	}
	if (null != stringValue) {
	    stringValues.add(stringValue);
	}
    }

    /*
    public HttpMultipartRequestParameterValue(List<String> stringValues){
    
    }
    */
    /**
     * @return the fileValues
     */
    public FileItem[] getFileValues() {

	if(!isFileValue()){
	    throw new RuntimeException("Impossibile recuperare un valore di tipo file dai valori di un parametro request di tipo stringa.");
	}
	return fileValues.toArray(new FileItem[fileValues.size()]);
    }

    /**
     * @return the stringValues
     */
    public String[] getStringValues() {

	if(!isStringValue()){
	    throw new RuntimeException("Impossibile recuperare un valore di tipo stringa dai valori di un parametro request di tipo file.");
	}
	return stringValues.toArray(new String[stringValues.size()]);
    }

    public boolean isFileValue() {

	return this.fileValues != null;
    }

    public boolean isStringValue() {

	return this.stringValues != null;
    }
    
    public void addStringValue(String stringValue){
	
	if(!isStringValue()){
	    throw new RuntimeException("Impossibile aggiungere un valore di tipo stringa ai valori di un parametro request di tipo file.");
	}
	this.stringValues.add(stringValue);
    }
    
    public void addFileValue(FileItem fileValue){
	
	if(!isFileValue()){
	    throw new RuntimeException("Impossibile aggiungere un valore di tipo file ai valori di un parametro request di tipo stringa.");
	}
	this.fileValues.add(fileValue);
    }
}
