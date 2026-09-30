package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FileUpload;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeInterdettiHelper;

/**
 * 
 * @author gianpaolot
 * 
 */
public interface AnagrafeinterdettiCommandService {

    /**
     * <pre>
     * Il metodo andrà ad inserire le anagrafiche interdette,inserire un' anagrafica interdetta significa associare all'anagrafica
     * un riferimento alla tabella SCADENZE settando il campo categoria a "I" (interdetta). 
     * L'anagrafica rimarrà interdetta fino a quando la data giornaliera sarà minore della data "datascadenza" della tabella SCADENZE
     * 
     * @param fileUpload
     * @return un oggetto AnagrafeInterdettiHelper che contiene due liste di  AnagrafeinterdettiCommand:
     * 
     * 	1- Quelle che deve importare
     *  2- Quelle che non sono state importare per incongruenze dei dati
     * 
     * <pre>
     */
    public AnagrafeInterdettiHelper insertAnagrafeInterdetti(FileUpload fileUpload);
}
