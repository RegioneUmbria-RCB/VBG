package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LayoutpagineDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Layoutpagine;
import it.gruppoinit.pal.gp.core.domain.LayoutpagineId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class LayoutpagineDAOImpl extends BaseDAOImpl<Layoutpagine, LayoutpagineId> implements LayoutpagineDAO {

    @Override
    public Class<Layoutpagine> getEntityClass() {

	return Layoutpagine.class;
    }

    @Override
    public List<Layoutpagine> findAll(Integer firstResult, Integer maxResult) {

	// TODO implementare con filter table perchè il doftware è sulla chiave
	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.lpOggetto", DAOOrderTypeEnum.ASC);
    }
}
