package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.LivelloServizioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
@Repository
public class LivelloServizioDAOImpl extends BaseDAOImpl<LivelloServizio, PkId> implements LivelloServizioDAO {

    @Override
    public Class<LivelloServizio> getEntityClass() {

	return LivelloServizio.class;
    }

    @Override
    public List<LivelloServizio> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
