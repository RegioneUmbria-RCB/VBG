package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InteressiLegaliDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.InteressiLegali;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

@Repository
public class InteressiLegaliDAOImpl extends BaseDAOImpl<InteressiLegali, Integer> implements InteressiLegaliDAO {

    @Override
    public Class<InteressiLegali> getEntityClass() {

	return InteressiLegali.class;
    }

    @Override
    public void delete(InteressiLegali entity) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(InteressiLegali entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(InteressiLegali entity) {

	throw new NotImplementedException();
    }

    @Override
    public InteressiLegali findById(Integer id) {

	throw new NotImplementedException();
    }

    // E' stato fatto l'override del metodo findByExample
    // perchè il metodo di BaseDAOImpl aggiunge idcomune come restriction
    @Override
    @SuppressWarnings("unchecked")
    public List<InteressiLegali> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	if (null != firstResult && null != maxResult) {
	    return (List<InteressiLegali>) getHibernateTemplate().findByCriteria(criteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<InteressiLegali>) getHibernateTemplate().findByCriteria(criteria);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InteressiLegali> findByDataInizioFine(Date dataInizio, Date dataFine) {

	String query = "from InteressiLegali interessiLegali"
		+ " where interessiLegali.dataInizio <=  :DataFine  and ( interessiLegali.dataFine >=  :DataInizio or "
		+ "interessiLegali.dataFine is null )";
	String paramNames[] = new String[2];
	paramNames[0] = "DataFine";
	paramNames[1] = "DataInizio";
	Date values[] = new Date[2];
	values[0] = dataFine;
	values[1] = dataInizio;
	List<InteressiLegali> list = getHibernateTemplate().findByNamedParam(query, paramNames, values);
	return list;
    }
}
