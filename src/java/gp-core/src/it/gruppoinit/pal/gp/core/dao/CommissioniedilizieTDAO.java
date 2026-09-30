package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CommissioniTHelper;

/**
 * 
 * @author gianpaolot
 */
public interface CommissioniedilizieTDAO extends BaseDAO<CommissioniedilizieT, PkId> {

    /**
     * Lista di commissioni edilize T filtrate per idcomune
     * 
     */
    public List<CommissioniedilizieT> findAll(Integer firstResult, Integer maxResult);

    /**
     * Può tornare nullo
     * 
     * @param codiceIstanza
     * @return
     */
    public CommissioniTHelper findByCodiceIstanza(Integer codiceIstanza);
}
