package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2BasecontestiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Dyn2BasecontestiDAOImpl extends BaseDAOImpl<Dyn2Basecontesti, String> implements Dyn2BasecontestiDAO {

    @Override
    public Class<Dyn2Basecontesti> getEntityClass() {

	return Dyn2Basecontesti.class;
    }

    @Override
    public List<Dyn2Basecontesti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "contesto", DAOOrderTypeEnum.ASC);
    }
}
