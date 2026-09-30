package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzelavoriDDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzelavoriDDAOImpl extends BaseDAOImpl<IstanzelavoriD, PkId> implements IstanzelavoriDDAO {

    @Override
    public Class<IstanzelavoriD> getEntityClass() {

	return IstanzelavoriD.class;
    }

    @Override
    public List<IstanzelavoriD> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
