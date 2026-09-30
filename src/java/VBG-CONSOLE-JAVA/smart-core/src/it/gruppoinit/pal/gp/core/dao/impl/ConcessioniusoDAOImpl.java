package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConcessioniusoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class ConcessioniusoDAOImpl extends BaseDAOImpl<Concessioniuso, PkId> implements ConcessioniusoDAO {

    @Override
    public Class<Concessioniuso> getEntityClass() {

	return Concessioniuso.class;
    }

    @Override
    public List<Concessioniuso> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Concessioniuso> findByFilter(Concessioniuso entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (entity.getDescrizione() != null && !entity.getDescrizione().equals(""))
	    det.add(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE));
	return (List<Concessioniuso>) getHibernateTemplate().findByCriteria(det);
    }
}
