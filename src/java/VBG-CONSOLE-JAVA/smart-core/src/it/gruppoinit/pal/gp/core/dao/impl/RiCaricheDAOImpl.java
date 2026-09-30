package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RiCaricheDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.RiCariche;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class RiCaricheDAOImpl extends BaseDAOImpl<RiCariche, String> implements RiCaricheDAO {

    @Override
    public Class<RiCariche> getEntityClass() {

	return RiCariche.class;
    }

    @Override
    public List<RiCariche> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
