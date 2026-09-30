package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AlberoprocDocSoggFirmatariDAOImpl extends BaseDAOImpl<AlberoprocDocSoggFirmatari, PkId> implements AlberoprocDocSoggFirmatariDAO {

    @Override
    public Class<AlberoprocDocSoggFirmatari> getEntityClass() {

	return AlberoprocDocSoggFirmatari.class;
    }

    @Override
    public List<AlberoprocDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
