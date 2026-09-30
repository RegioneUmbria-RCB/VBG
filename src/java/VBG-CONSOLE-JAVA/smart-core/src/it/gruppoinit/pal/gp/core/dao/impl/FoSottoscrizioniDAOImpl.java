package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoSottoscrizioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoSottoscrizioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class FoSottoscrizioniDAOImpl extends BaseDAOImpl<FoSottoscrizioni, PkId> implements FoSottoscrizioniDAO {

    @Override
    public Class<FoSottoscrizioni> getEntityClass() {

	return FoSottoscrizioni.class;
    }

    @Override
    public List<FoSottoscrizioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
