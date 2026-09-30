package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.ProdottoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Prodotto;

import org.springframework.stereotype.Repository;

@Repository
public class ProdottoDAOImpl extends BaseDAOImpl<Prodotto, String> implements ProdottoDAO {

    @Override
    public List<Prodotto> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Class<Prodotto> getEntityClass() {

	return Prodotto.class;
    }
}
