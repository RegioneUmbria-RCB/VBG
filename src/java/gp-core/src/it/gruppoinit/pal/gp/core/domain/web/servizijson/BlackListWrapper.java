package it.gruppoinit.pal.gp.core.domain.web.servizijson;


public class BlackListWrapper {
    
    private BlackList presenze = new BlackList();
    private BlackList bollettazione = new BlackList();
    
    public BlackList getPresenze() {
    
        return presenze;
    }
    
    public void setPresenze(BlackList presenze) {
    
        this.presenze = presenze;
    }
    
    public BlackList getBollettazione() {
    
        return bollettazione;
    }
    
    public void setBollettazione(BlackList bollettazione) {
    
        this.bollettazione = bollettazione;
    }
    
    
}
