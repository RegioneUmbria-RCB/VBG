package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipicausalioninteressiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipicausalioninteressiDAOImpl extends BaseDAOImpl<Tipicausalioninteressi, PkId> implements TipicausalioninteressiDAO {

    @Override
    public Class<Tipicausalioninteressi> getEntityClass() {

	return Tipicausalioninteressi.class;
    }

    @Override
    public List<Tipicausalioninteressi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "ggritardopagamento", DAOOrderTypeEnum.ASC);
    }
}
