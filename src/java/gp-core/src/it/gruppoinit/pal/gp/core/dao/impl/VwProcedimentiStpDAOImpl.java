package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwProcedimentiStpDAO;
import it.gruppoinit.pal.gp.core.domain.VwProcedimentiStp;
import it.gruppoinit.pal.gp.core.domain.VwProcedimentiStpId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwProcedimentiStpDAOImpl extends BaseDAOImpl<VwProcedimentiStp, VwProcedimentiStpId> implements VwProcedimentiStpDAO {

    @Override
    public Class<VwProcedimentiStp> getEntityClass() {

	return VwProcedimentiStp.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwProcedimentiStp> findByCodiceStp(String codiceStp) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codicestp", codiceStp));
	List<VwProcedimentiStp> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @Override
    public List<VwProcedimentiStp> findByCodiceInventario(Integer codiceInventario) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codiceinventario", codiceInventario));
	List<VwProcedimentiStp> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }
}
