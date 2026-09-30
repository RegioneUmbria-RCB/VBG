package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwAlberoprocStpDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStp;
import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStpId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwAlberoprocStpDAOImpl extends BaseDAOImpl<VwAlberoprocStp, VwAlberoprocStpId> implements VwAlberoprocStpDAO {

    @Override
    public Class<VwAlberoprocStp> getEntityClass() {

	return VwAlberoprocStp.class;
    }

    @Override
    public List<VwAlberoprocStp> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.codiceinventario", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwAlberoprocStp> findByCodiceStp(String idProcedimento) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("id.codicestp", idProcedimento));
	List<VwAlberoprocStp> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }
}
