package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OClassiaddettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OClassiaddetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OClassiaddettiDAOImpl extends BaseDAOImpl<OClassiaddetti, PkId> implements OClassiaddettiDAO {

    @Override
    public Class<OClassiaddetti> getEntityClass() {

	return OClassiaddetti.class;
    }

    @Override
    public List<OClassiaddetti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "classe", DAOOrderTypeEnum.ASC);
    }
}
