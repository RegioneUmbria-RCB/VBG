package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2BasetipitestoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basetipitesto;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class Dyn2BasetipitestoDAOImpl extends BaseDAOImpl<Dyn2Basetipitesto, String> implements Dyn2BasetipitestoDAO {

    @Override
    public Class<Dyn2Basetipitesto> getEntityClass() {

	return Dyn2Basetipitesto.class;
    }

    @Override
    public List<Dyn2Basetipitesto> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "tipotesto", DAOOrderTypeEnum.ASC);
    }
}
