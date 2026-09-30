/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeOneriDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * @author francol
 *
 */
@Repository
public class FoDomandeOneriDAOImpl extends BaseDAOImpl<FoDomandeOneri, PkId> implements FoDomandeOneriDAO {

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<FoDomandeOneri> getEntityClass() {

	return FoDomandeOneri.class;
    }
}
