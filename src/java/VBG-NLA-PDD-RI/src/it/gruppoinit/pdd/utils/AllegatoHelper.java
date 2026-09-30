package it.gruppoinit.pdd.utils;

import java.io.File;
import java.io.Serializable;

import javax.activation.DataHandler;

import org.apache.commons.lang.StringUtils;

public class AllegatoHelper implements Serializable {

    private static final long serialVersionUID = 3299889596965647352L;
    private String codiceOggetto;
    private DataHandler dataHandler;
    private String nomefile;
    private String descrizioneDocumento;
    private String tipoDocumento;
    private File fileContent;

    public String getDescrizioneDocumento() {

	return descrizioneDocumento;
    }

    public void setDescrizioneDocumento(String descrizioneDocumento) {

	this.descrizioneDocumento = descrizioneDocumento;
    }

    public String getNomefile() {

	return nomefile;
    }

    public void setNomefile(String nomefile) {

	this.nomefile = nomefile;
    }

    public String getMimeType() {

	if (StringUtils.isNotBlank(nomefile)) {
	    return Utilities.getContentType(nomefile);
	}
	return "";
    }

    public DataHandler getDataHandler() {

	return dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {

	this.dataHandler = dataHandler;
    }

    public String getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(String codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getTipoDocumento() {

	return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    public File getFileContent() {

	return fileContent;
    }

    public void setFileContent(File fileContent) {

	this.fileContent = fileContent;
    }
}
