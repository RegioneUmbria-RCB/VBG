package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Oggetti;

import java.io.Serializable;


public class OggettiFileSystemError implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7953758952346887884L;

    private String errorMessage;
    
    private Exception exception;
    
    private Integer codiceOggetto;
    
    private String nomeFile;
    
    private String path;

    
    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
    
        return errorMessage;
    }

    
    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
    
        this.errorMessage = errorMessage;
    }

    
    /**
     * @return the exception
     */
    public Exception getException() {
    
        return exception;
    }

    
    /**
     * @param exception the exception to set
     */
    public void setException(Exception exception) {
    
        this.exception = exception;
    }

    
    /**
     * @param oggetto the oggetto to set
     */
    public void setOggetto(Oggetti oggetto) {
    
        if(oggetto != null){
            this.codiceOggetto = oggetto.getId().getCodice();
            this.nomeFile = oggetto.getNomefile();
            this.path = oggetto.getPercorso();
        }
        else{
            this.codiceOggetto = null;
            this.nomeFile = null;
            this.path = null;
        }
    }


    
    /**
     * @return the nomeFile
     */
    public String getNomeFile() {
    
        return nomeFile;
    }


    
    /**
     * @param nomeFile the nomeFile to set
     */
    public void setNomeFile(String nomeFile) {
    
        this.nomeFile = nomeFile;
    }


    
    /**
     * @return the path
     */
    public String getPath() {
    
        return path;
    }


    
    /**
     * @param path the path to set
     */
    public void setPath(String path) {
    
        this.path = path;
    }


    
    /**
     * @param codiceOggetto the codiceOggetto to set
     */
    public void setCodiceOggetto(Integer codiceOggetto) {
    
        this.codiceOggetto = codiceOggetto;
    }
    
    public Integer getCodiceOggetto(){
	return codiceOggetto;
    }
}
