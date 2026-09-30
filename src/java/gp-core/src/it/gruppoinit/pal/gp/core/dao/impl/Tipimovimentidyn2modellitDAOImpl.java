package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Tipimovimentidyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellitId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class Tipimovimentidyn2modellitDAOImpl extends BaseDAOImpl<Tipimovimentidyn2modellit, Tipimovimentidyn2modellitId> implements
	Tipimovimentidyn2modellitDAO {

    @Override
    public Class<Tipimovimentidyn2modellit> getEntityClass() {

	return Tipimovimentidyn2modellit.class;
    }

    @Override
    public List<Tipimovimentidyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
