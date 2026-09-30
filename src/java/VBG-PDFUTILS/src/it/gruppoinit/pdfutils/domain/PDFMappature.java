package it.gruppoinit.pdfutils.domain;

import java.io.Serializable;

public class PDFMappature implements Serializable {

    private static final long serialVersionUID = 6653950211581802585L;
    private String label;
    private String ambito;
    private String xpath;
    private String decodFormatoInput;
    private String decodFormatoOutput;
    private String decodTipo;

    public String getLabel() {

	return label;
    }

    public void setLabel(String label) {

	this.label = label;
    }

    public String getAmbito() {

	return ambito;
    }

    public void setAmbito(String ambito) {

	this.ambito = ambito;
    }

    public String getXpath() {

	return xpath;
    }

    public void setXpath(String xpath) {

	this.xpath = xpath;
    }

    public String getDecodFormatoInput() {

	return decodFormatoInput;
    }

    public void setDecodFormatoInput(String decodFormatoInput) {

	this.decodFormatoInput = decodFormatoInput;
    }

    public String getDecodFormatoOutput() {

	return decodFormatoOutput;
    }

    public void setDecodFormatoOutput(String decodFormatoOutput) {

	this.decodFormatoOutput = decodFormatoOutput;
    }

    public String getDecodTipo() {

	return decodTipo;
    }

    public void setDecodTipo(String decodTipo) {

	this.decodTipo = decodTipo;
    }
}
