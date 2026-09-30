package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LinkPreferitiUtenteDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class LinkPreferitiUtenteDAOImpl extends BaseDAOImpl<LinkPreferitiUtente, PkId> implements LinkPreferitiUtenteDAO {

    @Override
    public Class<LinkPreferitiUtente> getEntityClass() {

	return LinkPreferitiUtente.class;
    }

    @Override
    public List<LinkPreferitiUtente> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordine", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Integer findMaxOrdine() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.setProjection(Projections.max("ordine"));
	List<Integer> list = (List<Integer>) getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return 0;
    }
}
