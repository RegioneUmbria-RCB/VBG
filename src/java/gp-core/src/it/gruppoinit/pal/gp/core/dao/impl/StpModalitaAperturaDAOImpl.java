package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StpModalitaAperturaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.StpModalitaApertura;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class StpModalitaAperturaDAOImpl extends BaseDAOImpl<StpModalitaApertura, String> implements StpModalitaAperturaDAO {

    @Override
    public Class<StpModalitaApertura> getEntityClass() {

	return StpModalitaApertura.class;
    }

    @Override
    public List<StpModalitaApertura> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
