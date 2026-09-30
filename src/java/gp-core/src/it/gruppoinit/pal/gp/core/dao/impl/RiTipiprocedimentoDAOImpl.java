package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RiTipiprocedimentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.RiTipiprocedimento;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class RiTipiprocedimentoDAOImpl extends BaseDAOImpl<RiTipiprocedimento, String> implements RiTipiprocedimentoDAO {

    @Override
    public Class<RiTipiprocedimento> getEntityClass() {

	return RiTipiprocedimento.class;
    }

    @Override
    public List<RiTipiprocedimento> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
