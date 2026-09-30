package it.gruppoinit.pal.gp.core.features.infrastructure.layout;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Layoutpaginebase;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class LayoutpaginebaseDAOImpl extends BaseDAOImpl<Layoutpaginebase, String> implements LayoutpaginebaseDAO {

    @Override
    public Class<Layoutpaginebase> getEntityClass() {

	return Layoutpaginebase.class;
    }

    @Override
    public List<Layoutpaginebase> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "lpOggetto", DAOOrderTypeEnum.ASC);
    }
}
