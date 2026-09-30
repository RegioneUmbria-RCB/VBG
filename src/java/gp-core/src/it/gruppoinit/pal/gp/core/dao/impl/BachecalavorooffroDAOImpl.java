package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BachecalavorooffroDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorooffro;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class BachecalavorooffroDAOImpl extends BaseDAOImpl<Bachecalavorooffro, PkId> implements BachecalavorooffroDAO {

    @Override
    public Class<Bachecalavorooffro> getEntityClass() {

	return Bachecalavorooffro.class;
    }

    @Override
    public List<Bachecalavorooffro> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "scadenza", DAOOrderTypeEnum.DESC);
    }
}
