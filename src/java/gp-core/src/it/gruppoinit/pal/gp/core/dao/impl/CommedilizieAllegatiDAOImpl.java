package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieAllegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class CommedilizieAllegatiDAOImpl extends BaseDAOImpl<CommedilizieAllegati, PkId> implements CommedilizieAllegatiDAO {

    @Override
    public Class<CommedilizieAllegati> getEntityClass() {

	return CommedilizieAllegati.class;
    }

    @Override
    public List<CommedilizieAllegati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
