package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ScadenzecategoriebaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenzecategoriebase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class ScadenzecategoriebaseDAOImpl extends BaseDAOImpl<Scadenzecategoriebase, PkId> implements ScadenzecategoriebaseDAO {

    @Override
    public Class<Scadenzecategoriebase> getEntityClass() {

	return Scadenzecategoriebase.class;
    }

    @Override
    public List<Scadenzecategoriebase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, null, null);
    }
}
