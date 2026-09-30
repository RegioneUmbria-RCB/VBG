package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.GiornisectimanaDAO;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class GiornisectimanaDAOImpl extends BaseDAOImpl<Giornisettimana, Integer> implements GiornisectimanaDAO {

    @Override
    public Class<Giornisettimana> getEntityClass() {

	return Giornisettimana.class;
    }

    // E' stato fatto l'override del metodo findByExample
    // perchè il metodo di BaseDAOImpl aggiunge idcomune come restriction
    @Override
    @SuppressWarnings("unchecked")
    public List<Giornisettimana> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.addOrder(Order.asc("id"));
	return (List<Giornisettimana>) getHibernateTemplate().findByCriteria(det);
    }
}
