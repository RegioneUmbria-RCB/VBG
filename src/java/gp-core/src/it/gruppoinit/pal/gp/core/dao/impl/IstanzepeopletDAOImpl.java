package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzepeopletDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeoplet;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IstanzepeopletDAOImpl extends BaseDAOImpl<Istanzepeoplet, PkId> implements IstanzepeopletDAO {

    @Override
    public Class<Istanzepeoplet> getEntityClass() {

	return Istanzepeoplet.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzepeoplet> findIstanzapeoletByIstanza(Istanze istanze) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("istanzepeopleds", "istanzepeopled", DetachedCriteria.LEFT_JOIN);
	criteria.createCriteria("istanzepeopled.istanze", "_istanze");
	criteria.add(Restrictions.eq("_istanze.id.codice", istanze.getId().getCodice()));
	List<Istanzepeoplet> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
