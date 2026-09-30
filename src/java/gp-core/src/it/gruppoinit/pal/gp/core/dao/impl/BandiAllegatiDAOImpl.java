/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BandiAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.BandiAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class BandiAllegatiDAOImpl extends BaseDAOImpl<BandiAllegati, PkId> implements BandiAllegatiDAO {

    @Override
    public Class<BandiAllegati> getEntityClass() {

	return BandiAllegati.class;
    }
}
