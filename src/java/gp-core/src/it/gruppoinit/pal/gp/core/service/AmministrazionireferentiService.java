package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

public interface AmministrazionireferentiService extends BaseService<Amministrazionireferenti, PkId> {

    /**
     * 
     * @param amministrazioni
     * @return lista di uffici filtrati per amministrazione
     */
    public List<Amministrazionireferenti> findByAmministrazioni(Amministrazioni amministrazioni);

    public List<Amministrazionireferenti> findByFilterTable(FilterTable ft);

    /**
     * Torna la lista delle Amministrazionireferenti di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Amministrazionireferenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
