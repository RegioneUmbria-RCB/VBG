package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwAnagrafeinterdettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdetti;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdettiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class VwAnagrafeinterdettiDAOImpl extends BaseDAOImpl<VwAnagrafeinterdetti, VwAnagrafeinterdettiId> implements VwAnagrafeinterdettiDAO {

    @Override
    public Class<VwAnagrafeinterdetti> getEntityClass() {

	return VwAnagrafeinterdetti.class;
    }

    @Override
    public List<VwAnagrafeinterdetti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
