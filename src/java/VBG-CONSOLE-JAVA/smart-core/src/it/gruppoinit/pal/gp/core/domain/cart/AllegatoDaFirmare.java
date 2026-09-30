package it.gruppoinit.pal.gp.core.domain.cart;

import java.io.File;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoDaFirmare", propOrder = { "errorMessage", "warningMessage" })
public class AllegatoDaFirmare extends AllegatoCart {

    @XmlTransient
    private String signedFileName;
    @XmlTransient
    private File signedAttachment;
    @XmlTransient
    private FileItem uploadedFile;
    @XmlElement(name = "errorMessage", required = false)
    private String errorMessage;
    @XmlElement(name = "warningMessage", required = false)
    private String warningMessage;

    public AllegatoDaFirmare() {

    }

    public AllegatoDaFirmare(Integer codiceOggetto) {

	super(codiceOggetto);
    }

    /**
     * @return the signedFileName
     */
    public String getSignedFileName() {

	return signedFileName;
    }

    /**
     * @param signedFileName
     *            the signedFileName to set
     */
    public void setSignedFileName(String signedFileName) {

	this.signedFileName = signedFileName;
    }

    /**
     * @return the signedAttachment
     */
    public File getSignedAttachment() {

	return signedAttachment;
    }

    /**
     * @param signedAttachment
     *            the signedAttachment to set
     */
    public void setSignedAttachment(File signedAttachment) {

	this.signedAttachment = signedAttachment;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {

	return errorMessage;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {

	this.errorMessage = errorMessage;
    }

    /**
     * @return the warningMessage
     */
    public String getWarningMessage() {

	return warningMessage;
    }

    /**
     * @param errorMessage
     *            the warningMessage to set
     */
    public void setWarningMessage(String warningMessage) {

	this.warningMessage = warningMessage;
    }

    /**
     * @return the uploadedFile
     */
    public FileItem getUploadedFile() {

	return uploadedFile;
    }

    /**
     * @param uploadedFile
     *            the uploadedFile to set
     */
    public void setUploadedFile(FileItem uploadedFile) {

	this.uploadedFile = uploadedFile;
    }

    public String buildErrorMessage() {

	StringBuilder sbErr = new StringBuilder("Il file ");
	if (StringUtils.isNotBlank(this.getFileName())) {
	    sbErr.append(this.getFileName()).append(" ");
	}
	/*
	sbErr.append("caricato nel campo ");
	if (StringUtils.isNotBlank(this.getDescrizione())) {
	    sbErr.append(this.getDescrizione()).append(" ");
	}
	/*
	if (StringUtils.isNotBlank(this.getIdSemantico())) {
	    sbErr.append("associato all'id semantico ").append(this.getIdSemantico()).append(" ");
	}
	if (this.getRowIndex() != null && this.getRowIndex() > -1) {
	    sbErr.append("alla riga ").append(this.getRowIndex() + 1).append(" ");
	}
	*/
	sbErr.append("non ha superato la validazione della firma digitale");
	if (StringUtils.isNotBlank(this.getErrorMessage())) {
	    sbErr.append(": ").append(this.getErrorMessage());
	} else if (StringUtils.isNotBlank(this.getWarningMessage())) {
	    sbErr.append(": ").append(this.getWarningMessage());
	}
	sbErr.append(" (é possibile procedere con la compilazione della domanda e firmarlo prima della sua presentazione).");
	return sbErr.toString();
    }

    public boolean isError() {

	return StringUtils.isNotBlank(getErrorMessage());
    }

    public boolean isWarning() {

	return !isError() && StringUtils.isNotBlank(getWarningMessage());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((getRiferimento() == null) ? 0 : getRiferimento().hashCode());
	return result;
    }

    /**
     * Due Istanze di {@link AllegatoDaFirmare} sono uguali se hanno lo stesso riferimento. Se il riferimento è nullo
     * allora le due istanze devono avere uguale l'attributo fileName
     */
    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AllegatoDaFirmare other = (AllegatoDaFirmare) obj;
	if (getRiferimento() == null) {
	    if (other.getRiferimento() != null) {
		return false;
	    } else {
		if (getFileName() == null) {
		    if (other.getFileName() != null)
			return false;
		} else if (!getFileName().equals(other.getFileName()))
		    return false;
	    }
	} else if (!getRiferimento().equals(other.getRiferimento()))
	    return false;
	return true;
    }
}
