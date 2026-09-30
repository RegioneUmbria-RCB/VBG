package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TitoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Titoli;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TitoliDAOImpl extends BaseDAOImpl<Titoli, PkId> implements TitoliDAO {

    @Override
    public Class<Titoli> getEntityClass() {

	return Titoli.class;
    }

    @Override
    public List<Titoli> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "titolo", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Titoli> findByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.ilike("titolo", descrizione, MatchMode.ANYWHERE));
	return (List<Titoli>) getHibernateTemplate().findByCriteria(det);
    }
}
