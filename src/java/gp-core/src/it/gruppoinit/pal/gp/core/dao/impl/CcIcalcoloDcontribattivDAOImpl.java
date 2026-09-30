package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoloDcontribattivDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloDcontribattiv;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcIcalcoloDcontribattivDAOImpl extends BaseDAOImpl<CcIcalcoloDcontribattiv, PkId> implements CcIcalcoloDcontribattivDAO {

    @Override
    public Class<CcIcalcoloDcontribattiv> getEntityClass() {

	return CcIcalcoloDcontribattiv.class;
    }

    @Override
    public List<CcIcalcoloDcontribattiv> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }
}
