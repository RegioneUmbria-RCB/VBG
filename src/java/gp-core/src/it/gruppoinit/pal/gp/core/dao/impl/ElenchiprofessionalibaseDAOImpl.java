package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ElenchiprofessionalibaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class ElenchiprofessionalibaseDAOImpl extends BaseDAOImpl<Elenchiprofessionalibase, Integer> implements ElenchiprofessionalibaseDAO {

    @Override
    public Class<Elenchiprofessionalibase> getEntityClass() {

	return Elenchiprofessionalibase.class;
    }

    @Override
    public List<Elenchiprofessionalibase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "epDescrizione", DAOOrderTypeEnum.ASC);
    }
}
