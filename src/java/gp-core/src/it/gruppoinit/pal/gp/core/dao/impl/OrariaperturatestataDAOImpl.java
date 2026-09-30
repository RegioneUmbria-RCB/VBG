package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OrariaperturatestataDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author lucap
 */
@Repository
public class OrariaperturatestataDAOImpl extends BaseDAOImpl<Orariaperturatestata, PkId> implements OrariaperturatestataDAO {

    @Override
    public Class<Orariaperturatestata> getEntityClass() {

	return Orariaperturatestata.class;
    }

    @Override
    public List<Orariaperturatestata> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "tipiorario.toDescrizione", DAOOrderTypeEnum.ASC);
    }
}
