package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiLocalizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipiLocalizzazioniDAOImpl extends BaseDAOImpl<TipiLocalizzazioni, PkId> implements TipiLocalizzazioniDAO {

    @Override
    public Class<TipiLocalizzazioni> getEntityClass() {

	return TipiLocalizzazioni.class;
    }

    @Override
    public List<TipiLocalizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
