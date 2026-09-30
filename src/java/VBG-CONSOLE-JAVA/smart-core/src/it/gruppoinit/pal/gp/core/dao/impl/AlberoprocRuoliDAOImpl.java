/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocRuoliDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoliId;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class AlberoprocRuoliDAOImpl extends BaseDAOImpl<AlberoprocRuoli, AlberoprocRuoliId> implements AlberoprocRuoliDAO {

    @Override
    public Class<AlberoprocRuoli> getEntityClass() {

	return AlberoprocRuoli.class;
    }
}
