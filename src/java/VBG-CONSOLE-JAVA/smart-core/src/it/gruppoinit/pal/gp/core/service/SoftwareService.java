package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

public interface SoftwareService extends BaseService<Software, String> {

    /**
     * vedi doc del DAO
     * 
     * @see SoftwareDAO#findByFilter(Software)
     * @param entity
     * @return
     */
    public List<Software> findByFilter(Software entity);

    /**
     * metodo per il recupero di tutti i software attivi
     * 
     * @param frontoffice
     * <br />
     *            false: recupera tutti i software attivi.<br />
     *            true: recupera solo quelli attivi per il frontoffice.
     * @return
     */
    public List<Software> findSoftwareAttivi(boolean frontoffice);

    /**
     * metodo per il recupero dei software abilitati per l'operatore tra quelli attivi. se l'operatore è un
     * amministratore il metodo ritorna la lista dei software attivi ordinati per software.ordine
     * 
     * @param responsabile
     * @return
     */
    public List<Software> findSoftwareAbilitati(Responsabili responsabile);

    /**
     * metodo per il recupero dei software abilitati per l'operatore tra quelli attivi. se l'operatore è un
     * amministratore il metodo ritorna la lista dei software attivi ordinati per software.ordine.
     * 
     * 
     * @param responsabile
     * @param escludiNonOpzionali
     *            se escludere o meno i software obbligatori
     * @return
     */
    public List<Software> findSoftwareAbilitati(Responsabili responsabile, boolean escludiNonOpzionali);

    /**
     * metodo per verificare se un software è attivo
     * 
     * @param codiceSoftware
     * @return true se attivo, false se non attivo
     */
    public boolean isSoftwareAttivo(String codiceSoftware);

    /**
     * metodo per verificare se un software è abilitato per un operatore
     * 
     * @param responsabile
     * @return
     */
    public boolean isSoftwareAbilitato(Responsabili responsabile, String codiceSoftware);

    /**
     * recupera tutti i software ordinati per ordine ASC
     */
    public List<Software> findAll(Integer firstResult, Integer maxResult);

    public List<Software> findAttiviAndExcludeTT(boolean isAttiviFO);
}
