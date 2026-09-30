/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtAooresponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.ProtAooresponsabili;
import it.gruppoinit.pal.gp.core.domain.ProtAooresponsabiliId;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class ProtAooresponsabiliDAOImpl extends BaseDAOImpl<ProtAooresponsabili, ProtAooresponsabiliId> implements ProtAooresponsabiliDAO {

    @Override
    public Class<ProtAooresponsabili> getEntityClass() {

	return ProtAooresponsabili.class;
    }
}
