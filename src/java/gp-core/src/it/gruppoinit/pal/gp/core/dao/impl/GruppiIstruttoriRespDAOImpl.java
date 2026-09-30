package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.GruppiIstruttoriRespDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class GruppiIstruttoriRespDAOImpl extends BaseDAOImpl<GruppiIstruttoriResp, PkId> implements GruppiIstruttoriRespDAO {

    @Override
    public Class<GruppiIstruttoriResp> getEntityClass() {

	return GruppiIstruttoriResp.class;
    }

    @Override
    public List<GruppiIstruttoriResp> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
