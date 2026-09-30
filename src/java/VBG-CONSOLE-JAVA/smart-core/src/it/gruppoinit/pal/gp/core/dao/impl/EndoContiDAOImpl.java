/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.EndoContiDAO;
import it.gruppoinit.pal.gp.core.domain.EndoConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class EndoContiDAOImpl extends BaseDAOImpl<EndoConti, PkId> implements EndoContiDAO {

    @Override
    public Class<EndoConti> getEntityClass() {

	return EndoConti.class;
    }
}
