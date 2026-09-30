/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellitValidazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModellitValidazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;


/**
 * @author francol
 *
 */
@Repository
public class Dyn2ModellitValidazioniDAOImpl extends BaseDAOImpl<Dyn2ModellitValidazioni, PkId> implements Dyn2ModellitValidazioniDAO {

    public Dyn2ModellitValidazioniDAOImpl() {

    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<Dyn2ModellitValidazioni> getEntityClass() {

	return Dyn2ModellitValidazioni.class;
    }
}
