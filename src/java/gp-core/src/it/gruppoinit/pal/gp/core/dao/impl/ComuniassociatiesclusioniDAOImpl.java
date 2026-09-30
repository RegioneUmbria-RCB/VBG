package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiesclusioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioni;
import it.gruppoinit.pal.gp.core.domain.ComuniAssociatiEsclusioniId;


/**
 * 
 * @author
 */
@Repository
public class ComuniassociatiesclusioniDAOImpl extends BaseDAOImpl<ComuniAssociatiEsclusioni, ComuniAssociatiEsclusioniId>
	implements ComuniassociatiesclusioniDAO {

    @Override
    public Class<ComuniAssociatiEsclusioni> getEntityClass() {

	return ComuniAssociatiEsclusioni.class;
    }

    @Override
    public List<ComuniAssociatiEsclusioni> findAll(Integer firstResult, Integer maxResult) {

	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
	throw new NotImplementedException();
    }

    @Override
    public ComuniAssociatiEsclusioni findById(ComuniAssociatiEsclusioniId id) {

	return super.findById(id);
    }
}
