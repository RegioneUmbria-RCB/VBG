package it.gruppoinit.pdd.ri.features.configurazione;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "codiceCatastale", "certificato", "percorsoCertificato" })
public class Certificato {

    private String codiceCatastale;
    private String nomeFile;
    private String password;
    private String fileURI;
    private String alias;

    public Certificato() {

    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public String getFileURI() {

	return fileURI;
    }

    public void setFileURI(String fileURI) {

	this.fileURI = fileURI;
    }

    @XmlTransient
    public byte[] getCertificato() {

	try {
	    return FileUtils.readFileToByteArray(new File(this.fileURI + this.nomeFile));
	} catch (IOException e) {
	    return null;
	}
    }

    @XmlTransient
    public String getPercorsoCertificato() {

	return new File(this.fileURI, this.nomeFile).getPath();
    }

    public Certificato(String fileURI, String nomeFile, String password, String alias) {

	this.nomeFile = nomeFile;
	this.password = password;
	this.fileURI = fileURI;
	this.alias = alias;
    }

    public String getCodiceCatastale() {

	return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {

	this.codiceCatastale = codiceCatastale;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }
}
