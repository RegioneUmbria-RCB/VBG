package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovimentoComunicazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoComunicazioni;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipimovimentoComunicazioniDAOImpl extends BaseDAOImpl<TipimovimentoComunicazioni, PkId> implements TipimovimentoComunicazioniDAO {

    @Override
    public Class<TipimovimentoComunicazioni> getEntityClass() {

	return TipimovimentoComunicazioni.class;
    }

    @Override
    public List<TipimovimentoComunicazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "funzione", DAOOrderTypeEnum.ASC);
    }
}
