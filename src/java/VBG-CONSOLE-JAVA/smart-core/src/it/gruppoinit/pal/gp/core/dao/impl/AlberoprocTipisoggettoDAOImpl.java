package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocTipisoggettoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AlberoprocTipisoggettoDAOImpl extends BaseDAOImpl<AlberoprocTipisoggetto, PkId> implements AlberoprocTipisoggettoDAO {

    @Override
    public Class<AlberoprocTipisoggetto> getEntityClass() {

	return AlberoprocTipisoggetto.class;
    }

    @Override
    public List<AlberoprocTipisoggetto> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
