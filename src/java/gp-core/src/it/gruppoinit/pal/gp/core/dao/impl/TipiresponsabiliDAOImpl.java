/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiresponsabiliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiresponsabili;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class TipiresponsabiliDAOImpl extends BaseDAOImpl<Tipiresponsabili, PkId> implements TipiresponsabiliDAO {

    @Override
    public Class<Tipiresponsabili> getEntityClass() {

	return Tipiresponsabili.class;
    }
}
