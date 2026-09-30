package it.gruppoinit.domain.nla;

import javax.activation.DataHandler;

public class Allegato extends AllegatoBase {

    protected DataHandler embeddedFileRef;
    protected String nomeFile;
    protected String cod;

    public DataHandler getEmbeddedFileRef() {

	return embeddedFileRef;
    }

    public void setEmbeddedFileRef(DataHandler embeddedFileRef) {

	this.embeddedFileRef = embeddedFileRef;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getCod() {

	return cod;
    }

    public void setCod(String cod) {

	this.cod = cod;
    }
}
