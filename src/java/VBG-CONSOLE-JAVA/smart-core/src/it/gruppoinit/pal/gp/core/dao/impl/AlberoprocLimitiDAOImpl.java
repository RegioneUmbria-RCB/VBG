package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocLimitiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLimiti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class AlberoprocLimitiDAOImpl extends BaseDAOImpl<AlberoprocLimiti, PkId> implements AlberoprocLimitiDAO {

    @Override
    public Class<AlberoprocLimiti> getEntityClass() {

	return AlberoprocLimiti.class;
    }

    @Override
    public List<AlberoprocLimiti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "nrmaxistanze", DAOOrderTypeEnum.ASC);
    }
}
