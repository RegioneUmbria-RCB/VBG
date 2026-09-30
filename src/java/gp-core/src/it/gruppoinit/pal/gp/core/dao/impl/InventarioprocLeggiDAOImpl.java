package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocLeggiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class InventarioprocLeggiDAOImpl extends BaseDAOImpl<InventarioprocLeggi, PkId> implements InventarioprocLeggiDAO {

    @Override
    public Class<InventarioprocLeggi> getEntityClass() {

	return InventarioprocLeggi.class;
    }

    @Override
    public List<InventarioprocLeggi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
