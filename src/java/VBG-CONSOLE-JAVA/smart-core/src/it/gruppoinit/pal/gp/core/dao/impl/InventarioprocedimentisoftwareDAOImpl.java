package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentisoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class InventarioprocedimentisoftwareDAOImpl extends BaseDAOImpl<Inventarioprocedimentisoftware, PkId> implements
	InventarioprocedimentisoftwareDAO {

    @Override
    public Class<Inventarioprocedimentisoftware> getEntityClass() {

	return Inventarioprocedimentisoftware.class;
    }

    @Override
    public List<Inventarioprocedimentisoftware> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }
}
