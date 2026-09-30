package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentipeopleDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class InventarioprocedimentipeopleDAOImpl extends BaseDAOImpl<Inventarioprocedimentipeople, PkId> implements InventarioprocedimentipeopleDAO {

    @Override
    public Class<Inventarioprocedimentipeople> getEntityClass() {

	return Inventarioprocedimentipeople.class;
    }

    @Override
    public List<Inventarioprocedimentipeople> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
