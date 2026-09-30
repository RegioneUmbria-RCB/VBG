/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidValidazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModellidValidazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;


/**
 * @author francol
 *
 */
@Repository
public class Dyn2ModellildValidazioniDAOImpl extends BaseDAOImpl<Dyn2ModellidValidazioni, PkId> implements Dyn2ModellidValidazioniDAO {

    public Dyn2ModellildValidazioniDAOImpl() {

    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<Dyn2ModellidValidazioni> getEntityClass() {

	return Dyn2ModellidValidazioni.class;
    }
}
