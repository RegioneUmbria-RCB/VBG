package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.LetteretipoDAO;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface LetteretipoService extends BaseService<Letteretipo, PkId> {

    /**
     * @see LetteretipoDAO#findAll(Integer, Integer)
     */
    public List<Letteretipo> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ricerca per descrizione una lettera tipo con LETTERETIPO.FLAG_DISABILITATO=0, se il parametro
     * {<b>includiDisabilitate</b>} è true allora la ricerca torna anche i record con LETTERETIPO.FLAG_DISABILITATO=1
     * ordinati per LETTERETIPO.DESCRIZIONE ASC
     * 
     * @param entity
     * @param includiDisabilitate
     * @return
     */
    public List<Letteretipo> findByDescrizione(Letteretipo entity, boolean includiDisabilitate);

    /**
     * Ricerca per descrizione una lettera tipo con LETTERETIPO.FLAG_DISABILITATO=0, se il parametro
     * {<b>includiDisabilitate</b>} è true allora la ricerca torna anche i record con LETTERETIPO.FLAG_DISABILITATO=1
     * ordinati per LETTERETIPO.DESCRIZIONE ASC
     * 
     * @param descrizione
     * @param software
     * @param includiDisabilitate
     * @return
     */
    public List<Letteretipo> findByDescrizioneAndSoftware(String descrizione, String software, boolean includiDisabilitate);

    /**
     * Il metodo controlla se è possibile disabilitare il record corrente. E' possibile disabilitare un record di
     * lettere tipo se non è configurata nessuna dipendenza nelle tabelle di configurazione. ad oggi
     * <ul>
     * <li>TIPIDOCUMENTO (tipi documento anagrafe)</li>
     * <li>TIPIMOVIMENTO (Lettera per la produzione del pdf modello-riepilogo secondo DPR160 )</li>
     * <li>TIPIMOVIMENTODOCTIPO (documenti tipo del movimento)</li>
     * <li>TIPIPROCEDURE (Lettera Tipo Autorizzazione)</li>
     * </ul>
     * 
     * @param letteretipo
     * @return
     */
    public boolean checkSeDisabilitare(Letteretipo letteretipo);
}
