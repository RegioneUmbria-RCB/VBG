package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwPosteggiconcessionipresenzeDAO;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessionipresenze;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessionipresenzeId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwPosteggiconcessionipresenzeDAOImpl extends BaseDAOImpl<VwPosteggiconcessionipresenze, VwPosteggiconcessionipresenzeId> implements
	VwPosteggiconcessionipresenzeDAO {

    @Override
    public Class<VwPosteggiconcessionipresenze> getEntityClass() {

	return VwPosteggiconcessionipresenze.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwPosteggiconcessionipresenze> findPosteggiConcessioniPresenze(MercatipresenzeT giorno) {

	DetachedCriteria cri = getIdcomuneCriteria();
	cri.add(Restrictions.eq("id.fkidtestata", giorno.getId().getCodice()));
	cri.createAlias("posteggio", "_posteggio");
	cri.addOrder(Order.asc("_posteggio.codiceposteggio"));
	List list = getHibernateTemplate().findByCriteria(cri);
	return list;
    }
}
