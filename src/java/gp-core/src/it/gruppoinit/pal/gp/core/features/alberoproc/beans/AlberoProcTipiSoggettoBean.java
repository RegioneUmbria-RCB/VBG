package it.gruppoinit.pal.gp.core.features.alberoproc.beans;

import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;

public class AlberoProcTipiSoggettoBean {
    
    private Tipisoggetto tipisoggetto;
    private boolean documentiTipiSoggettoChecked;
    
    public Tipisoggetto getTipisoggetto() {
    
        return tipisoggetto;
    }
    
    public void setTipisoggetto(Tipisoggetto tipisoggetto) {
    
        this.tipisoggetto = tipisoggetto;
    }
    
    public boolean isDocumentiTipiSoggettoChecked() {
    
        return documentiTipiSoggettoChecked;
    }
    
    public void setDocumentiTipiSoggettoChecked(boolean documentiTipiSoggettoChecked) {
    
        this.documentiTipiSoggettoChecked = documentiTipiSoggettoChecked;
    }
    
    
    
}
