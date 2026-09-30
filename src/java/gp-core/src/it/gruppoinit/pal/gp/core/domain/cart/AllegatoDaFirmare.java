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
@XmlType(name = "AllegatoDaFirmare", propOrder = { "descrizione", "riferimento", "fileName", "idSemantico", "rowIndex", "errorMessage",
	"warningMessage" })
public class AllegatoDaFirmare {

    @XmlElement(name = "descrizione", required = false)
    private String descrizione;
    @XmlElement(name = "riferimento", required = true)
    private Integer riferimento;
    @XmlElement(name = "fileName", required = true)
    private String fileName;
    @XmlTransient
    private String signedFileName;
    @XmlElement(name = "idSemantico", required = true)
    private String idSemantico;
    @XmlElement(name = "rowIndex", required = true)
    private Integer rowIndex;
    @XmlTransient
    private File attachment;
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

    public AllegatoDaFirmare(Integer riferimento) {

	this.riferimento = riferimento;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {

	return descrizione;
    }

    /**
     * @param descrizione
     *            the descrizione to set
     */
    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    /**
     * @return the riferimento
     */
    public Integer getRiferimento() {

	return riferimento;
    }

    /**
     * @param riferimento
     *            the riferimento to set
     */
    public void setRiferimento(Integer riferimento) {

	this.riferimento = riferimento;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {

	return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {

	this.fileName = fileName;
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
     * @return the attachment
     */
    public File getAttachment() {

	return attachment;
    }

    /**
     * @param attachment
     *            the attachment to set
     */
    public void setAttachment(File attachment) {

	this.attachment = attachment;
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
     * @return the idSemantico
     */
    public String getIdSemantico() {

	return idSemantico;
    }

    /**
     * @param idSemantico
     *            the idSemantico to set
     */
    public void setIdSemantico(String idSemantico) {

	this.idSemantico = idSemantico;
    }

    /**
     * @return the rowIndex
     */
    public Integer getRowIndex() {

	return rowIndex;
    }

    /**
     * @param rowIndex
     *            the rowIndex to set
     */
    public void setRowIndex(Integer rowIndex) {

	this.rowIndex = rowIndex;
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
	}
	else if(StringUtils.isNotBlank(this.getWarningMessage())){
	    sbErr.append(": ").append(this.getWarningMessage());
	}
	sbErr.append(" (é possibile procedere con la compilazione della domanda e firmarlo prima della sua presentazione).");
	return sbErr.toString();
    }
    
    public boolean isError(){
	
	return StringUtils.isNotBlank(getErrorMessage());
    }
    
    public boolean isWarning(){
	
	return !isError() && StringUtils.isNotBlank(getWarningMessage());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((riferimento == null) ? 0 : riferimento.hashCode());
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
	if (riferimento == null) {
	    if (other.riferimento != null) {
		return false;
	    } else {
		if (fileName == null) {
		    if (other.fileName != null)
			return false;
		} else if (!fileName.equals(other.fileName))
		    return false;
	    }
	} else if (!riferimento.equals(other.riferimento))
	    return false;
	return true;
    }
}
