package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureDyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDyn2modellitId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class TipiprocedureDyn2modellitDAOImpl extends BaseDAOImpl<TipiprocedureDyn2modellit, TipiprocedureDyn2modellitId> implements
	TipiprocedureDyn2modellitDAO {

    @Override
    public Class<TipiprocedureDyn2modellit> getEntityClass() {

	return TipiprocedureDyn2modellit.class;
    }

    @Override
    public List<TipiprocedureDyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }
}
