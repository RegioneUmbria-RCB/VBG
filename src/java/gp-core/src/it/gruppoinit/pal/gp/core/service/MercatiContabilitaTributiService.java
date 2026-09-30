package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatiContabilitaTributiDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiContabilitaTributiService extends BaseService<MercatiContabilitaTributi, PkId> {

    /**
     * @see MercatiContabilitaTributiDAO#findAll(Integer, Integer)
     */
    public List<MercatiContabilitaTributi> findAll(Integer firstResult, Integer maxResult);

    public List<MercatiContabilitaTributi> findByFormulaAndWithoutDataFine(Integer codiceFormula);

    public List<MercatiContabilitaTributi> findByFormula(Integer codice);
}
