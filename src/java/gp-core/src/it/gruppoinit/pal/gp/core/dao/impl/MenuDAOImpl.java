package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MenuDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Menu;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class MenuDAOImpl extends BaseDAOImpl<Menu, PkId> implements MenuDAO {

    @Override
    public Class<Menu> getEntityClass() {

	return Menu.class;
    }

    @Override
    public List<Menu> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordine", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer findOrdineMax() {

	DetachedCriteria det = getIdcomuneCriteria();
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("ordine"));
	det.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return 0;
    }
}
