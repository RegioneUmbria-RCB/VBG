package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSoggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSoggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AutorizzazioniSoggettiDAOImpl extends BaseDAOImpl<AutorizzazioniSoggetti, PkId> implements AutorizzazioniSoggettiDAO {

    @Override
    public Class<AutorizzazioniSoggetti> getEntityClass() {

	return AutorizzazioniSoggetti.class;
    }

    @Override
    public List<AutorizzazioniSoggetti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
