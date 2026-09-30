package it.gruppoinit.pal.gp.core.domain.cart;

import org.apache.commons.fileupload.FileItem;


public class FileUploadInfo {
    
    private FileItem[] uploadedFiles = new FileItem[0];
    private AllegatoDaFirmare[] attachmentsToSign = new AllegatoDaFirmare[0];
    private String idSemantico;
    private int rowIndex = -1;
    private int rowIndexUI = -1;
    
    /**
     * @return the uploadedFiles
     */
    public FileItem[] getUploadedFiles() {
    
        return uploadedFiles;
    }
    
    /**
     * @param uploadedFiles the uploadedFiles to set
     */
    public void setUploadedFiles(FileItem[] uploadedFiles) {
    
        this.uploadedFiles = uploadedFiles;
    }
    
    /**
     * @return the idSemantico
     */
    public String getIdSemantico() {
    
        return idSemantico;
    }
    
    /**
     * @param idSemantico the idSemantico to set
     */
    public void setIdSemantico(String idSemantico) {
    
        this.idSemantico = idSemantico;
    }
    
    /**
     * @return the httpParamName
     */
    public int getRowIndex() {
    
        return this.rowIndex;
    }
    
    /**
     * @param httpParamName the httpParamName to set
     */
    public void setRowIndex(int rowIndex) {
    
        this.rowIndex = rowIndex;
    }

    
    /**
     * Restituisce un {@link AllegatoDaFirmare}[] che contiene sempre lo stesso numero di elementi dell'array 'uploadedFiles'.
     * Ad ogni file caricato presente in 'uploadedFiles' corrisponde un elemento dell'array 'attachmentsToSign' allo stesso indice.
     * Se il file caricato non richiede la firma digitale o se possiede una firma digitale valida allora l'elemento in 'attachmentsToSign'
     * corrispondente sarà un riferimento a null altrimenti ci sarà un'istanza di {@link AllegatoDaFirmare}.
     * @return the attachmentsToSign
     */
    public AllegatoDaFirmare[] getAttachmentsToSign() {
    
        return attachmentsToSign;
    }

    
    /**
     * @param attachmentsToSign the attachmentsToSign to set
     */
    public void setAttachmentsToSign(AllegatoDaFirmare[] attachmentsToSign) {
    
        this.attachmentsToSign = attachmentsToSign;
    }

    
    /**
     * @return the rowIndexUI
     */
    public int getRowIndexUI() {
    
        return rowIndexUI;
    }

    
    /**
     * @param rowIndexUI the rowIndexUI to set
     */
    public void setRowIndexUI(int rowIndexUI) {
    
        this.rowIndexUI = rowIndexUI;
    }
    
    
}
