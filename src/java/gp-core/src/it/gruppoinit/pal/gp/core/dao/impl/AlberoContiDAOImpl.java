/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoContiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class AlberoContiDAOImpl extends BaseDAOImpl<AlberoConti, PkId> implements AlberoContiDAO {

    @Override
    public Class<AlberoConti> getEntityClass() {

	return AlberoConti.class;
    }
}
