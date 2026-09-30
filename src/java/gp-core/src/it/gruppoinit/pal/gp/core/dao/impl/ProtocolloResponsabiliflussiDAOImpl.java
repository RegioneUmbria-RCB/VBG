/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloResponsabiliflussiDAO;
import it.gruppoinit.pal.gp.core.domain.ProtocolloResponsabiliflussi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloResponsabiliflussiId;

import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class ProtocolloResponsabiliflussiDAOImpl extends BaseDAOImpl<ProtocolloResponsabiliflussi, ProtocolloResponsabiliflussiId> implements
	ProtocolloResponsabiliflussiDAO {

    @Override
    public Class<ProtocolloResponsabiliflussi> getEntityClass() {

	return ProtocolloResponsabiliflussi.class;
    }
}
