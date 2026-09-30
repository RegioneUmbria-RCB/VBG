package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocBolkesteinDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocBolkestein;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AlberoprocBolkesteinDAOImpl extends BaseDAOImpl<AlberoprocBolkestein, PkId> implements AlberoprocBolkesteinDAO {

    @Override
    public Class<AlberoprocBolkestein> getEntityClass() {

	return AlberoprocBolkestein.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoprocBolkestein> findByAlberoProc(Integer codice) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("alberoproc.id.codice", codice));
	return getHibernateTemplate().findByCriteria(det);
    }
}
