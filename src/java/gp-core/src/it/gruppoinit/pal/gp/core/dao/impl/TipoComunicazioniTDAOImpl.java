package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipoComunicazioniTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipoComunicazioniTDAOImpl extends BaseDAOImpl<TipoComunicazioniT, String> implements TipoComunicazioniTDAO {

    @Override
    public Class<TipoComunicazioniT> getEntityClass() {

	return TipoComunicazioniT.class;
    }

    @Override
    public List<TipoComunicazioniT> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
