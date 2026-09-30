package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieAppelloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class CommedilizieAppelloDAOImpl extends BaseDAOImpl<CommedilizieAppello, PkId> implements CommedilizieAppelloDAO {

    @Override
    public Class<CommedilizieAppello> getEntityClass() {

	return CommedilizieAppello.class;
    }

    @Override
    public List<CommedilizieAppello> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
