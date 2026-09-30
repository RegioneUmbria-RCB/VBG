package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StarBaseCommand {

    private Comuni comuneLocalizzazione;
    private List<Responsabilicomuni> comuniResponsabile = new ArrayList<Responsabilicomuni>();

    public StarBaseCommand() {

	super();
    }

    public Comuni getComuneLocalizzazione() {
    
        return comuneLocalizzazione;
    }

    public void setComuneLocalizzazione(Comuni comuneLocalizzazione) {
    
        this.comuneLocalizzazione = comuneLocalizzazione;
    }

    public boolean isConsoleLocale() {
    
        return ORMHelper.isConsoleLocale();
    }

    public List<Responsabilicomuni> getComuniResponsabile() {
    
        return comuniResponsabile;
    }

    public void setComuniResponsabile(List<Responsabilicomuni> comuniResponsabile) {
    
        this.comuniResponsabile = comuniResponsabile;
    }

    /**
     * indica se è necessario fare apparire una listbox con l'elenco dei comuni del gruppo.
     * di default restituisce true se è una configurazione multicomune nella consolle locale e non siè ancora definito 
     * per quale comune si sta caricando i dati in maschera
     * @return
     */
    public boolean getDisplayComuniResponsable() {
    
        return ORMHelper.isConsoleLocale() && (getComuneLocalizzazione() == null || getComuneLocalizzazione().getCodicecomune() == null);
    }

    /**
     * indica se è necessario fare apparire in maschera l'informazione sul comune per cui si sta facendo la localizzazione.
     * di default restituisce true quando si tratta di una consolle locale con una configurazione multicomune
     * @return
     */
    public boolean getDisplayComuneLocalizzazione() {
    
        return isConsoleLocale() && getComuniResponsabile().size() > 1;
    }

    public String getDefaultCodiceComune() {
    
        String codCom = null;
        if (isConsoleLocale() && getComuniResponsabile().size() == 1) {
            Comuni c = getComuniResponsabile().get(0).getComune();
            codCom = c.getComune();
        }
        return codCom;
    }

    public String getDescrizioneComuneLocalizzazione() {
        String desc = "";
        if(getComuneLocalizzazione() != null){
            desc = getComuneLocalizzazione().getComune();
        }
        if(StringUtils.isBlank(desc)){
            desc = "tutti i comuni del gruppo";
        }
        return desc;
    }
}