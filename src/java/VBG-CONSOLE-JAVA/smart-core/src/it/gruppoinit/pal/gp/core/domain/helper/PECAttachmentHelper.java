/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

/**
 * @author francol
 * 
 */
public class PECAttachmentHelper {

    private String nomeFile;
    private byte[] binaryContent;
    private Integer codiceOggetto;

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public byte[] getBinaryContent() {

	return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {

	this.binaryContent = binaryContent;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getTextContent() {

	String retVal = "";
	if(this.binaryContent != null){
	    retVal = new String(binaryContent);
	}
	return retVal;
    }
}
