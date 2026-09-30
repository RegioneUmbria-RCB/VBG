package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrazioniAnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniAnagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AmministrazioniAnagrafeDAOImpl extends BaseDAOImpl<AmministrazioniAnagrafe, PkId> implements AmministrazioniAnagrafeDAO {

    @Override
    public Class<AmministrazioniAnagrafe> getEntityClass() {

	return AmministrazioniAnagrafe.class;
    }

    @Override
    public List<AmministrazioniAnagrafe> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
