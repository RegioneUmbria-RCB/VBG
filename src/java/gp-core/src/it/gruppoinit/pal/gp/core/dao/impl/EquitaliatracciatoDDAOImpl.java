package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.EquitaliatracciatoDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.EquitaliatracciatoD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class EquitaliatracciatoDDAOImpl extends BaseDAOImpl<EquitaliatracciatoD, PkId> implements EquitaliatracciatoDDAO {

    @Override
    public Class<EquitaliatracciatoD> getEntityClass() {

	return EquitaliatracciatoD.class;
    }

    @Override
    public List<EquitaliatracciatoD> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
