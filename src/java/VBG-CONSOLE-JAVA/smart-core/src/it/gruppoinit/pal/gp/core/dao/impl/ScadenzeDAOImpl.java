package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ScadenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenze;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class ScadenzeDAOImpl extends BaseDAOImpl<Scadenze, PkId> implements ScadenzeDAO {

    @Override
    public Class<Scadenze> getEntityClass() {

	return Scadenze.class;
    }

    @Override
    public List<Scadenze> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "datascadenza", DAOOrderTypeEnum.DESC);
    }
}
