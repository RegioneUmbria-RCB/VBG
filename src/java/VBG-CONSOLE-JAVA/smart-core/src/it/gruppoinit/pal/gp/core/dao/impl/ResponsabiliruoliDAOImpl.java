/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliruoliDAO;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliruoliId;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class ResponsabiliruoliDAOImpl extends BaseDAOImpl<Responsabiliruoli, ResponsabiliruoliId> implements ResponsabiliruoliDAO {

    @Override
    public Class<Responsabiliruoli> getEntityClass() {

	return Responsabiliruoli.class;
    }
}
