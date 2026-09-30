package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StiliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Stili;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class StiliDAOImpl extends BaseDAOImpl<Stili, Integer> implements StiliDAO {

    @Override
    public Class<Stili> getEntityClass() {

	return Stili.class;
    }

    @Override
    public List<Stili> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "stDescrizione", DAOOrderTypeEnum.ASC);
    }
}
