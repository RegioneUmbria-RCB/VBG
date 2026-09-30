package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzeAccessoAttiTDAOImpl extends BaseDAOImpl<IstanzeAccessoAttiT, PkId> implements IstanzeAccessoAttiTDAO {

    @Override
    public Class<IstanzeAccessoAttiT> getEntityClass() {

	return IstanzeAccessoAttiT.class;
    }

    @Override
    public List<IstanzeAccessoAttiT> findAll(Integer firstResult, Integer maxResult) {

	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
