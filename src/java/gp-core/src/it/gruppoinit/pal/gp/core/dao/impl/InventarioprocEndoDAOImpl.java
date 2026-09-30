package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocEndoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InventarioprocEndoDAOImpl extends BaseDAOImpl<InventarioprocEndo, PkId> implements InventarioprocEndoDAO {

    @Override
    public List<InventarioprocEndo> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public List<InventarioprocEndo> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	throw new NotImplementedException();
    }

    @Override
    public Class<InventarioprocEndo> getEntityClass() {

	return InventarioprocEndo.class;
    }
}
