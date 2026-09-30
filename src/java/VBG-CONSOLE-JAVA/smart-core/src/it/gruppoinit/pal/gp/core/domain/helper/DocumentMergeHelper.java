package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;


public class DocumentMergeHelper {
    
    private Date dataStampa;
    private boolean invioAmministrazioni;
    private Integer codiceAmministrazione;
    private boolean invioRichiedente;
    private boolean invioTecnico;
    private boolean invioSoggettiIstanza;
    
    public DocumentMergeHelper(){
	this.dataStampa = new Date();
    }
    
    /**
     * @return the dataStampa
     */
    public Date getDataStampa() {
    
        return dataStampa;
    }

    /**
     * @param dataStampa the dataStampa to set
     */
    public void setDataStampa(Date dataStampa) {
    
        this.dataStampa = dataStampa;
    }

    
    /**
     * @return the invioAmministrazioni
     */
    public boolean isInvioAmministrazioni() {
    
        return invioAmministrazioni;
    }

    
    /**
     * @param invioAmministrazioni the invioAmministrazioni to set
     */
    public void setInvioAmministrazioni(boolean invioAmministazioni) {
    
        this.invioAmministrazioni = invioAmministazioni;
    }

    
    
    /**
     * @return the codiceAmministrazione
     */
    public Integer getCodiceAmministrazione() {
    
        return codiceAmministrazione;
    }

    
    /**
     * @param codiceAmministrazione the codiceAmministrazione to set
     */
    public void setCodiceAmministrazione(Integer codiceAmministarzione) {
    
        this.codiceAmministrazione = codiceAmministarzione;
    }

    /**
     * @return the invioRichiedente
     */
    public boolean isInvioRichiedente() {
    
        return invioRichiedente;
    }

    
    /**
     * @param invioRichiedente the invioRichiedente to set
     */
    public void setInvioRichiedente(boolean invioRichiedente) {
    
        this.invioRichiedente = invioRichiedente;
    }

    
    /**
     * @return the invioTecnico
     */
    public boolean isInvioTecnico() {
    
        return invioTecnico;
    }

    
    /**
     * @param invioTecnico the invioTecnico to set
     */
    public void setInvioTecnico(boolean invioTecnico) {
    
        this.invioTecnico = invioTecnico;
    }

    
    /**
     * @return the invioSoggettiIstanza
     */
    public boolean isInvioSoggettiIstanza() {
    
        return invioSoggettiIstanza;
    }

    
    /**
     * @param invioSoggettiIstanza the invioSoggettiIstanza to set
     */
    public void setInvioSoggettiIstanza(boolean invioSoggettiIstanza) {
    
        this.invioSoggettiIstanza = invioSoggettiIstanza;
    }
    
    
}
