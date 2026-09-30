package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StradariocoloreDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class StradariocoloreDAOImpl extends BaseDAOImpl<Stradariocolore, StradariocoloreId> implements StradariocoloreDAO {

    @Override
    public Class<Stradariocolore> getEntityClass() {

	return Stradariocolore.class;
    }

    @Override
    public List<Stradariocolore> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "colore", DAOOrderTypeEnum.ASC);
    }
}
