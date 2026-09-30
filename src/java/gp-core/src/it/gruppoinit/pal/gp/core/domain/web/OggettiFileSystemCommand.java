package it.gruppoinit.pal.gp.core.domain.web;

public class OggettiFileSystemCommand {

    private boolean setBlobNull;
    private Integer blobCount;
    private Integer fileCount;
    private Integer maxDocs;
    private Integer maxMinutes;
    private Integer minCodiceOggetto;
    private boolean verificaIncongruenze;

    public OggettiFileSystemCommand() {

	this.blobCount = -1;
	this.fileCount = -1;
	this.maxDocs = 0;
	this.maxMinutes = 0;
    }

    /**
     * @return the setBlobNull
     */
    public boolean isSetBlobNull() {

	return setBlobNull;
    }

    /**
     * @param setBlobNull
     *            the setBlobNull to set
     */
    public void setSetBlobNull(boolean setBlobNull) {

	this.setBlobNull = setBlobNull;
    }

    /**
     * @return the objectCount
     */
    public Integer getBlobCount() {

	return blobCount;
    }

    /**
     * @param objectCount
     *            the objectCount to set
     */
    public void setBlobCount(Integer objectCount) {

	this.blobCount = objectCount;
    }

    /**
     * @return the fileCount
     */
    public Integer getFileCount() {

	return fileCount;
    }

    /**
     * @param fileCount
     *            the fileCount to set
     */
    public void setFileCount(Integer fileCount) {

	this.fileCount = fileCount;
    }

    /**
     * @return the maxDocs
     */
    public Integer getMaxDocs() {

	return maxDocs;
    }

    /**
     * @param maxDocs
     *            the maxDocs to set
     */
    public void setMaxDocs(Integer maxDocs) {

	this.maxDocs = maxDocs;
    }

    /**
     * @return the maxMinutes
     */
    public Integer getMaxMinutes() {

	return maxMinutes;
    }

    /**
     * @param maxMinutes
     *            the maxMinutes to set
     */
    public void setMaxMinutes(Integer maxMinutes) {

	this.maxMinutes = maxMinutes;
    }

    /**
     * @return the verificaIncongruenze
     */
    public boolean isVerificaIncongruenze() {

	return verificaIncongruenze;
    }

    /**
     * @param verificaIncongruenze
     *            the verificaIncongruenze to set
     */
    public void setVerificaIncongruenze(boolean verificaIncongruenze) {

	this.verificaIncongruenze = verificaIncongruenze;
    }

    public Integer getMinCodiceOggetto() {

	return minCodiceOggetto;
    }

    public void setMinCodiceOggetto(Integer minCodiceOggetto) {

	this.minCodiceOggetto = minCodiceOggetto;
    }
}
