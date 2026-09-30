package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwIAttivitaautorizzazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioni;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitaautorizzazioniId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class VwIAttivitaautorizzazioniDAOImpl extends BaseDAOImpl<VwIAttivitaautorizzazioni, VwIAttivitaautorizzazioniId> implements
	VwIAttivitaautorizzazioniDAO {

    @Override
    public Class<VwIAttivitaautorizzazioni> getEntityClass() {

	return VwIAttivitaautorizzazioni.class;
    }

    @Override
    public List<VwIAttivitaautorizzazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.iidattivita", DAOOrderTypeEnum.ASC);
    }
}
