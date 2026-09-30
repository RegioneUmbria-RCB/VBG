package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

public class CartFileCopyInfo {

    private Integer idOggetto = 0;
    private String idComune = "";
    private String nomeFileTemporaneo;
    private String nomeFileOriginale = "";
    private String estensione = "";
    private String idSemantico = "";
    public static final String PRESENTAZIONE_DOMANDA_FILENAME_PATTERN = "{0}({1}-{2}).{3}";
    public static final int PRESENTAZIONE_DOMANDA_FILENAME_MAXLENGTH = 250;

    /**
     * @return the idOggetto
     */
    public Integer getIdOggetto() {

	return idOggetto;
    }

    /**
     * @param idOggetto
     *            the idOggetto to set
     */
    public void setIdOggetto(Integer idOggetto) {

	this.idOggetto = idOggetto;
    }

    /**
     * @return the idComune
     */
    public String getIdComune() {

	return idComune;
    }

    /**
     * @param idComune
     *            the idComune to set
     */
    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    /**
     * @return the nomeFile
     */
    public String getNomeFileTemporaneo() {

	return nomeFileTemporaneo;
    }

    /**
     * @param nomeFile
     *            the nomeFile to set
     */
    public void setNomeFileTemporaneo(String nomeFile) {

	this.nomeFileTemporaneo = nomeFile;
	if (StringUtils.isNotBlank(this.nomeFileTemporaneo)) {
	    String[] parts = this.nomeFileTemporaneo.split(FACCTConstants.PRESENTAZIONE_DOMANDA_TEMP_FILE_NAME_SEPARATOR);
	    if (parts.length == 1) {
		this.nomeFileOriginale = parts[0].trim();
		this.idSemantico = "";
	    } else if (parts.length > 1) {
		this.idSemantico = parts[0].trim();
		this.nomeFileOriginale = parts[1].trim();
	    }
	    parts = this.nomeFileOriginale.split("\\.");
	    if (parts.length > 1) {
		int firstExtensionPart = parts.length - 1;
		String ext = parts[parts.length - 1];
		if (parts.length > 2 && parts[parts.length - 1].equalsIgnoreCase("p7m")) {
		    firstExtensionPart--;
		    ext = parts[parts.length - 2] + "." + ext;
		}
		StringBuilder sbFileName = new StringBuilder(parts[0]);
		for (int i = 1; i < firstExtensionPart; i++) {
		    sbFileName.append(".").append(parts[i]);
		}
		this.estensione = ext;
		this.nomeFileOriginale = sbFileName.toString();
	    } else {
		this.nomeFileOriginale = parts[0];
	    }
	    /*
	    int lastDotIx = this.nomeFileOriginale.lastIndexOf('.');
	    if (lastDotIx > -1) {
	    this.nomeFileOriginale = this.nomeFileOriginale.substring(0, lastDotIx);
	    this.estensione = this.nomeFileOriginale.substring(lastDotIx + 1);
	    }
	    */
	}
    }

    public String buildNomeFilePresentazioneDomanda() {

	String nomeFile = this.nomeFileOriginale;
	if (nomeFile.length() > PRESENTAZIONE_DOMANDA_FILENAME_MAXLENGTH) {
	    nomeFile = nomeFile.substring(0, PRESENTAZIONE_DOMANDA_FILENAME_MAXLENGTH);
	}
	nomeFile = MessageFormat.format(PRESENTAZIONE_DOMANDA_FILENAME_PATTERN, new Object[] { nomeFile, this.idComune, this.idOggetto.toString(),
		this.estensione });
	return Utilities.correggiNomeFile(nomeFile);
    }

    public String getNomeFileOriginale() {

	return nomeFileOriginale;
    }

    public String getEstensione() {

	return estensione;
    }

    public String getIdSemantico() {

	return idSemantico;
    }

    public void setIdSemantico(String idSemantico) {

	this.idSemantico = idSemantico;
    }
}
