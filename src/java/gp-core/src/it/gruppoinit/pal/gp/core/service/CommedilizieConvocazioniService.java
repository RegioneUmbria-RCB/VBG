package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieConvocazioniDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

import java.util.List;

/**
 * 
 * @author
 */
public interface CommedilizieConvocazioniService extends BaseService<CommedilizieConvocazioni, PkId> {

    /**
     * @see CommedilizieConvocazioniDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieConvocazioni> findAll(Integer firstResult, Integer maxResult);

    public List<CommedilizieConvocazioni> findByFilterTable(FilterTable filterTable);

    /**
     * Lista di convocazoni filtrate per commissione edilizie e ordinate per data
     * 
     * @param commissioniedilizieT
     * @return
     */
    public List<CommedilizieConvocazioni> findByCommissioneEdiliziaT(CommissioniedilizieT commissioniedilizieT);

    /**
     * effettua la cancellazione delle convocazioni senza applicare nessun controllo Utilizzato per la cancellazione a
     * cascata delle convocazioni quando viene cancellata un acommissione
     * 
     * @param entity
     */
    public void deleteWithoutControl(CommedilizieConvocazioni entity);
}
