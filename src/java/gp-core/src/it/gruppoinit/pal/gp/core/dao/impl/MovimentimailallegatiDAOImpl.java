package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentimailallegatiDAO;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class MovimentimailallegatiDAOImpl extends BaseDAOImpl<Movimentimailallegati, PkId> implements MovimentimailallegatiDAO {

    @Override
    public Class<Movimentimailallegati> getEntityClass() {

	return Movimentimailallegati.class;
    }
}
