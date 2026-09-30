package it.gruppoinit.service;

import it.gruppoinit.constants.AttivitaDaEseguireEnum;
import it.init.sigepro.rte.types.DettaglioPraticaType;

public class AttivitaDaEseguireResult {

    private AttivitaDaEseguireEnum attivitaDaEseguireEnum;
    private DettaglioPraticaType pratica;
    
    public AttivitaDaEseguireEnum getAttivitaDaEseguireEnum() {
    
        return attivitaDaEseguireEnum;
    }
    
    public void setAttivitaDaEseguireEnum(AttivitaDaEseguireEnum attivitaDaEseguireEnum) {
    
        this.attivitaDaEseguireEnum = attivitaDaEseguireEnum;
    }

    
    public DettaglioPraticaType getPratica() {
    
        return pratica;
    }

    
    public void setPratica(DettaglioPraticaType pratica) {
    
        this.pratica = pratica;
    }
     
}
