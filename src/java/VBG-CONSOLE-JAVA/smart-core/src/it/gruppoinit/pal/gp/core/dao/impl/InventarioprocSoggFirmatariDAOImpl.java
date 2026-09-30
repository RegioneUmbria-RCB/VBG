package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.InventarioprocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class InventarioprocSoggFirmatariDAOImpl extends BaseDAOImpl<InventarioprocSoggFirmatari, PkId> implements InventarioprocSoggFirmatariDAO {

    @Override
    public Class<InventarioprocSoggFirmatari> getEntityClass() {

	return InventarioprocSoggFirmatari.class;
    }

    @Override
    public List<InventarioprocSoggFirmatari> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
