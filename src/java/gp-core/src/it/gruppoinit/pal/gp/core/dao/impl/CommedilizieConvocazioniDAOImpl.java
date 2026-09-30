package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieConvocazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class CommedilizieConvocazioniDAOImpl extends BaseDAOImpl<CommedilizieConvocazioni, PkId> implements CommedilizieConvocazioniDAO {

    @Override
    public Class<CommedilizieConvocazioni> getEntityClass() {

	return CommedilizieConvocazioni.class;
    }

    @Override
    public List<CommedilizieConvocazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
