package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseComuniAssociatiDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioDTO;

/**
 * 
 * @author
 */
@Repository
public class SorteggitestataDAOImpl extends BaseComuniAssociatiDAOImpl<Sorteggitestata, PkId> implements SorteggitestataDAO {

    @Override
    public Class<Sorteggitestata> getEntityClass() {

	return Sorteggitestata.class;
    }

    @Override
    public List<Sorteggitestata> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "stDatasorteggio", DAOOrderTypeEnum.DESC);
    }

    @Override
    public List<Sorteggitestata> findAllSenzaCategoria() {

	return findAllSenzaCategoria(ORMHelper.getSoftware());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Sorteggitestata> findAllSenzaCategoria(String software) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("software.codice", software));
	det.createCriteria("categoria", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.isNull("categoria.id.codice"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    protected void setCodiceComune(Sorteggitestata entity) {

	if (!checkIfCodiceComuneIsSet(entity.getComune())) {
	    entity.setComune(getDefaultComune());
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getCodiciIstanza(FiltriSorteggioBean filtro) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeDaSorteggiare queryHelper = new QueryIstanzeDaSorteggiare(sessimpl, filtro);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SorteggioDettaglioDTO> findDettaglioDTO(Integer idTestata) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryDettaglio queryHelper = new QueryDettaglio(sessimpl, idTestata);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(SorteggioDettaglioDTO.class));
	return (List<SorteggioDettaglioDTO>) q.list();
    }
}
