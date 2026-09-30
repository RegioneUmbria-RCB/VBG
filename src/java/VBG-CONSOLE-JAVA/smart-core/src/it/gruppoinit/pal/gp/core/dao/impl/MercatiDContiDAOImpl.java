package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDContiDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MercatiDContiDAOImpl extends BaseDAOImpl<MercatiDConti, PkId> implements MercatiDContiDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiDConti> findByPosteggio(MercatiD posteggio) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("posteggio.id.codice", posteggio.getId().getCodice()));
	return (List<MercatiDConti>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Class<MercatiDConti> getEntityClass() {

	return MercatiDConti.class;
    }
}
