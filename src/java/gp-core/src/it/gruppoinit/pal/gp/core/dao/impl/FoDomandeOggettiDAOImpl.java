package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeOggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class FoDomandeOggettiDAOImpl extends BaseDAOImpl<FoDomandeOggetti, FoDomandeOggettiId> implements FoDomandeOggettiDAO {

    @Override
    public Class<FoDomandeOggetti> getEntityClass() {

	return FoDomandeOggetti.class;
    }

    @Override
    public List<FoDomandeOggetti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
