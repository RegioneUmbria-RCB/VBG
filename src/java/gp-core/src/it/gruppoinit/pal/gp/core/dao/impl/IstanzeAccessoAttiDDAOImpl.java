package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiDDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzeAccessoAttiDDAOImpl extends BaseDAOImpl<IstanzeAccessoAttiD, PkId> implements IstanzeAccessoAttiDDAO {

    @Override
    public Class<IstanzeAccessoAttiD> getEntityClass() {

	return IstanzeAccessoAttiD.class;
    }

    @Override
    public List<IstanzeAccessoAttiD> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
    }
}
