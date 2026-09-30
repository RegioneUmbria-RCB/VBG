package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Helpbase;
import it.gruppoinit.pal.gp.core.domain.HelpbaseId;

public interface HelpbaseService extends BaseService<Helpbase, HelpbaseId> {

    /**
     * recupera l'help di base controllando prima se è presente per il software corrente e poi per TT
     * 
     * @param contentType
     *            la pagina per la quale visualizzare l'help
     * @return
     */
    public Helpbase findByContentAndSoftwares(String contentType,String software);

    /**
     * <pre>
     * Il metodo permette di verificare se l'operatore loggato può modificare l'Help di base.
     * 
     * 1- Viene fatto il controllo se è attiva la verticalizzazione di modifica dell'help di base.
     * 2- Si controlla se tale regola è attiva per l'operatore loggato.
     * Se sono entrambe vere :return <b>true</b> 
     * Altrimenti            :return <b>false</b>
     * 
     * 
     * @param codiceResponsabile
     * @param contentType
     * @return false
     * </pre>
     */
    public boolean isAllowedChangeHelpBase(Integer codiceResponsabile, String contentType);
    
}
