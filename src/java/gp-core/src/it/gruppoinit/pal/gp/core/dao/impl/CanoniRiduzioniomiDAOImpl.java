package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniRiduzioniomiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioniomi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CanoniRiduzioniomiDAOImpl extends BaseDAOImpl<CanoniRiduzioniomi, PkId> implements CanoniRiduzioniomiDAO {

    @Override
    public Class<CanoniRiduzioniomi> getEntityClass() {

	return CanoniRiduzioniomi.class;
    }

    @Override
    public List<CanoniRiduzioniomi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
