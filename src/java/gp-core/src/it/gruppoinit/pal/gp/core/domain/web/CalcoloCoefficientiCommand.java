package it.gruppoinit.pal.gp.core.domain.web;


public class CalcoloCoefficientiCommand {
    
    private Integer idDestinazione;
    
    private Integer idTipoIntervento;
    
    private Integer idValiditaCoefficiente;
    
    private Integer idContributoCalcolo;
    
    private Double coefficiente;
    
    private Double coefficientetot;
    
    private Double contributotot;

    
    /**
     * @return the idDestinazione
     */
    public Integer getIdDestinazione() {
    
        return idDestinazione;
    }

    
    /**
     * @param idDestinazione the idDestinazione to set
     */
    public void setIdDestinazione(Integer idDestinazione) {
    
        this.idDestinazione = idDestinazione;
    }

    
    /**
     * @return the idTipoIntervento
     */
    public Integer getIdTipoIntervento() {
    
        return idTipoIntervento;
    }

    
    /**
     * @param idTipoIntervento the idTipoIntervento to set
     */
    public void setIdTipoIntervento(Integer idTipoIntervento) {
    
        this.idTipoIntervento = idTipoIntervento;
    }


    
    /**
     * @return the idValiditaCoefficiente
     */
    public Integer getIdValiditaCoefficiente() {
    
        return idValiditaCoefficiente;
    }


    
    /**
     * @param idValiditaCoefficiente the idValiditaCoefficiente to set
     */
    public void setIdValiditaCoefficiente(Integer idValiditaCoefficiente) {
    
        this.idValiditaCoefficiente = idValiditaCoefficiente;
    }


    
    /**
     * @return the idContributoCalcolo
     */
    public Integer getIdContributoCalcolo() {
    
        return idContributoCalcolo;
    }


    
    /**
     * @param idContributoCalcolo the idContributoCalcolo to set
     */
    public void setIdContributoCalcolo(Integer idContributoCalcolo) {
    
        this.idContributoCalcolo = idContributoCalcolo;
    }


    
    /**
     * @return the coefficiente
     */
    public Double getCoefficiente() {
    
        return coefficiente;
    }


    
    /**
     * @param coefficiente the coefficiente to set
     */
    public void setCoefficiente(Double coefficiente) {
    
        this.coefficiente = coefficiente;
    }


    
    /**
     * @return the coefficientetot
     */
    public Double getCoefficientetot() {
    
        return coefficientetot;
    }


    
    /**
     * @param coefficientetot the coefficientetot to set
     */
    public void setCoefficientetot(Double coefficientetot) {
    
        this.coefficientetot = coefficientetot;
    }


    
    /**
     * @return the contributotot
     */
    public Double getContributotot() {
    
        return contributotot;
    }


    
    /**
     * @param contributotot the contributotot to set
     */
    public void setContributotot(Double contributotot) {
    
        this.contributotot = contributotot;
    }
    
    
}
