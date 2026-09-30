package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.CommissioniedilizieTDAO;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CommissioniTHelper;
import it.gruppoinit.pal.gp.core.domain.web.CommissioniedilizieTCommand;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * 
 * @author gianpaolot
 */
public interface CommissioniedilizieTService extends BaseService<CommissioniedilizieT, PkId> {

    /**
     * @see CommissioniedilizieTDAO#findAll(Integer, Integer)
     */
    public List<CommissioniedilizieT> findAll(Integer firstResult, Integer maxResult);

    public List<CommissioniedilizieT> findByFilterTable(FilterTable filterTable);

    /**
     * <pre>
     * Il metodo aggiorna l'oggetto master (CommissioniedilizieT) e in più fa un aggiornamento di dei figli.
     * Il campo dei figli che verrà aggionarnato sarà "ordine"
     * 
     * &#64;param entity
     * </pre>
     */
    public void updateCommissioniedilizieTAndChild(CommissioniedilizieTCommand entity);

    public CommissioniTHelper findByCodiceIstanza(Integer codiceIstanza);
}
