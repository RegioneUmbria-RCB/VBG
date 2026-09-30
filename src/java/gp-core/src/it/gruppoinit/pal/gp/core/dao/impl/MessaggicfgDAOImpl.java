package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MessaggicfgDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Messaggicfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MessaggicfgDAOImpl extends BaseDAOImpl<Messaggicfg, PkId> implements MessaggicfgDAO {

    @Override
    public Class<Messaggicfg> getEntityClass() {

	return Messaggicfg.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Messaggicfg findBySoftware(Software software) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	criteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	criteria.add(Restrictions.eq("software.codice", software.getCodice()));
	List<Messaggicfg> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<Messaggicfg> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "messaggicfgbase", DAOOrderTypeEnum.ASC);
    }
}
