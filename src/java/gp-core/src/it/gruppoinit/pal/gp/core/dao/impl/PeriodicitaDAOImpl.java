/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PeriodicitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Periodicita;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class PeriodicitaDAOImpl extends BaseDAOImpl<Periodicita, Integer> implements PeriodicitaDAO {

    @Override
    public Class<Periodicita> getEntityClass() {

	return Periodicita.class;
    }

    /**
     * Ricerca tutte le periodicità (senza filtro per idcomune)
     */
    @Override
    public List<Periodicita> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, null, null);
    }
}
