package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocD2modtattDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtattId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AlberoprocD2modtattDAOImpl extends BaseDAOImpl<AlberoprocD2modtatt, AlberoprocD2modtattId> implements AlberoprocD2modtattDAO {

    @Override
    public Class<AlberoprocD2modtatt> getEntityClass() {

	return AlberoprocD2modtatt.class;
    }

    @Override
    public List<AlberoprocD2modtatt> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "", DAOOrderTypeEnum.ASC);
    }
}
