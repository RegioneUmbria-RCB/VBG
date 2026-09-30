package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiAnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafeId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzeAccessoAttiAnagrafeDAOImpl extends BaseDAOImpl<IstanzeAccessoAttiAnagrafe, IstanzeAccessoAttiAnagrafeId> implements
	IstanzeAccessoAttiAnagrafeDAO {

    @Override
    public Class<IstanzeAccessoAttiAnagrafe> getEntityClass() {

	return IstanzeAccessoAttiAnagrafe.class;
    }

    @Override
    public List<IstanzeAccessoAttiAnagrafe> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
    }
}
