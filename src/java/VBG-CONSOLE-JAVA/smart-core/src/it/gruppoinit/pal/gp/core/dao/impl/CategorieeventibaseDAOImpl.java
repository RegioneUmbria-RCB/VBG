package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CategorieeventibaseDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CategorieeventibaseDAOImpl extends BaseDAOImpl<Categorieeventibase, String> implements CategorieeventibaseDAO {

    @Override
    public Class<Categorieeventibase> getEntityClass() {

	return Categorieeventibase.class;
    }

    @Override
    public List<Categorieeventibase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
