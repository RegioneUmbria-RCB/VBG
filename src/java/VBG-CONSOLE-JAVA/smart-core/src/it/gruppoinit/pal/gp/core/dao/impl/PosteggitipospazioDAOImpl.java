package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PosteggitipospazioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PosteggitipospazioDAOImpl extends BaseDAOImpl<Posteggitipospazio, PkId> implements PosteggitipospazioDAO {

    @Override
    public Class<Posteggitipospazio> getEntityClass() {

	return Posteggitipospazio.class;
    }

    @Override
    public List<Posteggitipospazio> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tipospazio", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Posteggitipospazio> findByTipoSpazio(Posteggitipospazio posteggitipospazio) {

	List<Posteggitipospazio> list = new ArrayList<Posteggitipospazio>();
	if (!posteggitipospazio.getTipospazio().equals("")) {
	    if (posteggitipospazio.getTipospazio().equals("%")) {
		list = this.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tipospazio", DAOOrderTypeEnum.ASC);
	    } else {
		DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
		det.add(Restrictions.ilike("tipospazio", posteggitipospazio.getTipospazio(), MatchMode.ANYWHERE));
		det.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
		det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
		list = getHibernateTemplate().findByCriteria(det);
	    }
	}
	return list;
    }
}