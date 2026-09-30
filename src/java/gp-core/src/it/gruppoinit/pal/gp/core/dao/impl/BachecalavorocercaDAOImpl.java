package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BachecalavorocercaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorocerca;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class BachecalavorocercaDAOImpl extends BaseDAOImpl<Bachecalavorocerca, PkId> implements BachecalavorocercaDAO {

    @Override
    public Class<Bachecalavorocerca> getEntityClass() {

	return Bachecalavorocerca.class;
    }

    @Override
    public List<Bachecalavorocerca> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "scadenza", DAOOrderTypeEnum.DESC);
    }
}
