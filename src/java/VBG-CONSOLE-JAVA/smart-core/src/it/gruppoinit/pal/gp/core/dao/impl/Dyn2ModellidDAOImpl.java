package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellidDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Dyn2ModellidDAOImpl extends BaseDAOImpl<Dyn2Modellid, PkId> implements Dyn2ModellidDAO {

    @Override
    public Class<Dyn2Modellid> getEntityClass() {

	return Dyn2Modellid.class;
    }

    @Override
    public Integer findMaxRigaByModelloT(Dyn2Modellit dyn2Modellit) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("dyn2ModellitId", dyn2Modellit.getId().getCodice()));
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	projectionListPrecedenti.add(Projections.max("posverticale"));
	criteria.setProjection(projectionListPrecedenti);
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	if (list.get(0) != null && !list.isEmpty()) {
	    return list.get(0);
	}
	return 0;
    }

    @Override
    public Integer findMaXColonnaByRigaModelloDAndModelloT(Dyn2Modellit dyn2Modellit, Dyn2Modellid dyn2Modellid) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("dyn2ModellitId", dyn2Modellit.getId().getCodice()));
	criteria.add(Restrictions.eq("posverticale", dyn2Modellid.getPosverticale()));
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	projectionListPrecedenti.add(Projections.max("posorizzontale"));
	criteria.setProjection(projectionListPrecedenti);
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	if (list.get(0) != null && !list.isEmpty()) {
	    return list.get(0);
	}
	return 0;
    }
    
    @Override
    public void delete(Dyn2Modellid entity) {

	// TODO Auto-generated method stub
	this.getHibernateTemplate().setFlushMode(HibernateTemplate.FLUSH_EAGER);
	super.delete(entity);
    }
    
}
