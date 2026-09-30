package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author gianpaolot
 */
public interface CommissioniedilizieRDAO extends BaseDAO<CommissioniedilizieR, PkId> {

    /**
     * Lista commissioni edilizie r
     * 
     */
    public List<CommissioniedilizieR> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupero il massimo ordine inserito per le commissioni edilizie r associate alle commissioni edilizie t
     * 
     * @param commissioniedilizieT
     * @return
     */
    public Integer maxOrdineByCommissioniedilizieT(CommissioniedilizieT commissioniedilizieT);

    /**
     * torna i codici commissioniedilizie_t dove è stato usato il movimento di richiesta
     * 
     * @param codiceMovimento
     * @return
     */
    public Set<Integer> findCodiciCommissioniByMovimento(Integer codiceMovimento);

    public List<CommissioniedilizieT> findCommissioniByIstanza(Integer codiceIstanza);

    public int countCommissioniByIstanza(Integer codiceIstanza);
}
