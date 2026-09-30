package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;
import java.util.Set;

public interface MercatipresenzeStoricoService extends BaseService<MercatipresenzeStorico, PkId> {

    /**
     * vedi doc del DAO
     * 
     * @see MercatipresenzeStoricoDAO#findAnniDaStorico()
     * @return
     */
    public List<Integer> findAnniDaStorico();

    /**
     * metodo per la creazione di una lista di anni ricavata dall'intersezione dei risultati ricavati da
     * MERCATIPRESENZE_T chiamando {@link MercatipresenzeTService#findAnniMercatiPresenti()} e MERCATIPRESENZE_STORICO
     * chiamando {@link MercatipresenzeStoricoService#findAnniDaStorico()}
     * 
     * @return
     */
    public Set<Integer> findAnni();

    /**
     * Torna la lista delle MercatipresenzeStorico di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<MercatipresenzeStorico> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);
}
