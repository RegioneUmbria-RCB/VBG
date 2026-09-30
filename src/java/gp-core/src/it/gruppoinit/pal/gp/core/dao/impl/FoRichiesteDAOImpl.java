package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoRichiesteDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class FoRichiesteDAOImpl extends BaseDAOImpl<FoRichieste, PkId> implements FoRichiesteDAO {

    @Override
    public Class<FoRichieste> getEntityClass() {

	return FoRichieste.class;
    }

    @Override
    public List<FoRichieste> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "datarichiesta", DAOOrderTypeEnum.ASC);
    }
}
