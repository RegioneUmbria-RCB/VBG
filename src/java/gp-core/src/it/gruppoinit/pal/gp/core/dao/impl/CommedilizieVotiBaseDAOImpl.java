package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieVotiBaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotiBase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class CommedilizieVotiBaseDAOImpl extends BaseDAOImpl<CommedilizieVotiBase, Integer> implements CommedilizieVotiBaseDAO {

    @Override
    public Class<CommedilizieVotiBase> getEntityClass() {

	return CommedilizieVotiBase.class;
    }

    @Override
    public List<CommedilizieVotiBase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, null, null);
    }
}
