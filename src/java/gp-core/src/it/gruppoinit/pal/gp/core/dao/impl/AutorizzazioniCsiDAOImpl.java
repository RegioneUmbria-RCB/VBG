package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniCsiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AutorizzazioniCsiDAOImpl extends BaseDAOImpl<AutorizzazioniCsi, PkId> implements AutorizzazioniCsiDAO {

    @Override
    public Class<AutorizzazioniCsi> getEntityClass() {

	return AutorizzazioniCsi.class;
    }

    @Override
    public List<AutorizzazioniCsi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "", DAOOrderTypeEnum.ASC);
    }
}
