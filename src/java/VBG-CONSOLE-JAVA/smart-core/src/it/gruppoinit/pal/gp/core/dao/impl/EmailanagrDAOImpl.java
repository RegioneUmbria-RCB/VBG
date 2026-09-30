package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.EmailanagrDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Emailanagr;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class EmailanagrDAOImpl extends BaseDAOImpl<Emailanagr, PkId> implements EmailanagrDAO {

    @Override
    public Class<Emailanagr> getEntityClass() {

	return Emailanagr.class;
    }

    @Override
    public List<Emailanagr> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "data", DAOOrderTypeEnum.DESC);
    }
}
